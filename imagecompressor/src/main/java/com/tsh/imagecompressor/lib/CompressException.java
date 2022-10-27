package com.tsh.imagecompressor.lib;

/**
 * 图片压缩异常
 * @author hutianhang
 */
public class CompressException extends Exception {
    public CompressException(String message) {
        super(message);
    }

    public CompressException(String message, Throwable cause) {
        super(message, cause);
    }
}
