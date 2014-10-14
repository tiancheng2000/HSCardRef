package com.hscardref.generic.domain;

import com.thinkalike.generic.domain.Node;

public class Card implements Node{

	//-- Constants and Enums -----------------------------------
	public static final String TAG = Card.class.getSimpleName();

	//-- Delegates and Events ----------------------------------
	//-- Instance and Shared Fields ----------------------------
	private String 		_relativePath; 	//relative to Config.STORAGE_BASEPATH
	private String 		_id; 			//card id eg.Moonfire,
	private CardType 	_cardType; 		// card type 
	private int 		_cost; 			//cost value
	private int 		_atk; 			//attack value
	private int 		_hp; 			//hp value
	private String 		_type; 			//Constant.CardType
	private String	 	_race; 			//Constant.CardRace
	private String 		_ability; 	//Constant.CardAbility
	
	//-- Constructors ------------------------------------------
	public Card() {
	}

	public Card(String relativePath, 
				String id, 
				String type, 
				int cost, 
				int atk, 
				int hp, 
				CardType cardType, 
				String ability) {
		this._relativePath = relativePath;
		this._id = id;
		this._type = type;
		this._cost = cost;
		this._atk = atk;
		this._hp = hp;
		this._cardType = cardType;
		this._ability = ability;
	}
	//-- Properties --------------------------------------------

	/**
	 * @return the _id
	 */
	public String getId() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void setId(String _id) {
		this._id = _id;
	}

	/**
	 * @return the _type
	 */
	public String getType() {
		return _type;
	}

	/**
	 * @param _type the _type to set
	 */
	public void setType(String _type) {
		this._type = _type;
	}

	/**
	 * @return the _cost
	 */
	public int getCost() {
		return _cost;
	}

	/**
	 * @param _cost the _cost to set
	 */
	public void setCost(int _cost) {
		this._cost = _cost;
	}

	/**
	 * @return the _atk
	 */
	public int getAtk() {
		return _atk;
	}

	/**
	 * @param _atk the _atk to set
	 */
	public void setAtk(int _atk) {
		this._atk = _atk;
	}

	/**
	 * @return the _hp
	 */
	public int getHp() {
		return _hp;
	}

	/**
	 * @param _hp the _hp to set
	 */
	public void setHp(int _hp) {
		this._hp = _hp;
	}

	/**
	 * @return the _cardType
	 */
	public CardType getCardType() {
		return _cardType;
	}

	/**
	 * @param druid the _cardType to set
	 */
	public void setCardType(CardType cardType) {
		this._cardType = cardType;
	}

	/**
	 * @return the _abilities
	 */
	public String getAbility() {
		return _ability;
	}

	/**
	 * @param _abilities the _abilities to set
	 */
	public void setAbility(String _ability) {
		this._ability = _ability;
	}

	/**
	 * @return the _relativePath
	 */
	public String getRelativePath() {
		return _relativePath;
	}

	/**
	 * @param _relativePath the _relativePath to set
	 */
	public void setRelativePath(String _relativePath) {
		this._relativePath = _relativePath;
	}

	/**
	 * @return the _race
	 */
	public String getRace() {
		return _race;
	}

	/**
	 * @param _race the _race to set
	 */
	public void setRace(String _race) {
		this._race = _race;
	}

	//-- Destructors -------------------------------------------
	//-- Base Class Overrides ----------------------------------
	//-- Public and internal Methods ---------------------------
	//-- Private and Protected Methods -------------------------
	//-- Event Handlers ----------------------------------------
	
}
