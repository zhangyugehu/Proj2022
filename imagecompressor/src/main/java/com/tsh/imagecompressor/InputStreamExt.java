package com.tsh.imagecompressor;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamExt {

    private static final byte[] BUFFER = new byte[1024 * 1024];

    public static void copy(@NonNull InputStream is, @NonNull OutputStream os) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(is);
             BufferedOutputStream bos = new BufferedOutputStream(os)) {
            int read;
            while ((read = bis.read(BUFFER)) != -1) {
                bos.write(BUFFER, 0, read);
            }
        }
    }
}
