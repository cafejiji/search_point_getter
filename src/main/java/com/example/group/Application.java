package com.example.group;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.example.group.models.Site;
import com.example.group.models.db.SQLite;
import com.example.group.models.db.dao.SearchHistoryDAO;
import com.example.group.utils.AppConfig;
import com.example.group.utils.AppLogger;
import com.example.group.utils.AppUtils;

/**
 * Get search point automatically
 * 
 * @author k.yanagida
 *
 */
public class Application {

	private static SearchHistoryDAO dao;

	private static final int CONCURRENT_THREADS = 10;

	static {
		dao = new SearchHistoryDAO();
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final long startTime = System.currentTimeMillis();

		try {

			AppLogger.info("start processing");

			if (!AppUtils.isValidEnvironmeent()) {
				AppLogger.warn("invalid platform");
				return;
			}

			// initialize
			AppConfig.initialize();

			// get current date of string
			final String currentDateStr = AppUtils.getFormattedDateStr(
					AppUtils.getToday(), AppUtils.DATE_FORMAT);

			// delete histories of search until yesterday
			dao.deleteSearchHistory(currentDateStr);

			// execute main process
			execute(currentDateStr);

		} catch (final Throwable t) {
			AppLogger.error(t, "error occurred during application process");
		} finally {

			SQLite.close();
			final long endTime = System.currentTimeMillis();
			AppLogger.info("finished all processing. took:"
					+ (endTime - startTime) + "ms");
		}
	}

	/**
	 * Execute main process
	 * 
	 * @param currentDateStr
	 * @throws Exception
	 */
	private static void execute(final String currentDateStr) throws Exception {

		final List<Site> sites = AppConfig.getSiteList().getSites();
		final List<Thread> threads = new ArrayList<Thread>();
		for (final Site site : sites) {
			final Thread thread = new Thread(new Process(new Semaphore(
					CONCURRENT_THREADS), dao, site, currentDateStr));
			threads.add(thread);
			thread.start();
		}

		for (final Thread thread : threads) {
			thread.join();
		}

	}
}
