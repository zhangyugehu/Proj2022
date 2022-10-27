package com.tsh.imagecompressor.lib;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.InputStream;

/**
 * @author hutianhang
 */
public class ConfigMember {
    protected Uri uri;
    protected Streamer streamer;
    protected Bitmap.CompressFormat format;
    protected int quality = 70;
}
