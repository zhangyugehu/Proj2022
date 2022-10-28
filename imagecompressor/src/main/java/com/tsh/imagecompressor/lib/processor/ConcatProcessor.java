package com.tsh.imagecompressor.lib.processor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tsh.imagecompressor.lib.CompressException;
import com.tsh.imagecompressor.lib.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author hutianhang
 */
public class ConcatProcessor extends AbsProcessor {

    private final AbsProcessor[] processors;

    public ConcatProcessor(AbsProcessor... processors) {
        this.processors = processors;
    }

    @Override
    public Bitmap compress() throws CompressException {
        // 给子processor设置config
        Config currentConfig = config;
        for (AbsProcessor processor : processors) {
            currentConfig = config.upon().build();
            processor.setConfig(currentConfig);
            Bitmap bitmap = processor.compress();
            Config finalCurrentConfig = currentConfig;
            currentConfig.setStreamProvider(() -> {
                try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                    bitmap.compress(finalCurrentConfig.getFormat(), finalCurrentConfig.getQuality(), bos);
                    return new ByteArrayInputStream(bos.toByteArray());
                }
            });
        }
        try {
            // 使用最后处理玩的流输出
            return BitmapFactory.decodeStream(currentConfig.getStreamProvider().openStream());
        } catch (IOException e) {
            throw new CompressException("", e);
        }
    }
}
