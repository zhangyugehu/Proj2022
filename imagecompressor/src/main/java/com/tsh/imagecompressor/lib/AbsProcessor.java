package com.tsh.imagecompressor.lib;

import android.content.Context;
import android.graphics.Bitmap;

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

    protected final Context context;
    protected Config config;

    public AbsProcessor(Context context) {
        this.context = context;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * 压缩方法
     *
     * @return
     * @throws CompressException
     */
    abstract Bitmap compress() throws CompressException;

    protected <T> T handleSourceStream(StreamConsumer<T> consumer) throws CompressException {
        if (config.getUri() != null) {
            try (InputStream is = context.getContentResolver().openInputStream(config.getUri())) {
                return consumer.accept(is);
            } catch (IOException e) {
                throw new CompressException("", e);
            }
        } else if (config.getStreamer() != null) {
            try {
                return consumer.accept(config.getStreamer().openStream());
            } catch (IOException e) {
                throw new CompressException("", e);
            }
        } else {
            throw new CompressException("set uri or inputStream first.");
        }
    }
}
