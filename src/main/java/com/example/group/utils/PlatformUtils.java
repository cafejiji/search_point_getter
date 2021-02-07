package com.example.group.utils;

/**
 * Platform utility
 * 
 * @author k.yanagida
 *
 */
public final class PlatformUtils {

	private static final String OS_NAME = System.getProperty("os.name")
			.toLowerCase();

	private PlatformUtils() {
	}

	/**
	 * Check OS whether the Windows or not
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		return OS_NAME.startsWith("windows");
	}

	/**
	 * Check OS whether the MacOS or not
	 * 
	 * @return
	 */
	public static boolean isMac() {
		return OS_NAME.startsWith("mac");
	}

	/**
	 * Check OS whether the Linux or not
	 * 
	 * @return
	 */
	public static boolean isLinux() {
		return OS_NAME.startsWith("linux");
	}

}
