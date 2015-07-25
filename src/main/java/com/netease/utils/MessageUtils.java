package com.netease.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageUtils {
	private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	private static Properties prop;
	
	static {
		InputStream in = null;
		Reader reader = null;
		try {
			prop = new Properties();
			String path = MessageUtils.class.getResource("/").getPath() + "message.constants";
			in = new FileInputStream(path);
			reader = new InputStreamReader(in, "UTF-8");
			prop.load(reader);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupport encoding UTF-8", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			IOUtils.closeReader(reader);
			IOUtils.closeInputStream(in);
		}
	}

	public static String getMessage(String msgKey) {
		return (String) prop.getProperty(msgKey);
	}
}