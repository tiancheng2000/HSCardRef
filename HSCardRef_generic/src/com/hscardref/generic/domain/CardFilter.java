package com.hscardref.generic.domain;

/**
 * Card Filter class for filtering Card list.  
 */
public interface CardFilter {

	public boolean accept(Card card);

}
