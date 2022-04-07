package com.tsh.crypto.crypto;

import com.tsh.crypto.util.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 对称加密
 *
 * @author hutianhang
 */
public class AES implements IBytesCrypto, IFileCrypto {

    public static byte[] generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    IO.Listener listener;

    public void setListener(IO.Listener listener) {
        this.listener = listener;
    }

    @Override
    public byte[] encodeBytes(byte[] key, byte[] content) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (key == null) {
            throw new IllegalArgumentException("key null");
        }
        return doFinal(key, content, Cipher.ENCRYPT_MODE);
    }

    @Override
    public byte[] decodeBytes(byte[] key, byte[] contentEn) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (key == null) {
            throw new IllegalArgumentException("key null");
        }
        return doFinal(key, contentEn, Cipher.DECRYPT_MODE);
    }

    @Override
    public void encodeFile(byte[] key, File in, File out) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (key == null) {
            throw new IllegalArgumentException("key null");
        }
        cryptoFile(key, in, out, Cipher.ENCRYPT_MODE);
    }

    @Override
    public void decodeFile(byte[] key, File in, File out) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (key == null) {
            throw new IllegalArgumentException("key null");
        }
        cryptoFile(key, in, out, Cipher.DECRYPT_MODE);
    }

    private Cipher getCipher(byte[] keyBytes, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKeySpec sKey = new SecretKeySpec(keyBytes, "AES");
        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec zeroIv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(mode, sKey, zeroIv);
        return cipher;
    }

    private byte[] doFinal(byte[] key, byte[] content, int mode) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return getCipher(key, mode).doFinal(content);
    }

    private void cryptoFile(byte[] key, File in, File out, int mode) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        IO.copy(new FileInputStream(in), new CipherOutputStream(new FileOutputStream(out), getCipher(key, mode)), listener, null, 0);
    }

}
