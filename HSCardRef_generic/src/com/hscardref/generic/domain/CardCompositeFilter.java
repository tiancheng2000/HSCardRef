package com.hscardref.generic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardCompositeFilter  implements Serializable, CardFilter{

	private static final long serialVersionUID = -7060210544600464481L;

	//-- Constants and Enums -----------------------------------
	public static final String TAG = CardCompositeFilter.class.getSimpleName();

	//-- Delegates and Events ----------------------------------
	//-- Instance and Shared Fields ----------------------------
	private List<String> _types; //relative to Config.STORAGE_BASEPATH
	private List<String> _races; //relative to Config.STORAGE_BASEPATH
	private List<String> _abilities; //relative to Config.STORAGE_BASEPATH
	
	//-- Constructors ------------------------------------------
	public CardCompositeFilter() {
		this._types = new ArrayList<String>();
		this._races = new ArrayList<String>();
		this._abilities = new ArrayList<String>();
	}

	public CardCompositeFilter(List<String> types, List<String> races, List<String> abilities) {
		this._types = types;
		this._races = races;
		this._abilities = abilities;
	}
	//-- Properties --------------------------------------------

	/**
	 * @return the _types
	 */
	public List<String> getTypes() {
		return _types;
	}

	/**
	 * @param _types the _types to set
	 */
	public void setTypes(List<String> _types) {
		this._types = _types;
	}

	/**
	 * @return the _races
	 */
	public List<String> getRaces() {
		return _races;
	}

	/**
	 * @param _races the _races to set
	 */
	public void setRaces(List<String> _races) {
		this._races = _races;
	}

	/**
	 * @return the _abilities
	 */
	public List<String> getAbilities() {
		return _abilities;
	}

	/**
	 * @param _abilities the _abilities to set
	 */
	public void setAbilities(List<String> _abilities) {
		this._abilities = _abilities;
	}
	
	/**
	 * Type attrbuite has set
	 */
	public boolean isTypeEmpty() {
		if (this._types != null && this._types.size() > 0) {
			return false;
		}

		return true;
	}

	/**
	 * Race attrbuite has set
	 */
	public boolean isRaceEmpty() {
		if (this._races != null && this._races.size() > 0) {
			return false;
		}

		return true;
	}

	/**
	 * Ability attrbuite has set
	 */
	public boolean isAbilityEmpty() {
		if (this._abilities != null && this._abilities.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean accept(Card card) {
		// card type info is not empty
		if (!this.isTypeEmpty()) {
			
			// ability conditions flag
			boolean hasType = true;
			String currentType = card.getType();
			if (currentType == null || currentType.isEmpty()) {
				return false;
			}

			if (currentType == null || currentType.isEmpty()) {
				return false;
			}
			List<String> types = this.getTypes();
			for (int i = 0; i < types.size(); i++) {
				if (!currentType.contains(types.get(i))) {
					hasType = false;
					break;
				}
			}
			
			// ability conditions are not met
			if (!hasType) {
				return false;
			}
		}
		
		// card race info is not empty
		if (!this.isRaceEmpty()) {
			// ability conditions flag
			boolean hasRace = true;
			String currentRace = card.getRace();
			if (currentRace == null || currentRace.isEmpty()) {
				return false;
			}
			List<String> races = this.getRaces();
			for (int i = 0; i < races.size(); i++) {
				if (!currentRace.contains(races.get(i))) {
					hasRace = false;
					break;
				}
			}
			
			// ability conditions are not met
			if (!hasRace) {
				return false;
			}
		}

		//  card Ability info is not empty
		if (!this.isAbilityEmpty()) {
			
			// ability conditions flag
			boolean hasAbility = true;
			String currentAbility = card.getAbility();
			if (currentAbility == null || currentAbility.isEmpty()) {
				return false;
			}
			List<String> abilities = this.getAbilities();
			for (int i = 0; i < abilities.size(); i++) {
				if (!currentAbility.contains(abilities.get(i))) {
					hasAbility = false;
					break;
				}
			}
			
			// ability conditions are not met
			if (!hasAbility) {
				return false;
			}
		}
		return true;
	}
	
	public void empty(){
		for (int i = 0; i < _types.size(); i++) {
			_types.remove(i);
		}
		for (int i = 0; i < _races.size(); i++) {
			_races.remove(i);
		}
		for (int i = 0; i < _abilities.size(); i++) {
			_abilities.remove(i);
		}
	}
}
