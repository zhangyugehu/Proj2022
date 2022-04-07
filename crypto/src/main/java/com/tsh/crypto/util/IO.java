package com.tsh.crypto.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hutianhang
 */
public class IO {

    /**
     * 32 KB
     */
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
    /**
     * 500 Kb
     */
    public static final int DEFAULT_IMAGE_TOTAL_SIZE = 500 * 1024;

    public interface Listener {
        /**
         *
         * @param cur
         * @param total
         * @param completed
         * @return
         */
        boolean onProgress(long cur, long total, boolean completed);
    }

    public interface OnBytesHandler {
        /**
         *
         * @param bytes
         */
        void onBytes(byte[] bytes) throws Exception;
    }

    public static void copy(InputStream is, OutputStream os, Listener listener, OnBytesHandler handler, int bufferSize) throws IOException {
        try {
            if (bufferSize == 0) {
                bufferSize = DEFAULT_BUFFER_SIZE;
            }
            long current = 0L;
            long total = is.available();
            if (total <= 0) {
                total = DEFAULT_IMAGE_TOTAL_SIZE;
            }

            if (listener != null && listener.onProgress(current, total, false)) {
                return;
            }
            final byte[] bytes = new byte[bufferSize];
            int count;
            while ((count = is.read(bytes, 0, bufferSize)) != -1) {
                if (handler != null) {
                    handler.onBytes(bytes);
                }
                os.write(bytes, 0, count);
                current += count;
                if (listener != null && listener.onProgress(current, total, false)) {
                    return;
                }
            }
            os.flush();
            if (listener != null) {
                listener.onProgress(current, total, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onProgress(-1, -1, true);
            }
        } finally {
            close(is, os);
        }
    }

    public static String readAsString(File file) {
        return readAsString(file, false);
    }

    public static String readAsString(File file, boolean newLine) {
        FileReader fr = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (!firstLine && newLine) {
                    sb.append("\n");
                }
                sb.append(line);
                firstLine = false;
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            close(fr, reader);
        }
    }

    public static void writeToFile(String content, File file) {
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            if (file.exists()) {
                boolean deleteSuccess = file.delete();
            }
            boolean createSuccess = file.createNewFile();
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fw, writer);
        }
    }

    public static void writeToFile(byte[] content, File to) throws IOException {
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        try {
            copy((bais = new ByteArrayInputStream(content)), (fos = new FileOutputStream(to)), null, null, 0);
        } finally {
            close(bais, fos);
        }
    }

    public static byte[] readAsByteArray(File from) throws IOException {
        long length = from.length();
        if (length <= 0) {
            return new byte[0];
        }
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        try {
            copy((fis = new FileInputStream(from)), (baos = new ByteArrayOutputStream((int) length)), null, null, 0);
            return baos.toByteArray();
        } finally {
            close(fis, baos);
        }
    }

    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
