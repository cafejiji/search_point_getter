package com.example.group;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.FastArrayList;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.group.models.Site;
import com.example.group.models.WordList;
import com.example.group.models.db.dao.SearchHistoryDAO;
import com.example.group.utils.AppConfig;
import com.example.group.utils.AppLogger;
import com.example.group.utils.AppUtils;
import com.example.group.utils.PlatformUtils;

/**
 * Main process
 * 
 * @author k.yanagida
 *
 */
public class Process implements Runnable {

	private Semaphore semaphore;
	private SearchHistoryDAO dao;

	private Site site;
	private String currentDateStr;

	private static final int SLEEP_SECONDS = 1;

	/**
	 * Constructor
	 * 
	 * @param semaphore
	 * @param dao
	 * @param site
	 * @param currentDateStr
	 */
	public Process(final Semaphore semaphore, final SearchHistoryDAO dao,
			final Site site, final String currentDateStr) {
		this.semaphore = semaphore;
		this.site = site;
		this.currentDateStr = currentDateStr;
		this.dao = dao;
	}

	/**
	 * run process
	 */
	public void run() {

		final long startTime = System.currentTimeMillis();

		WebDriver driver = null;
		String threadName = null;
		String name = null;

		try {
			semaphore.acquire();
			TimeUnit.SECONDS.sleep(SLEEP_SECONDS);

			threadName = AppLogger.getPrefix(Thread.currentThread().getName());
			name = AppLogger.getPrefix(site.getName());

			final String currentTimeStr = AppUtils.getFormattedDateStr(Calendar
					.getInstance().getTime(), AppUtils.TIME_FORMAT);

			// get a count of search number
			final int searchCount = dao.getSearchCount(name, currentDateStr);
			AppLogger.info(threadName, name, " is searched the ", searchCount);
			if (searchCount >= site.getLimit()) {
				AppLogger.warn(threadName, name,
						" is reached to limit of searching already");
				return;
			}

			// get a time of latest searched
			final String latestSearchTime = dao.getLatestSearchTime(name);
			AppLogger.info(threadName, name, " latest search time is ",
					latestSearchTime);
			if (!StringUtils.isEmpty(latestSearchTime)
					&& site.getInterval() > 0
					&& site.getInterval() > AppUtils.getDifferenceMinute(
							currentTimeStr, latestSearchTime)) {
				AppLogger.warn(threadName, name, " doesn't pass interval yet");
				return;
			}

			driver = getDriver();
			driver.get(site.getLoginUrl());
			getDynamicElement(driver, site.getXpathOfLoginIdText()).sendKeys(
					site.getLoginId());
			getDynamicElement(driver, site.getXpathOfPasswordText()).sendKeys(
					site.getPassword());
			getDynamicElement(driver, site.getXpathOfLoginButton()).click();

			driver.get(site.getSearchUrl());

			final String keyword = decideWord(AppConfig.getWordList());

			AppLogger.info(threadName, name, " search word is ", keyword);

			getDynamicElement(driver, site.getXpathOfSearchText()).sendKeys(
					keyword);
			getDynamicElement(driver, site.getXpathOfSearchButton()).click();

			// insert history of search
			dao.insertSearchHistory(name, currentTimeStr);

		} catch (final Exception e) {
			AppLogger.error(e, threadName, name,
					" An unexpected error has occurred");
		} finally {
			if (driver != null) {
				driver.close();
				driver.quit();
			}
			final long endTime = System.currentTimeMillis();
			AppLogger.info(threadName, name, " finished processing. took:"
					+ (endTime - startTime) + "ms");
			semaphore.release();
		}

	}

	/**
	 * Decide word to search from word.json
	 * 
	 * @param wordList
	 * @return String
	 */
	private static String decideWord(final WordList wordList) {
		final FastArrayList words = wordList.getWords();
		return words.get(new Random().nextInt(words.size())).toString();
	}

	/**
	 * Get element which is appeared dynamically
	 * 
	 * @param driver
	 * @param xpath
	 * @return WebElement
	 */
	private static WebElement getDynamicElement(final WebDriver driver,
			final String xpath) {
		return (new WebDriverWait(driver, 30))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(final WebDriver d) {
						return (driver.findElement(By.xpath(xpath)));
					}
				});
	}

	/**
	 * Get PhantomJSDriver for now
	 * 
	 * @return WebDriver
	 */
	private static WebDriver getDriver() {
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities
				.setCapability(
						PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
						(AppUtils.getResourcePath(PlatformUtils.isWindows() ? "phantomjs.exe"
								: "phantomjs")));
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
				new String[] { "--ignore-ssl-errors=true",
						"--webdriver-loglevel=NONE" });
		return new PhantomJSDriver(capabilities);
	}
}
