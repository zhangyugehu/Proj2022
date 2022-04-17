package com.tsh.crypto.crypto;

import java.io.File;

/**
 * @author hutianhang
 */
public interface IFileCrypto {

    /**
     *
     * @param in
     * @throws Exception
     */
    void encodeFile(byte[] key, File in, File out) throws Exception;

    /**
     *
     * @param out
     * @throws Exception
     */
    void decodeFile(byte[] key, File in, File out) throws Exception;
}
