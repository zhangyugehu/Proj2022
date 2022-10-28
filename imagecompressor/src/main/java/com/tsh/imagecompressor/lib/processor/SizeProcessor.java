package com.tsh.imagecompressor.lib.processor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tsh.imagecompressor.Global;
import com.tsh.imagecompressor.lib.CompressException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 文件大小压缩处理器
 *
 * @author hutianhang
 */
public class SizeProcessor extends AbsProcessor {

    private static final long MB = 1024 * 1024;
    private static final long KB = 1024;
    private static final long SIZE_EXTRA_LARGE = 10L * MB;
    private static final long SIZE_LARGE = 2L * MB;
    private static final long SIZE_MEDIUM = MB;
    private static final long SIZE_SMALL = 512 * KB;

    private long dstSize = -1;
    private byte[] bytesData;

    public SizeProcessor toSize(long size) {
        dstSize = size;
        return this;
    }

    public SizeProcessor toSmallSize() {
        return toSize(SIZE_SMALL);
    }

    public SizeProcessor toMediumSize() {
        return toSize(SIZE_MEDIUM);
    }

    public SizeProcessor toLargeSize() {
        return toSize(SIZE_LARGE);
    }

    public SizeProcessor toExtraLargeSize() {
        return toSize(SIZE_EXTRA_LARGE);
    }

    @Override
    public Bitmap compress() throws CompressException {
        if (dstSize <= 0) {
            throw new CompressException("targetSize must > 0");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap result = null;
        Log.i("MainActivityTag", "except to: " + Global.readableSize(dstSize));
        while (bytesData == null || bytesData.length > dstSize) {
            result = handleSourceStream(is -> BitmapFactory.decodeStream(is, null, options));
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                result.compress(config.getFormat(), config.getQuality(), bos);
                bytesData = bos.toByteArray();
                if (options.inSampleSize == 1) {
                    // 第一次根据大小计算大概的采样率
                    options.inSampleSize = (int) Math.sqrt(bytesData.length / (dstSize * 1.0f));
                }
            } catch (IOException e) {
                throw new CompressException("", e);
            }
            Log.i("MainActivityTag",
                    "bytesData.length: " + Global.readableSize(bytesData.length) +
                    ", inSampleSize: " + options.inSampleSize +
                    ", bitmap size: " + result.getWidth() + "x" + result.getHeight()
            );
            // 不符合要求时进一步压缩采样率
            options.inSampleSize += 1;
        }
        return result;
    }
}
