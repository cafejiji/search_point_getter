package com.example.group.models.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;

import com.example.group.enums.OperationType;
import com.example.group.enums.Table;
import com.example.group.utils.AppLogger;

/**
 * Control about SQLite
 * 
 * @author k.yanagida
 *
 */
public class SQLite {

	private static SQLite sqlite;
	private static Connection connection;
	private static Statement statement;

	/**
	 * Get itself's instance
	 * 
	 * @return SQLite
	 */
	private static SQLite getInstance() {
		return sqlite == null ? new SQLite() : sqlite;
	}

	/**
	 * Get connection of database
	 * 
	 * @return Connection
	 */
	private static Connection getSqliteConnection() {
		return getInstance().getConnection();
	}

	/**
	 * Get connection of database
	 * 
	 * @return Connection
	 */
	private Connection getConnection() {
		return connection;
	}

	/**
	 * Constructor
	 */
	private SQLite() {

		if (connection != null) {
			return;
		}

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(StringUtils.join(
					"jdbc:sqlite:/", System.getProperty("user.dir"),
					"/data/example"));
			for (final Table table : Table.values()) {
				if ((Integer) select(
						"select count(*) from sqlite_master WHERE type='table' AND name='",
						table.toString(), "'") == 0) {
					update("create table ", table.toString(), " (",
							table.getColumnsDefinition(), ");");
				}
			}

		} catch (final Exception e) {
			AppLogger.error(e, "database initialize error");
		}
	}

	/**
	 * Update database
	 * 
	 * @param sqlParts
	 */
	public static synchronized int update(final String... sqlParts) {
		return (Integer) execute(OperationType.UPDATE, sqlParts);
	}

	/**
	 * Select single value
	 * 
	 * @param sqlParts
	 * @return Object
	 */
	public static synchronized Object select(final String... sqlParts) {
		return execute(OperationType.SELECT, sqlParts);
	}

	/**
	 * Close connection of database
	 */
	public static void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (final Exception e) {
			AppLogger.error(e, "connection close error");
		}
	}

	/**
	 * Execute SQL
	 * 
	 * @param type
	 * @param sqlParts
	 * @return Object
	 */
	private static synchronized Object execute(final OperationType type,
			final String... sqlParts) {
		try {
			final String sql = StringUtils.join(sqlParts);
			statement = getSqliteConnection().createStatement();
			if (type == OperationType.UPDATE) {
				return statement.executeUpdate(sql);
			} else if (type == OperationType.SELECT) {
				return statement.executeQuery(sql).getObject(1);
			} else {
				return null;
			}
		} catch (final Exception e) {
			AppLogger.error(e, "database operation error");
			return null;
		} finally {
			try {
				statement.close();
			} catch (final SQLException e) {
				AppLogger.error(e, "statement close error");
			}
		}
	}
}
