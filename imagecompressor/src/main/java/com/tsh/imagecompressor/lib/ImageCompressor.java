package com.tsh.imagecompressor.lib;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author hutianhang
 */
public class ImageCompressor {

    public class CompressResult {

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

    private final AbsProcessor compressor;
    private Config config;

    public ImageCompressor(AbsProcessor compressor) {
        this.compressor = compressor;
    }

    public ImageCompressor with(Config config) {
        this.config = config;
        return this;
    }

    public CompressResult compress() throws CompressException {
        if (compressor == null) {
            throw new CompressException("compressor null");
        }
        compressor.setConfig(config);
        return new CompressResult(config, compressor.compress());
    }
}
