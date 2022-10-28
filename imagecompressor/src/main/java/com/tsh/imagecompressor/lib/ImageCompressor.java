package com.tsh.imagecompressor.lib;

import android.graphics.Bitmap;
import android.os.Looper;

import com.tsh.imagecompressor.lib.processor.AbsProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片压缩操作类
 *
 * @author hutianhang
 */
public class ImageCompressor {

    public static class CompressResult {

        private final Bitmap bitmap;
        private final Config config;

        public CompressResult(Config config, Bitmap bitmap) {
            this.bitmap = bitmap;
            this.config = config;
        }

        public void toFile(File file) throws CompressException {
            try (OutputStream os = new FileOutputStream(file)) {
                bitmap.compress(config.getFormat(), config.getQuality(), os);
            } catch (IOException e) {
                throw new CompressException("", e);
            }
        }

        public Bitmap toBitmap() {
            return bitmap;
        }

    }

    public static ImageCompressor withConfig(Config config) {
        return new ImageCompressor(config);
    }

    private AbsProcessor processor;
    private Config config;

    public ImageCompressor(AbsProcessor processor) {
        this.processor = processor;
    }

    public ImageCompressor(Config config) {
        this.config = config;
    }

    public ImageCompressor byProcessor(AbsProcessor processor) {
        this.processor = processor;
        return this;
    }

    public CompressResult compress() throws CompressException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new CompressException("Do not execute this function on the main thread.");
        }
        if (processor == null) {
            throw new CompressException("compressor null");
        }
        processor.setConfig(config);
        return new CompressResult(config, processor.compress());
    }
}
