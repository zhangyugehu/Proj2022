package com.tsh.crypto.crypto;

import com.tsh.crypto.util.IO;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author hutianhang
 */
public class Security {

    RSA rsa;
    AES aes;

    public Security(String privateKey, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        rsa = new RSA(privateKey, publicKey);
        aes = new AES();
    }

    public void setListener(IO.Listener listener) {
        aes.setListener(listener);
    }

    public EncodeModel encodeString(byte[] key, String content) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] keyBytesEn = rsa.encodeBytes(null, key);
        byte[] contentBytesEn = aes.encodeBytes(key, content.getBytes());
        return new EncodeModel(contentBytesEn, keyBytesEn);
    }

    public String decodeString(EncodeModel model) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] aesKey = rsa.decodeBytes(null, model.keyBytesEn);
        return new String(aes.decodeBytes(aesKey, model.contentBytesEn));
    }

    public byte[] encodeFile(byte[] key, File in, File out) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] keyBytesEn = rsa.encodeBytes(null, key);
        aes.encodeFile(key, in, out);
        return keyBytesEn;
    }

    public void decodeFile(byte[] keyEn, File in, File out) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
        byte[] aesKey = rsa.decodeBytes(null, keyEn);
        aes.decodeFile(aesKey, in, out);
    }

    public static class EncodeModel {
        public byte[] contentBytesEn;
        public byte[] keyBytesEn;

        public EncodeModel(byte[] contentBytesEn, byte[] keyBytesEn) {
            this.contentBytesEn = contentBytesEn;
            this.keyBytesEn = keyBytesEn;
        }

        public String contentEnToBase64() {
            return Base64.getEncoder().encodeToString(contentBytesEn);
        }
    }
}
