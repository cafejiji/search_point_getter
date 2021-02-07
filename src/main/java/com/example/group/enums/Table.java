package com.example.group.enums;

/**
 * Database table definition
 * 
 * @author k.yanagida
 *
 */
public enum Table {

	SEARCH_HISTORY(
			new String[][] { { "name", "string" }, { "date", "string" } });

	private String[][] columns;

	private Table(final String[][] columns) {
		this.columns = columns;
	}

	/**
	 * Get columns
	 * 
	 * @return String[][]
	 */
	public String[][] getColumns() {
		return columns;
	}

	/**
	 * Get columns definition for create statement
	 * 
	 * @return String
	 */
	public String getColumnsDefinition() {
		final StringBuilder str = new StringBuilder();
		for (final String[] column : columns) {
			str.append(column[0]).append(" ");
			str.append(column[1]).append(", ");
		}
		return str.delete(str.lastIndexOf(","), str.length()).toString();
	}

}
