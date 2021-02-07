package com.example.group.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application logger
 * 
 * @author k.yanagida
 *
 */
public final class AppLogger {

	public static final Logger logger = LoggerFactory.getLogger("all.logger");

	private AppLogger() {
	}

	/**
	 * Output info log
	 * 
	 * @param messages
	 */
	public static void info(final Object... messages) {
		logger.info(StringUtils.join(messages));
	}

	/**
	 * Output warn log
	 * 
	 * @param messages
	 */
	public static void warn(final Object... messages) {
		logger.warn(StringUtils.join(messages));
	}

	/**
	 * Output error log
	 * 
	 * @param t
	 * @param messages
	 */
	public static void error(final Throwable t, final Object... messages) {
		logger.error(StringUtils.join(messages), t);
	}

	/**
	 * Get prefix for log
	 * 
	 * @param prefix
	 * @return String
	 */
	public static String getPrefix(final Object prefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(prefix);
		sb.append("]");
		return sb.toString();
	}

}
