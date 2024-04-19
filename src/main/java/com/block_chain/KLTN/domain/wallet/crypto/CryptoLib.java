package com.block_chain.KLTN.domain.wallet.crypto;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class CryptoLib {
    public static String Encoded(byte[] msg){
        return Base64.getEncoder().encodeToString(msg);
    }

    public static byte[] Decoded(String msg){
        return Base64.getDecoder().decode(msg);
    }

    public static String hash(String msg){
        return BCrypt.hashpw(msg, BCrypt.gensalt());
    }

    public static boolean verify(String msg, String hash){
        return BCrypt.checkpw(msg, hash);
    }

    public static Cipher createCipher(String msg, byte[] salt, IvParameterSpec ivspec, int mode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher ecipher;

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec mykeySpec = new PBEKeySpec(msg.toCharArray(), salt, 10000, 128);
        SecretKey tmp = factory.generateSecret(mykeySpec);
        SecretKeySpec mySecretkey = new SecretKeySpec(tmp.getEncoded(), "AES");

        //==> Create and initiate encryption
        System.out.println("Initiate encryption alogrithm... + " + mode);
        ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(mode, mySecretkey, ivspec);

        return ecipher;
    }
}
