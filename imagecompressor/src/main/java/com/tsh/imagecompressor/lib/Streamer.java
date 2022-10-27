package com.tsh.imagecompressor.lib;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author hutianhang
 */
public interface Streamer {
    /**
     * 打开流
     *
     * @return
     * @throws IOException
     */
    InputStream openStream() throws IOException;
}
