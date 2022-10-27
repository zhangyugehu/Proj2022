package com.tsh.imagecompressor.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author hutianhang
 */
public class SizeCompressor extends AbsProcessor {

    private static final long MB = 1024 * 1024;
    private static final long KB = 1024;
    private static final long SIZE_EXTRA_LARGE = 10L * MB;
    private static final long SIZE_LARGE = 2L * MB;
    private static final long SIZE_MEDIUM = MB;
    private static final long SIZE_SMALL = 512 * KB;

    private long dstSize = -1;

    public SizeCompressor(Context context) {
        super(context);
    }

    public SizeCompressor toSize(long size) {
        dstSize = size;
        return this;
    }

    public SizeCompressor toSmallSize() {
        return toSize(SIZE_SMALL);
    }

    public SizeCompressor toMediumSize() {
        return toSize(SIZE_MEDIUM);
    }

    public SizeCompressor toLargeSize() {
        return toSize(SIZE_LARGE);
    }

    public SizeCompressor toExtraLargeSize() {
        return toSize(SIZE_EXTRA_LARGE);
    }

    @Override
    Bitmap compress() throws CompressException {
        if (dstSize <= 0) {
            throw new CompressException("targetSize must > 0");
        }
        File tmpFile = new File(context.getCacheDir(), "compress.tmp");
        boolean delete = tmpFile.delete();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap result = null;
        long baseSize = -1;
        while (tmpFile.length() == 0 || tmpFile.length() > dstSize) {
            result = handleSourceStream(is -> BitmapFactory.decodeStream(is, null, options));
            try (OutputStream os = new FileOutputStream((tmpFile))) {
                result.compress(config.getFormat(), config.getQuality(), os);
                if (options.inSampleSize == 1) {
                    baseSize = tmpFile.length();
                } else {
                    Log.i("MainActivityTag", "inSample: " + (tmpFile.length() / (baseSize * 1.0f)));
                }
            } catch (IOException e) {
                throw new CompressException("", e);
            }
            Log.i("MainActivityTag", "compress: " +
                    Formatter.formatFileSize(context, tmpFile.length()) + ", " +
                    (tmpFile.length() / dstSize) + ", " + options.inSampleSize +
                    ", " + baseSize / (tmpFile.length() * 1.0f)
            );
            options.inSampleSize += 1;
        }
        return result;
    }
}
