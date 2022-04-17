package com.tsh.crypto.crypto;

import java.io.File;

/**
 * @author hutianhang
 */
public interface IBytesCrypto {
    /**
     *
     * @param content
     * @return
     * @throws Exception
     */
    byte[] encodeBytes(byte[] key, byte[] content) throws Exception;

    /**
     *
     * @param contentEn
     * @return
     * @throws Exception
     */
    byte[] decodeBytes(byte[] key, byte[] contentEn) throws Exception;

}
