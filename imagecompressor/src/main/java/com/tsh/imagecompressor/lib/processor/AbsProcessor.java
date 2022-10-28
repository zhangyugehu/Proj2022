package com.tsh.imagecompressor.lib.processor;

import android.graphics.Bitmap;

import com.tsh.imagecompressor.lib.CompressException;
import com.tsh.imagecompressor.lib.Config;

import java.io.IOException;
import java.io.InputStream;

/**
 * 压缩处理器
 *
 * @author hutianhang
 */
public abstract class AbsProcessor {
    public interface StreamConsumer<T> {

        /**
         *
         * @param is
         * @return
         */
        T accept(InputStream is);
    }

    protected Config config;

    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * 压缩方法
     *
     * @return
     * @throws CompressException
     */
    public abstract Bitmap compress() throws CompressException;

    protected <T> T handleSourceStream(StreamConsumer<T> consumer) throws CompressException {
        if (config.getStreamProvider() == null) {
            throw new CompressException("set uri or inputStream first.");
        }
        try {
            return consumer.accept(config.getStreamProvider().openStream());
        } catch (IOException e) {
            throw new CompressException("", e);
        }
    }
}
