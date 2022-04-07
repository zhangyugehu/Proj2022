package com.tsh.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author hutianhang
 */
public class CryptoUtil {
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final int DECRYPT_SIZE = 128;
    private static final int ENCRYPT_SIZE = 64;

    //region 非对称加密
    private static Key loadKeyFromStream(InputStream is)
            throws IOException, ClassNotFoundException {
        Key key;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
            key = (Key) ois.readObject();
        } finally {
            IOUtils.closeSilently(ois);
        }
        return key;
    }

    public static PrivateKey loadPrivateKey(String stringifyKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(
                        Base64.getDecoder().decode(stringifyKey)));
    }

    public static PublicKey loadPublicKey(String stringifyKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(
                        Base64.getDecoder().decode(stringifyKey)));
    }

    private static byte[] doFinal(Key key, byte[] content, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, key);
        return cipher.doFinal(content);
    }

    public static byte[] encodeToBytes(Key key, byte[] content)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return doFinal(key, content, Cipher.ENCRYPT_MODE);
    }

    public static byte[] encodeToBytes(String privateKey, byte[] content)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        return doFinal(loadPrivateKey(privateKey), content, Cipher.ENCRYPT_MODE);
    }

    public static String decodeToString(Key key, byte[] content)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return new String(doFinal(key, content, Cipher.DECRYPT_MODE));
    }

    public static byte[] decodeToBytes(Key key, byte[] content)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        return doFinal(key, content, Cipher.DECRYPT_MODE);
    }

    public static byte[] decodeToBytes(String publicKey, byte[] content)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        return doFinal(loadPublicKey(publicKey), content, Cipher.DECRYPT_MODE);
    }
    //endregion

    public static byte[] genAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }


    public static final String VIPARA = "0102030405060708";

    private static Cipher getAesCipher(byte[] keyBytes, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec sKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, sKey, zeroIv);
        return cipher;
    }

    private static byte[] aesEncode(byte[] key, byte[] content, int mode) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = getAesCipher(key, mode);
        return cipher.doFinal(content);
    }

    public static byte[] aesEncode(byte[] key, String content) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return aesEncode(key, content.getBytes(StandardCharsets.UTF_8), Cipher.ENCRYPT_MODE);
    }

    public static String aesDecode(byte[] key, byte[] content) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new String(aesEncode(key, content, Cipher.DECRYPT_MODE));
    }

}
