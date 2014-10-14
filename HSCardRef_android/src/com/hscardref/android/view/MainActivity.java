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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.hscardref.R;
import com.hscardref.android.HSCardRefApp;
import com.hscardref.generic.common.Constant;
import com.hscardref.generic.domain.CardCompositeFilter;
import com.hscardref.generic.domain.CardFilterCollection;
import com.thinkalike.android.common.UncaughtExceptionHandler;
import com.thinkalike.generic.common.LogTag;
import com.thinkalike.generic.common.Util;

/**
 * This activity has different presentations for handset and tablet-size devices.
 * <p>
 * The activity makes heavy use of fragments. The list of resource is a
 * {@link NodeSelectorFragment} and the work area is a {@link WorkareaFragment}.
 * <p>
 * This activity also implements the required {@link NodeSelectorFragment.FragmentCallbacks}
 * interface to listen for resource drag&drop.
 */
public class MainActivity extends FragmentActivity implements FragmentCallbacks {

	//-- Constants and Enums ----------------------------------------------
	private static final int DIALOG_ONCLOSE = 99;
	private static final int WIN_MODEL_REQUEST_CODE = 0x234;

	//-- Inner Classes and Structures -------------------------------------
	//-- Delegates and Events ---------------------------------------------
	//-- Instance and Shared Fields ---------------------------------------
	protected UncaughtExceptionHandler _ueh = new UncaughtExceptionHandler(this, Constant.APP_SHORTNAME, Constant.UNCAUGHTEXCEPTION_RECEIVER_MAIL);
	/**
	 * Whether or not the screen size is large enough to contain a two-pane mode activity. i.e. when running on a tablet device.
	 */
	private boolean _isLargeScreen; //IMPROVE: manage with onSaveInstanceState()

	//-- Properties -------------------------------------------------------
	//-- Constructors -----------------------------------------------------
	//-- Destructors ------------------------------------------------------
	//-- Base Class Overrides ---------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onCreate", getClass().getSimpleName()));
		HSCardRefApp.getInstance().registerUIContext(this); //Application.getUIContext() will be used as uiContext by ViewModel in some case, which happens before onResume(). 
		super.onCreate(savedInstanceState);
		_ueh.initialize();
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.ll_twopane) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			_isLargeScreen = true;
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	protected void onResume() {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onResume", getClass().getSimpleName()));
		HSCardRefApp.getInstance().registerUIContext(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onDestroy", getClass().getSimpleName()));
		_ueh.restoreOriginalHandler();
		_ueh = null;
		HSCardRefApp.getInstance().unregisterUIContext(this);
		super.onDestroy();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog=null;
		switch (id){
		case DIALOG_ONCLOSE:
			new AlertDialog.Builder(this)
			.setMessage(this.getResources().getString(R.string.close_confirm))
			.setCancelable(false)
			.setPositiveButton(R.string.btn_OK, 
		    		new DialogInterface.OnClickListener(){
		             	public void onClick(DialogInterface dialog, int id){
		             		MainActivity.this.finish();
		             	}
		           	})
		    .setNegativeButton(R.string.btn_Cancel, null)
			.show();
            break;
		default:
			break;
		}
		return dialog;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == WIN_MODEL_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Bundle mBundle = intent.getBundleExtra("CardCompositeFilter");
				CardCompositeFilter cardCompositeFilter = (CardCompositeFilter) mBundle
						.get("com.hscardref.generic.domain.CardCompositeFilter");
				NodeSelectorFragment nodeSelectorFragment = (NodeSelectorFragment) getSupportFragmentManager()
						.findFragmentById(R.id.nodeselector);

				if (nodeSelectorFragment != null) {
					nodeSelectorFragment.setRefresh(false);
					// Call a method in the NodeSelectorFragment to search card
					// info
					nodeSelectorFragment.applyCardFilter(-2, 0, cardCompositeFilter);
				}
				
				WorkareaFragment workareaFragment = (WorkareaFragment) getSupportFragmentManager()
						.findFragmentById(R.id.workarea);
		    	if (cardCompositeFilter.getAbilities().size() > 0 
		    			|| cardCompositeFilter.getRaces().size() > 0
		    			|| cardCompositeFilter.getTypes().size() > 0) {
		    		workareaFragment.setFilterDialogStatus(false);
		    	} else {
		    		workareaFragment.setFilterDialogStatus(true);
		    	}
			}
		}
	}

	//-- Public and internal Methods --------------------------------------
	//-- Private and Protected Methods ------------------------------------
	//-- Event Handlers ---------------------------------------------------
	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		this.showDialog(DIALOG_ONCLOSE); //for simplicity. IMPROVE: use DialogFragment instead
		//super.onBackPressed();
	}

	//--- FragmentCallback ---
	@Override
	public void onAction(int id, int filterType) {

		NodeSelectorFragment nodeSelectorFragment = (NodeSelectorFragment) getSupportFragmentManager()
				.findFragmentById(R.id.nodeselector);

		if (nodeSelectorFragment != null) {
			if (R.id.btn_nodefilter_custom == id) {
				//IMPROVE: use English comment for open source community~~
				// 父Activitiy向子类Activity传递数据
				Intent intent = new Intent(MainActivity.this,
						SearchActivity.class);
				CardFilterCollection cardFilter = nodeSelectorFragment.get_cardSearchCondition();
				
				// 设置数据信息
	    		Bundle mBundle = new Bundle();
	    		mBundle.putSerializable("com.hscardref.generic.domain.CardCompositeFilter", cardFilter == null ? null : cardFilter.get_cardCompositeFilter());
		        // 设置在intent中存储 
		        intent.putExtra("CardCompositeFilter", mBundle);  

				// 备注此处启动方式为startActivityForResult(intent,请求编码)而不是startActivity(intent)
				// 这是startActivityForResult和startActivity的重要区别，
				// 1.是否传递请求编码
				// 2.是否可以调用子类的方法
				startActivityForResult(intent, WIN_MODEL_REQUEST_CODE);
			} else {
				// Call a method in the NodeSelectorFragment to search card info
				nodeSelectorFragment.applyCardFilter(id, filterType, null);
			}
		}
	}

}
