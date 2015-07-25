package com.netease.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class IOUtils {
	/**
     * Constructor is private to prevent instantiation.
     */
	private IOUtils() {}
	
	public static void closeInputStream(InputStream in) {
		try {
			if(in != null) {
				in.close();
			}
		} catch (IOException ioe) {
			// Normal to ignore it.
		}
	}
	
	public static void closeOutputStream(OutputStream out) {
		try {
			if(out != null) {
				out.close();
			}
		} catch (IOException ioe) {
			// Normal to ignore it.
		}
	}
}
