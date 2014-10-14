/**
* Copyright 2013-2014 Tiancheng Hu
* 
* Licensed under the GNU Lesser General Public License, version 3.0 (LGPL-3.0, the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://opensource.org/licenses/lgpl-3.0.html
*     
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.hscardref.android.view;

import java.util.List;

import com.hscardref.generic.common.Constant;
import com.hscardref.generic.domain.CardCompositeFilter;
import com.hscardref.R;
import com.thinkalike.generic.common.LogTag;
import com.thinkalike.generic.common.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class SearchActivity extends Activity implements OnClickListener {

	private Button _btn_search;
	private Button _btn_clear;
	private CheckBox _chk_minion;
	private CheckBox _chk_spell;
	private CheckBox _chk_weapon;
	private CheckBox _chk_murloc;
	private CheckBox _chk_demon;
	private CheckBox _chk_beast;
	private CheckBox _chk_dragon;
	private CheckBox _chk_pirate;
	private CheckBox _chk_divineShield;
	private CheckBox _chk_deathRattle;
	private CheckBox _chk_combo;
	private CheckBox _chk_charge;
	private CheckBox _chk_battlecry;
	private CheckBox _chk_overload;
	private CheckBox _chk_secret;
	private CheckBox _chk_silence;
	private CheckBox _chk_stealth;
	private CheckBox _chk_taunt;
	private CheckBox _chk_spellDamage;
	private CheckBox _chk_enrage;
	private CheckBox _chk_choice;
	private CheckBox _chk_windfury;

    private CardCompositeFilter _cardCompositeFilter = null;

	/** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onCreate", getClass().getSimpleName()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfilter);
        
        _btn_search = (Button)findViewById(R.id.btn_search_sub);
        _btn_search.setOnClickListener(this);
        
        _btn_clear = (Button)findViewById(R.id.btn_clear);
        _btn_clear.setOnClickListener(this);
        

        _chk_minion = (CheckBox)findViewById(R.id.chk_minion);
        _chk_spell = (CheckBox)findViewById(R.id.chk_spell);
        _chk_weapon = (CheckBox)findViewById(R.id.chk_weapon);
        _chk_murloc = (CheckBox)findViewById(R.id.chk_murloc);
        _chk_demon = (CheckBox)findViewById(R.id.chk_demon);
        _chk_beast = (CheckBox)findViewById(R.id.chk_beast);
        _chk_dragon = (CheckBox)findViewById(R.id.chk_dragon);
        _chk_pirate = (CheckBox)findViewById(R.id.chk_pirate);
        _chk_divineShield = (CheckBox)findViewById(R.id.chk_divineShield);
        _chk_deathRattle = (CheckBox)findViewById(R.id.chk_deathRattle);
        _chk_combo = (CheckBox)findViewById(R.id.chk_combo);
        _chk_charge = (CheckBox)findViewById(R.id.chk_charge);
        _chk_battlecry = (CheckBox)findViewById(R.id.chk_battlecry);
        _chk_overload = (CheckBox)findViewById(R.id.chk_overload);
        _chk_secret = (CheckBox)findViewById(R.id.chk_secret);
        _chk_silence = (CheckBox)findViewById(R.id.chk_silence);
        _chk_stealth = (CheckBox)findViewById(R.id.chk_stealth);
        _chk_taunt = (CheckBox)findViewById(R.id.chk_taunt);
        _chk_spellDamage = (CheckBox)findViewById(R.id.chk_spellDamage);
        _chk_enrage = (CheckBox)findViewById(R.id.chk_enrage);
        _chk_choice = (CheckBox)findViewById(R.id.chk_choice);
        _chk_windfury = (CheckBox)findViewById(R.id.chk_windfury);

        Intent intent = getIntent();
		Bundle mBundle = intent.getBundleExtra("CardCompositeFilter");
		_cardCompositeFilter = (CardCompositeFilter) mBundle
				.get("com.hscardref.generic.domain.CardCompositeFilter");

		if (_cardCompositeFilter == null) {
			_cardCompositeFilter = new CardCompositeFilter();
		} else {
			// checkbox value init
			
			// type check
			List<String> types = _cardCompositeFilter.getTypes();
			if (types != null) {
				for (int i = 0; i < types.size(); i++) {
					if (types.get(i).equals(Constant.CardType.MINION)) {
						_chk_minion.setChecked(true);
					}
					if (types.get(i).equals(Constant.CardType.SPELL)) {
						_chk_spell.setChecked(true);
					}
					if (types.get(i).equals(Constant.CardType.WEAPON)) {
						_chk_weapon.setChecked(true);
					}
				}
			}
			
			// race check
			List<String> races = _cardCompositeFilter.getRaces();
			if (races != null) {
				for (int i = 0; i < races.size(); i++) {
					if (races.get(i).equals(Constant.CardRace.MURLOC)) {
						_chk_murloc.setChecked(true);
					}
					if (races.get(i).equals(Constant.CardRace.DEMON)) {
						_chk_demon.setChecked(true);
					}
					if (races.get(i).equals(Constant.CardRace.BEAST)) {
						_chk_beast.setChecked(true);
					}
					if (races.get(i).equals(Constant.CardRace.DRAGON)) {
						_chk_dragon.setChecked(true);
					}
					if (races.get(i).equals(Constant.CardRace.PIRATE)) {
						_chk_pirate.setChecked(true);
					}
				}
			}

			// ability check
			List<String> abilities = _cardCompositeFilter.getAbilities();
			if (abilities != null) {
				for (int i = 0; i < abilities.size(); i++) {
					if (abilities.get(i).equals(Constant.CardAbility.BATTLECRY)) {
						_chk_battlecry.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.COMBO)) {
						_chk_combo.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.CHARGE)) {
						_chk_charge.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.DEATHRATTLE)) {
						_chk_deathRattle.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.DIVINESHIELD)) {
						_chk_divineShield.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.OVERLOAD)) {
						_chk_overload.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.SECRET)) {
						_chk_secret.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.SILENCE)) {
						_chk_silence.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.STEALTH)) {
						_chk_stealth.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.TAUNT)) {
						_chk_taunt.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.WINDFURY)) {
						_chk_windfury.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.CHOICE)) {
						_chk_choice.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.ENRAGE)) {
						_chk_enrage.setChecked(true);
					}
					if (abilities.get(i).equals(Constant.CardAbility.SPELLDAMAGE)) {
						_chk_spellDamage.setChecked(true);
					}
				}
			}
		}
    }

	@Override
	public void onClick(View arg0) {
		
    	if (R.id.btn_search_sub == arg0.getId()) {
    		final Intent intent = getIntent();
    		
    		updateTypeCondition(_chk_minion, Constant.CardType.MINION);
    		updateTypeCondition(_chk_spell, Constant.CardType.SPELL);
    		updateTypeCondition(_chk_weapon, Constant.CardType.WEAPON);
    		
    		updateRaceCondition(_chk_murloc, Constant.CardRace.MURLOC);
    		updateRaceCondition(_chk_demon, Constant.CardRace.DEMON);
    		updateRaceCondition(_chk_beast, Constant.CardRace.BEAST);
    		updateRaceCondition(_chk_dragon, Constant.CardRace.DRAGON);
    		updateRaceCondition(_chk_pirate, Constant.CardRace.PIRATE);
    		
    		updateAbilityCondition(_chk_battlecry, Constant.CardAbility.BATTLECRY);
    		updateAbilityCondition(_chk_combo, Constant.CardAbility.COMBO);
    		updateAbilityCondition(_chk_charge, Constant.CardAbility.CHARGE);
    		updateAbilityCondition(_chk_deathRattle, Constant.CardAbility.DEATHRATTLE);
    		updateAbilityCondition(_chk_divineShield, Constant.CardAbility.DIVINESHIELD);
    		updateAbilityCondition(_chk_overload, Constant.CardAbility.OVERLOAD);
    		updateAbilityCondition(_chk_secret, Constant.CardAbility.SECRET);
    		updateAbilityCondition(_chk_silence, Constant.CardAbility.SILENCE);
    		updateAbilityCondition(_chk_stealth, Constant.CardAbility.STEALTH);
    		updateAbilityCondition(_chk_taunt, Constant.CardAbility.TAUNT);
    		updateAbilityCondition(_chk_windfury, Constant.CardAbility.WINDFURY);
    		updateAbilityCondition(_chk_choice, Constant.CardAbility.CHOICE);
    		updateAbilityCondition(_chk_enrage, Constant.CardAbility.ENRAGE);
    		updateAbilityCondition(_chk_spellDamage, Constant.CardAbility.SPELLDAMAGE);
    		
    		Bundle mBundle = new Bundle();
    		mBundle.putSerializable("com.hscardref.generic.domain.CardCompositeFilter", _cardCompositeFilter);
            //设置在intent中存储 
            intent.putExtra("CardCompositeFilter", mBundle);  
            //设置回调的方法 
            setResult(Activity.RESULT_OK, intent);

            //关闭当前的窗体  
            finish();
    	}
    	if (R.id.btn_clear == arg0.getId()) {
    		if (_chk_minion.isChecked()) {
    			_chk_minion.setChecked(false);
    		}
    		if (_chk_spell.isChecked()) {
    			_chk_spell.setChecked(false);
    		}
    		if (_chk_weapon.isChecked()) {
    			_chk_weapon.setChecked(false);
    		}
    		if (_chk_murloc.isChecked()) {
    			_chk_murloc.setChecked(false);
    		}
    		if (_chk_demon.isChecked()) {
    			_chk_demon.setChecked(false);
    		}
    		if (_chk_beast.isChecked()) {
    			_chk_beast.setChecked(false);
    		}
    		if (_chk_dragon.isChecked()) {
    			_chk_dragon.setChecked(false);
    		}
    		if (_chk_pirate.isChecked()) {
    			_chk_pirate.setChecked(false);
    		}
    		if (_chk_battlecry.isChecked()) {
    			_chk_battlecry.setChecked(false);
    		}
    		if (_chk_combo.isChecked()) {
    			_chk_combo.setChecked(false);
    		}
    		if (_chk_charge.isChecked()) {
    			_chk_charge.setChecked(false);
    		}
    		if (_chk_deathRattle.isChecked()) {
    			_chk_deathRattle.setChecked(false);
    		}
    		if (_chk_divineShield.isChecked()) {
    			_chk_divineShield.setChecked(false);
    		}
    		if (_chk_overload.isChecked()) {
    			_chk_overload.setChecked(false);
    		}
    		if (_chk_secret.isChecked()) {
    			_chk_secret.setChecked(false);
    		}
    		if (_chk_silence.isChecked()) {
    			_chk_silence.setChecked(false);
    		}
    		if (_chk_stealth.isChecked()) {
    			_chk_stealth.setChecked(false);
    		}
    		if (_chk_taunt.isChecked()) {
    			_chk_taunt.setChecked(false);
    		}
    		if (_chk_windfury.isChecked()) {
    			_chk_windfury.setChecked(false);
    		}
    		if (_chk_choice.isChecked()) {
    			_chk_choice.setChecked(false);
    		}
    		if (_chk_enrage.isChecked()) {
    			_chk_enrage.setChecked(false);
    		}
    		if (_chk_spellDamage.isChecked()) {
    			_chk_spellDamage.setChecked(false);
    		}
    	}

	}

	@Override
	public void onDestroy() {		
		super.onDestroy();
	}
	/**
	 * According to the checkbox of creating filter condition.
	 */
	private void updateTypeCondition(CheckBox checkbox, String content) {
		if (checkbox.isChecked()) {
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
		if (checkbox.isChecked()) {
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
		if (checkbox.isChecked()) {
			if (!_cardCompositeFilter.getAbilities().contains(content)) {
				_cardCompositeFilter.getAbilities().add(content);
			}
		} else {
			_cardCompositeFilter.getAbilities().remove(content);
		}
	}
}
