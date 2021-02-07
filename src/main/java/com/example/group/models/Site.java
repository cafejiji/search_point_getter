package com.example.group.models;

/**
 * Data class for site
 * 
 * @author k.yanagida
 *
 */
public class Site {

	private String name;
	private String loginUrl;
	private String loginId;
	private String password;
	private String xpathOfLoginIdText;
	private String xpathOfPasswordText;
	private String xpathOfLoginButton;
	private String searchUrl;
	private int limit;
	private int interval;
	private String xpathOfSearchText;
	private String xpathOfSearchButton;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(final String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(final String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getXpathOfLoginIdText() {
		return xpathOfLoginIdText;
	}

	public void setXpathOfLoginIdText(final String xpathOfLoginIdText) {
		this.xpathOfLoginIdText = xpathOfLoginIdText;
	}

	public String getXpathOfPasswordText() {
		return xpathOfPasswordText;
	}

	public void setXpathOfPasswordText(final String xpathOfPasswordText) {
		this.xpathOfPasswordText = xpathOfPasswordText;
	}

	public String getXpathOfLoginButton() {
		return xpathOfLoginButton;
	}

	public void setXpathOfLoginButton(final String xpathOfLoginButton) {
		this.xpathOfLoginButton = xpathOfLoginButton;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(final String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(final int limit) {
		this.limit = limit;
	}

	public String getXpathOfSearchText() {
		return xpathOfSearchText;
	}

	public void setXpathOfSearchText(final String xpathOfSearchText) {
		this.xpathOfSearchText = xpathOfSearchText;
	}

	public String getXpathOfSearchButton() {
		return xpathOfSearchButton;
	}

	public void setXpathOfSearchButton(final String xpathOfSearchButton) {
		this.xpathOfSearchButton = xpathOfSearchButton;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(final int interval) {
		this.interval = interval;
	}

}
