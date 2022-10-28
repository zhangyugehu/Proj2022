package com.tsh.imagecompressor.lib;

import android.graphics.Bitmap;

/**
 * @author hutianhang
 */
public class Config extends ConfigConstant {

    public static class Builder extends ConfigConstant {

        public Builder setFormat(Bitmap.CompressFormat format) {
            this.format = format;
            return this;
        }

        public Builder setStreamProvider(StreamProvider streamProvider) {
            this.streamProvider = streamProvider;
            return this;
        }

        public Builder setQuality(int quality) {
            this.quality = quality;
            return this;
        }

        public Config build() {
            Config config = new Config();
            config.streamProvider = streamProvider;
            config.format = format;
            config.quality = quality;
            return config;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder upon() {
        return Config.newBuilder()
                .setFormat(format)
                .setQuality(quality)
                .setStreamProvider(streamProvider);
    }

    private Config() {
    }

    public void setStreamProvider(StreamProvider provider) {
        this.streamProvider = provider;
    }

    public StreamProvider getStreamProvider() {
        return streamProvider;
    }

    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    public int getQuality() {
        return quality;
    }
}
