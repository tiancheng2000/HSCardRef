package com.hscardref.jfx.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.hscardref.generic.common.Constant;
import com.hscardref.generic.domain.CardCompositeFilter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SearchScene extends AnchorPane implements Initializable, EventHandler<ActionEvent> {

	//-- Constants and Enums --------------------------
	public static final String TAG = SearchScene.class.getSimpleName();

	//-- Delegates and Events --------------------------
	//-- Instance and Shared Fields --------------------------
	//kw: it seems inconvenient to keep the "_" convention for FXML field variables, since the names must be same with those in FXML files (fx:id).
	@FXML
    CheckBox chk_minion;
	@FXML
    CheckBox chk_spell;
	@FXML
    CheckBox chk_weapon;
	@FXML
    CheckBox chk_murloc;
    @FXML
    CheckBox chk_demon;
    @FXML
    CheckBox chk_beast;
    @FXML
    CheckBox chk_dragon;
    @FXML
    CheckBox chk_pirate;
    @FXML
    CheckBox chk_battlecry;
    @FXML
    CheckBox chk_charge;
    @FXML
    CheckBox chk_combo;
    @FXML
    CheckBox chk_deathRattle;
    @FXML
    CheckBox chk_divineShield;
    @FXML
    CheckBox chk_overload;
    @FXML
    CheckBox chk_secret;
    @FXML
    CheckBox chk_silence;
    @FXML
    CheckBox chk_stealth;
    @FXML
    CheckBox chk_taunt;
    @FXML
    CheckBox chk_windfury;
    @FXML
    CheckBox chk_choice;
    @FXML
    CheckBox chk_enrage;
    @FXML
    CheckBox chk_spellDamage;
    @FXML
    Button btn_search;
    @FXML
    Button btn_clear;
    
    private Stage _stage = null;
    
    private MainScene parentScene = null;
    
    private CardCompositeFilter _cardCompositeFilter = null;
     
    public  SearchScene() {
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		_cardCompositeFilter = new CardCompositeFilter();
    	btn_search.addEventHandler(ActionEvent.ACTION, this);
    	btn_clear.addEventHandler(ActionEvent.ACTION, this);
	}

	public void onFilterApply() {
		updateTypeCondition(chk_minion, Constant.CardType.MINION);
		updateTypeCondition(chk_spell, Constant.CardType.SPELL);
		updateTypeCondition(chk_weapon, Constant.CardType.WEAPON);
		
		updateRaceCondition(chk_murloc, Constant.CardRace.MURLOC);
		updateRaceCondition(chk_demon, Constant.CardRace.DEMON);
		updateRaceCondition(chk_beast, Constant.CardRace.BEAST);
		updateRaceCondition(chk_dragon, Constant.CardRace.DRAGON);
		updateRaceCondition(chk_pirate, Constant.CardRace.PIRATE);
		
		updateAbilityCondition(chk_battlecry, Constant.CardAbility.BATTLECRY);
		updateAbilityCondition(chk_combo, Constant.CardAbility.COMBO);
		updateAbilityCondition(chk_charge, Constant.CardAbility.CHARGE);
		updateAbilityCondition(chk_deathRattle, Constant.CardAbility.DEATHRATTLE);
		updateAbilityCondition(chk_divineShield, Constant.CardAbility.DIVINESHIELD);
		updateAbilityCondition(chk_overload, Constant.CardAbility.OVERLOAD);
		updateAbilityCondition(chk_secret, Constant.CardAbility.SECRET);
		updateAbilityCondition(chk_silence, Constant.CardAbility.SILENCE);
		updateAbilityCondition(chk_stealth, Constant.CardAbility.STEALTH);
		updateAbilityCondition(chk_taunt, Constant.CardAbility.TAUNT);
		updateAbilityCondition(chk_windfury, Constant.CardAbility.WINDFURY);
		updateAbilityCondition(chk_choice, Constant.CardAbility.CHOICE);
		updateAbilityCondition(chk_enrage, Constant.CardAbility.ENRAGE);
		updateAbilityCondition(chk_spellDamage, Constant.CardAbility.SPELLDAMAGE);

		parentScene.searchCard();
	}
    public void hide() {
        if(_stage != null) {
        	_stage.hide();
        }
    }
    public void show() {
        if(_stage != null) {
        	_stage.show();
        }
    }

	//-- Event Handlers --------------------------
	//--- EventHandler<ActionEvent> ---
    @Override
    public void handle(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == btn_search) {

    		_stage.hide();
    	}
    	if (actionEvent.getSource() == btn_clear) {
    		if (chk_minion.isSelected()) {
    			chk_minion.setSelected(false);
    		}
    		if (chk_spell.isSelected()) {
    			chk_spell.setSelected(false);
    		}
    		if (chk_weapon.isSelected()) {
    			chk_weapon.setSelected(false);
    		}
    		if (chk_murloc.isSelected()) {
    			chk_murloc.setSelected(false);
    		}
    		if (chk_demon.isSelected()) {
    			chk_demon.setSelected(false);
    		}
    		if (chk_beast.isSelected()) {
    			chk_beast.setSelected(false);
    		}
    		if (chk_dragon.isSelected()) {
    			chk_dragon.setSelected(false);
    		}
    		if (chk_pirate.isSelected()) {
    			chk_pirate.setSelected(false);
    		}
    		if (chk_battlecry.isSelected()) {
    			chk_battlecry.setSelected(false);
    		}
    		if (chk_combo.isSelected()) {
    			chk_combo.setSelected(false);
    		}
    		if (chk_deathRattle.isSelected()) {
    			chk_deathRattle.setSelected(false);
    		}
    		if (chk_divineShield.isSelected()) {
    			chk_divineShield.setSelected(false);
    		}
    		if (chk_overload.isSelected()) {
    			chk_overload.setSelected(false);
    		}
    		if (chk_secret.isSelected()) {
    			chk_secret.setSelected(false);
    		}
    		if (chk_silence.isSelected()) {
    			chk_silence.setSelected(false);
    		}
    		if (chk_stealth.isSelected()) {
    			chk_stealth.setSelected(false);
    		}
    		if (chk_taunt.isSelected()) {
    			chk_taunt.setSelected(false);
    		}
    		if (chk_windfury.isSelected()) {
    			chk_windfury.setSelected(false);
    		}
    		if (chk_choice.isSelected()) {
    			chk_choice.setSelected(false);
    		}
    		if (chk_enrage.isSelected()) {
    			chk_enrage.setSelected(false);
    		}
    		if (chk_spellDamage.isSelected()) {
    			chk_spellDamage.setSelected(false);
    		}
    		
    		_cardCompositeFilter.empty();
    	}
    }

    public void set_stage(Stage _stage) {
		this._stage = _stage;
	}

    public Stage get_stage() {
		return this._stage;
	}

	/**
	 * @return the attrInfo
	 */
	public CardCompositeFilter getCardCompositeFilter() {
		return _cardCompositeFilter;
	}

	/**
	 * @param attrInfo the attrInfo to set
	 */
	public void setCardCompositeFilter(CardCompositeFilter cardCompositeFilter) {
		this._cardCompositeFilter = cardCompositeFilter;
	}

	/**
	 * @param parentScene the parentScene to set
	 */
	public void setParentScene(MainScene parentScene) {
		this.parentScene = parentScene;
	}
	
	/**
	 * According to the checkbox of creating filter condition.
	 */
	private void updateTypeCondition(CheckBox checkbox, String content) {
		if (checkbox.isSelected()) {
			if (!_cardCompositeFilter.getTypes().contains(content)) {
				_cardCompositeFilter.getTypes().add(content);
			}
		} else {
			_cardCompositeFilter.getTypes().remove(content);
		}
	}

	/**
	 * According to the checkbox of creating filter condition.
	 */
	private void updateRaceCondition(CheckBox checkbox, String content) {
		if (checkbox.isSelected()) {
			if (!_cardCompositeFilter.getRaces().contains(content)) {
				_cardCompositeFilter.getRaces().add(content);
			}
		} else {
			_cardCompositeFilter.getRaces().remove(content);
		}
	}

	/**
	 * According to the checkbox of creating filter condition.
	 */
	private void updateAbilityCondition(CheckBox checkbox, String content) {
		if (checkbox.isSelected()) {
			if (!_cardCompositeFilter.getAbilities().contains(content)) {
				_cardCompositeFilter.getAbilities().add(content);
			}
		} else {
			_cardCompositeFilter.getAbilities().remove(content);
		}
	}
}
