package com.block_chain.KLTN.domain.wallet;

import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import com.block_chain.KLTN.domain.organization.OrganizationRepository;
import com.block_chain.KLTN.domain.postOffices.PostOfficesEntity;
import com.block_chain.KLTN.domain.postOffices.PostOfficesRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.block_chain.KLTN.publiser.CreateWalletProducer;
import com.block_chain.KLTN.util.AppUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

@Service
@RequiredArgsConstructor
public class DefaultWalletService implements WalletService{
    private final WalletRepository walletRepository;
    private final CreateWalletProducer createWalletProducer;
    private final PostOfficesRepository postOfficesRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public void createWallet(CreateWalletEvent event) {
        WalletEntity entity = WalletEntity
                .builder()
                .code(event.code())
                .type(event.type())
                .build();
        initWalletEntity(entity);
        walletRepository.save(entity);

        switch (event.type()) {
            case POST_OFFICES:
                PostOfficesEntity postOffices = postOfficesRepository.findById(event.code())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Offices"));
                postOffices.setWalletAddress(entity.getAddress());
                break;
            case ORGANIZATION:
                OrganizationEntity organization = organizationRepository.findById(event.code())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Organization"));
                organization.setWalletAddress(entity.getAddress());
                break;
        
            default:
                throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Wallet");
        }

        createWalletProducer.sendMessage(entity);
    }

    @Override
    public String getWalletAddress(WalletType type, Integer code) {
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
