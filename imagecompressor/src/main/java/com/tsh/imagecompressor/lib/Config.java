package com.tsh.imagecompressor.lib;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.InputStream;

/**
 * @author hutianhang
 */
public class Config extends ConfigMember {

    public static class Builder extends ConfigMember {

        public Builder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }


        public Builder setFormat(Bitmap.CompressFormat format) {
            this.format = format;
            return this;
        }

        public Builder setStreamer(Streamer streamer) {
            this.streamer = streamer;
            return this;
        }

        public Builder setQuality(int quality) {
            this.quality = quality;
            return this;
        }

        public Config build() {
            Config config = new Config();
            config.uri = uri;
            config.streamer = streamer;
            config.format = format;
            config.quality = quality;
            return config;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Config() {
    }

    public Uri getUri() {
        return uri;
    }

    public Streamer getStreamer() {
        return streamer;
    }

    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    public int getQuality() {
        return quality;
    }
}
