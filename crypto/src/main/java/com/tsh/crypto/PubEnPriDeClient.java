package com.tsh.crypto;

import com.tsh.crypto.util.CryptoUtil;
import com.tsh.crypto.util.IOUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author hutianhang
 */
public class PubEnPriDeClient {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        PublicKey pub = CryptoUtil.loadPublicKey(IOUtils.readAsString(new File(Const.RES_DIR, "pub.key")));
        byte[] encodeBytes = CryptoUtil.encodeToBytes(pub, "zhangyugehu".getBytes(StandardCharsets.UTF_8));
        System.out.println("encodeString.BASE64: " + Base64.getEncoder().encodeToString(encodeBytes));

        PrivateKey pri = CryptoUtil.loadPrivateKey(IOUtils.readAsString(new File(Const.RES_DIR, "pri.key")));
        String decodeString = CryptoUtil.decodeToString(pri, encodeBytes);
        System.out.println("decodeString: " + decodeString);
    }
}
