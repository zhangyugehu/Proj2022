package com.tsh.crypto.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RSA 非对称加密
 * @author hutianhang
 */
public class RSA implements IBytesCrypto {
    public static PrivateKey loadPrivateKey(String stringifyKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(
                        Base64.getDecoder().decode(
                                stringifyKey)));
    }

    public static PublicKey loadPublicKey(String stringifyKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(
                        Base64.getDecoder().decode(
                                stringifyKey)));
    }

    private final Key privateKey, publicKey;

    public RSA(Key privateKey, Key publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public RSA(String privateKey, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.privateKey = loadPrivateKey(privateKey);
        this.publicKey = loadPublicKey(publicKey);
    }

    @Override
    public byte[] encodeBytes(byte[] key, byte[] content) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (privateKey == null) {
            throw new IllegalArgumentException("privateKey null");
        }
        return doFinal(privateKey, content, Cipher.ENCRYPT_MODE);
    }

    @Override
    public byte[] decodeBytes(byte[] key, byte[] contentEn) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (publicKey == null) {
            throw new IllegalArgumentException("publicKey null");
        }
        return doFinal(publicKey, contentEn, Cipher.DECRYPT_MODE);
    }

    /* 一般不用作文件加解密
    @Override
    public void encodeFile(byte[] key, File in, File out) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        cryptoFile(publicKey, in, out, Cipher.ENCRYPT_MODE, 64);
    }

    @Override
    public void decodeFile(byte[] key, File in, File out) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        cryptoFile(privateKey, in, out, Cipher.DECRYPT_MODE, 128);
    }

    private void cryptoFile(Key key, File in, File out, int mode, int bufSize) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        IO.copy(new FileInputStream(in), new FileOutputStream(out), ((cur, total, completed) -> {
            System.out.println(mode + " " + cur + "/" + total);
            return false;
        }), bytes -> doFinal(privateKey, bytes, mode), bufSize);
    }
     */

    private byte[] doFinal(Key key, byte[] content, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(mode, key);
        return cipher.doFinal(content);
    }
}
