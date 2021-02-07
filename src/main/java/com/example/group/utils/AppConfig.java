package com.example.group.utils;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;

import com.example.group.Application;
import com.example.group.models.ConfigList;
import com.example.group.models.SiteList;
import com.example.group.models.WordList;

/**
 * Application configuration
 *
 * @author k.yanagida
 */
public final class AppConfig {

	private static SiteList siteList;
	private static WordList wordList;

	private AppConfig() {
	}

	/**
	 * initialize
	 */
	public synchronized static void initialize() {
		if (siteList != null) {
			return;
		}
		wordList = (WordList) getConfigList("word.json", WordList.class);
		siteList = (SiteList) getConfigList("site.json", SiteList.class);

	}

	/**
	 * Get configuration list for general
	 *
	 * @param fileName
	 * @param type
	 * @return ConfigList
	 */
	@SuppressWarnings("unchecked")
	private static ConfigList getConfigList(final String fileName,
			@SuppressWarnings("rawtypes") final Class type) {
		try {
			final ConfigList configList = (ConfigList) new ObjectMapper()
					.readValue(new File(Application.class.getClassLoader()
							.getResource(fileName).getPath()), type);
			return configList;
		} catch (final Throwable t) {
			AppLogger.error(t, "error occurred during configuration process");
			return null;
		}
	}

	public static SiteList getSiteList() {
		return siteList;
	}

	public static WordList getWordList() {
		return wordList;
	}

}
