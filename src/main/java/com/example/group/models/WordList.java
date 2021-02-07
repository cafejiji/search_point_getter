package com.example.group.models;

import org.apache.commons.collections.FastArrayList;

/**
 * Data class for word list
 * 
 * @author k.yanagida
 *
 */
public class WordList implements ConfigList {

	private FastArrayList words;

	public FastArrayList getWords() {
		return words;
	}

	public void setWords(final FastArrayList words) {
		this.words = words;
	}

}
