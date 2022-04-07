package com.tsh.crypto;

import com.tsh.crypto.crypto.AES;
import com.tsh.crypto.crypto.Base58;
import com.tsh.crypto.crypto.Security;
import com.tsh.crypto.util.IO;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author hutianhang
 */
public class Client {
    public static void testGenerateKey(String[] args) throws NoSuchAlgorithmException {
        final int SIZE = 1000;
        Map<String, byte[]> keyMap = new HashMap<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            byte[] bytes = AES.generateKey();
            String base58 = Base58.encode(bytes);
            keyMap.put(base58, bytes);
            System.out.println(base58 + " " + keyMap.size());
        }
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {

        Security security = new Security(
                IO.readAsString(new File(Const.RES_DIR, "pri.key")),
                IO.readAsString(new File(Const.RES_DIR, "pub.key"))
        );
        security.setListener((cur, total, completed) -> {
            System.out.println("progress: " + cur + "/" + total + "  " + completed);
            return false;
        });
        byte[] key = AES.generateKey();

        testString(security, key);
//        testFile(security, key);
    }

    private static void testFile(Security security, byte[] key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException {
        byte[] keyEn = security.encodeFile(key, new File(Const.RES_DIR, "file.jpg"), new File(Const.BUILD_DIR, "file.en"));
        security.decodeFile(keyEn, new File(Const.BUILD_DIR, "file.en"), new File(Const.BUILD_DIR, "file.de"));
    }

    private static void testString(Security security, byte[] key) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
        Security.EncodeModel encodeModel = security.encodeString(key,
                IO.readAsString(new File(Const.RES_DIR, "content.txt"), true));
        IO.writeToFile(encodeModel.contentBytesEn, new File(Const.BUILD_DIR, "content.en"));

        String decode = security.decodeString(new Security.EncodeModel(
                IO.readAsByteArray(new File(Const.BUILD_DIR, "content.en")),
                encodeModel.keyBytesEn
        ));
        IO.writeToFile(decode, new File(Const.BUILD_DIR, "content.de"));
    }
}
