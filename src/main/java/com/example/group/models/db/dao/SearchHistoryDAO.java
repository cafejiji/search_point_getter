package com.example.group.models.db.dao;

import com.example.group.enums.Table;
import com.example.group.models.db.SQLite;

/**
 * Access the search history
 * 
 * @author k.yanagida
 *
 */
public class SearchHistoryDAO {

	private static final String TABLE_NAME = Table.SEARCH_HISTORY.toString();

	/**
	 * Get a count of search number in specific day
	 * 
	 * @param name
	 * @param date
	 * @return Integer
	 */
	public Integer getSearchCount(final String name, final String date) {
		return (Integer) SQLite.select("select count(*) from ", TABLE_NAME,
				" where name = '", name, "' and date like '", date, "%';");
	}

	/**
	 * Get latest search time
	 * 
	 * @param name
	 * @return String
	 */
	public String getLatestSearchTime(final String name) {
		return (String) SQLite.select("select max(date) from ", TABLE_NAME,
				" where name = '", name, "';");
	}

	/**
	 * Delete histories of search until specific day
	 * 
	 * @param date
	 * @return Integer
	 */
	public Integer deleteSearchHistory(final String date) {
		return SQLite.update("delete from ", TABLE_NAME, " where date < '",
				date, "';");
	}

	/**
	 * Insert history of search
	 * 
	 * @param name
	 * @param date
	 * @return Integer
	 */
	public Integer insertSearchHistory(final String name, final String date) {
		return SQLite.update("insert into ", TABLE_NAME,
				" (name, date) values('", name, "', '", date, "');");
	}

}
