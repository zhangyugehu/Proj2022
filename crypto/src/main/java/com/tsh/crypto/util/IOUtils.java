package com.tsh.crypto.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides I/O operations
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class IOUtils {

	/** {@value} */
	public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 KB
	/** {@value} */
	public static final int DEFAULT_IMAGE_TOTAL_SIZE = 500 * 1024; // 500 Kb
	/** {@value} */
	public static final int CONTINUE_LOADING_PERCENTAGE = 75;

	private IOUtils() {
	}

	/**
	 * Copies stream, fires progress events by listener, can be interrupted by listener. Uses buffer size =
	 * {@value #DEFAULT_BUFFER_SIZE} bytes.
	 *
	 * @param is       Input stream
	 * @param os       Output stream
	 * @param listener null-ok; Listener of copying progress and controller of copying interrupting
	 * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
	 * @throws IOException
	 */
	public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener) throws IOException {
		return copyStream(is, os, listener, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Copies stream, fires progress events by listener, can be interrupted by listener.
	 *
	 * @param is         Input stream
	 * @param os         Output stream
	 * @param listener   null-ok; Listener of copying progress and controller of copying interrupting
	 * @param bufferSize Buffer size for copying, also represents a step for firing progress listener callback, i.e.
	 *                   progress event will be fired after every copied <b>bufferSize</b> bytes
	 * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
	 * @throws IOException
	 */
	public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener, int bufferSize)
			throws IOException {
		int current = 0;
		int total = is.available();
		if (total <= 0) {
			total = DEFAULT_IMAGE_TOTAL_SIZE;
		}

		final byte[] bytes = new byte[bufferSize];
		int count;
		if (shouldStopLoading(listener, current, total)) return false;
		while ((count = is.read(bytes, 0, bufferSize)) != -1) {
			os.write(bytes, 0, count);
			current += count;
			if (shouldStopLoading(listener, current, total)) return false;
		}
		os.flush();
		return true;
	}

	private static boolean shouldStopLoading(CopyListener listener, int current, int total) {
		if (listener != null) {
			boolean shouldContinue = listener.onBytesCopied(current, total);
			if (!shouldContinue) {
				if (100 * current / total < CONTINUE_LOADING_PERCENTAGE) {
					return true; // if loaded more than 75% then continue loading anyway
				}
			}
		}
		return false;
	}

	/**
	 * Reads all data from stream and close it silently
	 *
	 * @param is Input stream
	 */
	public static void readAndCloseStream(InputStream is) {
		final byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
		try {
			while (is.read(bytes, 0, DEFAULT_BUFFER_SIZE) != -1);
		} catch (IOException ignored) {
		} finally {
			closeSilently(is);
		}
	}

	public static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ignored) {
			}
		}
	}

    /** Listener and controller for copy process */
	public static interface CopyListener {
		/**
		 * @param current Loaded bytes
		 * @param total   Total bytes for loading
		 * @return <b>true</b> - if copying should be continued; <b>false</b> - if copying should be interrupted
		 */
		boolean onBytesCopied(int current, int total);
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
			closeSilently(fr);
			closeSilently(reader);
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
			closeSilently(fw);
			closeSilently(writer);
		}
	}

}
