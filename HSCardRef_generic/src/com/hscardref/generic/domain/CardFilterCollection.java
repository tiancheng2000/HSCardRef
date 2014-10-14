package com.hscardref.generic.domain;

public class CardFilterCollection {
	//-- Constants and Enums -----------------------------------
	public static final String TAG = CardFilterCollection.class.getSimpleName();

	//-- Delegates and Events ----------------------------------
	//-- Instance and Shared Fields ----------------------------
	private CardNumericFilter		_cardNumericFilter;
	private CardCompositeFilter 	_cardCompositeFilter;
	
	//-- Constructors ------------------------------------------
	public CardFilterCollection() {
		_cardNumericFilter = new CardNumericFilter(0, -1);
		_cardCompositeFilter = null;
	}

	public CardFilterCollection(CardNumericFilter cardNumericFilter, CardCompositeFilter cardCompositeFilter) {
		_cardNumericFilter = cardNumericFilter;
		_cardCompositeFilter = cardCompositeFilter;
	}
	
	//-- Public and internal Methods ---------------------------
	public boolean accept(Card card){
		boolean result = false;
		
		if (_cardCompositeFilter != null) {
			result = _cardCompositeFilter.accept(card);
			if (!result) {
				return false;
			}
		}
		
		result = _cardNumericFilter.accept(card);

		return result;
	}

	//-- Properties --------------------------------------------

	/**
	 * @return the _cardNumericFilter
	 */
	public CardNumericFilter get_cardNumericFilter() {
		return _cardNumericFilter;
	}

	/**
	 * @param _cardNumericFilter the _cardNumericFilter to set
	 */
	public void set_cardNumericFilter(CardNumericFilter _cardNumericFilter) {
		this._cardNumericFilter = _cardNumericFilter;
	}

	/**
	 * @return the _cardCompositeFilter
	 */
	public CardCompositeFilter get_cardCompositeFilter() {
		return _cardCompositeFilter;
	}

	/**
	 * @param _cardCompositeFilter the _cardCompositeFilter to set
	 */
	public void set_cardCompositeFilter(CardCompositeFilter _cardCompositeFilter) {
		this._cardCompositeFilter = _cardCompositeFilter;
	}

}
