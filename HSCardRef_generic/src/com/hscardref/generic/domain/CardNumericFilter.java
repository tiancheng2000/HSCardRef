package com.hscardref.generic.domain;

import com.hscardref.generic.common.Constant;

public class CardNumericFilter implements CardFilter {

	//-- Constants and Enums -----------------------------------
	public static final String TAG = CardFilterCollection.class.getSimpleName();

	//-- Delegates and Events ----------------------------------
	//-- Instance and Shared Fields ----------------------------
	private int 	_type; //relative to Config.STORAGE_BASEPATH
	private int 	_num; //card id eg.Moonfire,

	//-- Constructors ------------------------------------------
	public CardNumericFilter() {
		this._type = 0;
		this._num = -1;
	}

	public CardNumericFilter(int type, int num) {
		this._type = type;
		this._num = num;
	}
	//-- Properties --------------------------------------------
	/**
	 * @return the _type
	 */
	public int getType() {
		return _type;
	}

	/**
	 * @param _type the _type to set
	 */
	public void setType(int _type) {
		this._type = _type;
	}

	/**
	 * @return the _num
	 */
	public int getNum() {
		return _num;
	}

	/**
	 * @param _num the _num to set
	 */
	public void setNum(int _num) {
		this._num = _num;
	}

	@Override
	public boolean accept(Card card) {
		
		int compareNum = 0;
		
		if (this.getType() == Constant.FilterType.CRYSTAL) {
			compareNum = card.getCost();
		} else if (this.getType() == Constant.FilterType.HP) {
			compareNum = card.getHp();
		} else if (this.getType() == Constant.FilterType.ATTACK) {
			compareNum = card.getAtk();
		}
		
		if(this.getNum() == -1) {
			return true;
		}
		
		if (this.getNum() == compareNum) {
			return true;
		} else {
			if (this.getNum() == 7 && compareNum > 7 ) {
				return true;
			}
		}
		return false;
	}
}
