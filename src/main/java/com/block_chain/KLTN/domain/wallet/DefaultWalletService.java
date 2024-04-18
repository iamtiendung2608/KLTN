package com.block_chain.KLTN.domain.wallet;

import com.block_chain.KLTN.publiser.CreateWalletProducer;
import com.block_chain.KLTN.util.AppUtil;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

@Service
public class DefaultWalletService implements WalletService{
    private final WalletRepository walletRepository;
    private final CreateWalletProducer createWalletProducer;

    public DefaultWalletService(WalletRepository walletRepository, CreateWalletProducer createWalletProducer) {
        this.walletRepository = walletRepository;
        this.createWalletProducer = createWalletProducer;
    }


    @Override
    public void createWallet(CreateWalletEvent event) {
        WalletEntity entity = WalletEntity
                .builder()
                .code(event.code())
                .type(WalletType.USER)
                .build();
        initWalletEntity(entity);
        walletRepository.save(entity);
        //TODO send event to rabbitmq
        createWalletProducer.sendMessage(entity);
    }

    @Override
    public String getWalletAddress(WalletType type, String code) {
        return "";
    }

    private void initWalletEntity(WalletEntity entity){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "SunEC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            entity.setAddress(AppUtil.encryptPublicKey(keyPair.getPublic()));
            String[] encrypted = AppUtil.encryptPrivateKey(entity.getAddress() , keyPair.getPrivate());
            entity.setSaltIv(encrypted[0]);
            entity.setSecret(encrypted[1]);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
