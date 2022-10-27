package com.tsh.imagecompressor.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author hutianhang
 */
public class QuickSizeProcessor extends AbsProcessor {

    private static final long MB = 1024 * 1024;
    private static final long KB = 1024;
    private static final long SIZE_EXTRA_LARGE = 10L * MB;
    private static final long SIZE_LARGE = 2L * MB;
    private static final long SIZE_MEDIUM = MB;
    private static final long SIZE_SMALL = 512 * KB;

    private long dstSize = -1;

    public QuickSizeProcessor(Context context) {
        super(context);
    }

    public QuickSizeProcessor toSize(long size) {
        dstSize = size;
        return this;
    }

    public QuickSizeProcessor toSmallSize() {
        return toSize(SIZE_SMALL);
    }

    public QuickSizeProcessor toMediumSize() {
        return toSize(SIZE_MEDIUM);
    }

    public QuickSizeProcessor toLargeSize() {
        return toSize(SIZE_LARGE);
    }

    public QuickSizeProcessor toExtraLargeSize() {
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
        Log.i("MainActivityTag", "except to: " + Formatter.formatFileSize(context, dstSize));
        while (tmpFile.length() == 0 || tmpFile.length() > dstSize) {
            result = handleSourceStream(is -> BitmapFactory.decodeStream(is, null, options));
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream((tmpFile)))) {
                result.compress(config.getFormat(), config.getQuality(), bos);
                if (options.inSampleSize == 1) {
                    options.inSampleSize = (int) Math.sqrt(tmpFile.length() / (dstSize * 1.0f));
                }
            } catch (IOException e) {
                throw new CompressException("", e);
            }
            Log.i("MainActivityTag",
                    "tmpFile.length: " + Formatter.formatFileSize(context, tmpFile.length()) +
                    ", inSampleSize: " + options.inSampleSize +
                    ", bitmap size: " + result.getWidth() + "x" + result.getHeight()
            );
            options.inSampleSize += 1;
        }
        return result;
    }
}
