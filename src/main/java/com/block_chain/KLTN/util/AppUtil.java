package com.block_chain.KLTN.util;

import com.block_chain.KLTN.domain.wallet.crypto.CryptoLib;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

@Component
public class AppUtil {

    public static String getStringFromKey(Key key) {
        return CryptoLib.Encoded(key.getEncoded());
    }

    //********Public Key*********//

    public static String encryptPublicKey(PublicKey key){
        return getStringFromKey(key);
    }

    public static String[] encryptPrivateKey(String msg, PrivateKey key) throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //Initialize salt
        Random r = new SecureRandom();
        byte[] salt = new byte[8];
        r.nextBytes(salt);

        //Initialize vector
        byte[] vector = new byte[128/8];
        r.nextBytes(vector);

        //initialize variables
        String MsgToEncrypt = getStringFromKey(key);

        //Generating AES key
        Cipher ecipher = CryptoLib.createCipher(msg, salt, new IvParameterSpec(vector), Cipher.ENCRYPT_MODE);

        //encrypttion
        byte[] encrypted = ecipher.doFinal(MsgToEncrypt.getBytes());
        byte[] concat = concatenateByteArrays(salt, vector);
        String salt_iv = CryptoLib.Encoded(concat);
        String encrypted_str = CryptoLib.Encoded(encrypted);

        return new String[] {salt_iv, encrypted_str};
    }
    public static byte[] concatenateByteArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
