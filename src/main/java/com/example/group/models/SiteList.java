package com.example.group.models;

import java.util.List;

/**
 * Data class for site list
 * 
 * @author k.yanagida
 *
 */
public class SiteList implements ConfigList {

	private List<Site> sites;

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(final List<Site> sites) {
		this.sites = sites;
	}

}
