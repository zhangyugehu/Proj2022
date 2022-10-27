package com.tsh.imagecompressor.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author hutianhang
 */
public class DimensionProcessor extends AbsProcessor {

    private static final int DIMENSIONS_LAGER = 4096;
    private static final int DIMENSIONS_MEDIUM = 2048;
    private static final int DIMENSIONS_SMALL = 1024;

    private int dimension = -1;

    public DimensionProcessor toDimension(int dimension) {
        this.dimension = dimension;
        return this;
    }

    public DimensionProcessor toSmallDimension() {
        return toDimension(DIMENSIONS_SMALL);
    }

    public DimensionProcessor toMediumDimension() {
        return toDimension(DIMENSIONS_MEDIUM);
    }

    public DimensionProcessor toLargeDimension() {
        return toDimension(DIMENSIONS_LAGER);
    }

    public DimensionProcessor(Context context) {
        super(context);
    }

    @Override
    Bitmap compress() throws CompressException {
        if (dimension <= 0) {
            throw new CompressException("dimension must > 0");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        handleSourceStream(is -> BitmapFactory.decodeStream(is, null, options));
        int sourceDimension = Math.max(options.outHeight, options.outWidth);
        if (sourceDimension <= dimension) {
            return handleSourceStream(BitmapFactory::decodeStream);
        } else {
            options.inJustDecodeBounds = false;
            options.inSampleSize = sourceDimension / dimension + 1;
            return handleSourceStream(is -> BitmapFactory.decodeStream(is, null, options));
        }
    }
}
