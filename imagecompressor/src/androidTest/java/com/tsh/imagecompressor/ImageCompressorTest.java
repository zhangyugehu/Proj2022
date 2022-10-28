package com.tsh.imagecompressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.tsh.imagecompressor.lib.CompressException;
import com.tsh.imagecompressor.lib.Config;
import com.tsh.imagecompressor.lib.processor.DimensionProcessor;
import com.tsh.imagecompressor.lib.ImageCompressor;
import com.tsh.imagecompressor.lib.processor.SizeProcessor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class ImageCompressorTest {

    @Test
    public void compressSizeTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        File resultFile = new File(appContext.getExternalFilesDir(Environment.DIRECTORY_DCIM), "result");
        try {
            ImageCompressor.CompressResult result = ImageCompressor
                    .withConfig(Config.newBuilder()
                            .setFormat(Bitmap.CompressFormat.JPEG)
                            .setStreamProvider(() -> appContext.getAssets().open("p1.jpg"))
                            .build())
                    .byProcessor(new SizeProcessor().toSmallSize())
                    .compress();
            result.toFile(resultFile);
        } catch (CompressException e) {
            e.printStackTrace();
        } finally {
            Assert.assertTrue(resultFile.exists() && resultFile.length() <= 512 * 1024);
        }
    }

    @Test
    public void compressDimensionTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ImageCompressor.CompressResult result = null;
        try {
            result = ImageCompressor
                    .withConfig(Config.newBuilder()
                            .setFormat(Bitmap.CompressFormat.JPEG)
                            .setStreamProvider(() -> appContext.getAssets().open("p1.jpg"))
                            .build())
                    .byProcessor(new DimensionProcessor().toSmallDimension())
                    .compress();
        } catch (CompressException e) {
            e.printStackTrace();
        } finally {
            Bitmap resultBitmap;
            Assert.assertTrue(result != null
                    && (resultBitmap = result.toBitmap()) != null
                    && Math.max(resultBitmap.getWidth(), resultBitmap.getHeight()) <= 1024);
        }
    }
}
