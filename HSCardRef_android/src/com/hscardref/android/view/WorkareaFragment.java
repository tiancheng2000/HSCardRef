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

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.hscardref.R;
import com.hscardref.android.adapter.CardFilterTypeAdapter;
import com.hscardref.generic.common.Constant;
import com.hscardref.generic.viewmodel.WorkareaViewModel;
import com.thinkalike.android.control.ImageNodeView;
import com.thinkalike.generic.common.LogTag;
import com.thinkalike.generic.common.Util;
import com.thinkalike.generic.event.PropertyChangeEvent;
import com.thinkalike.generic.event.PropertyChangeListener;
import com.thinkalike.generic.viewmodel.control.INodeView;
import com.thinkalike.generic.viewmodel.control.UINode;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link CanvasActivity} on handsets.
 */
public class WorkareaFragment extends Fragment implements OnClickListener{

	//-- Constants and Enums --------------------------
	public static final String TAG = WorkareaFragment.class.getSimpleName();
	//public static final String ARG_ITEM_ID = "item_id";
	private final static int[] NumFilterIds = new int[]{R.id.btn_nodefilter_all, R.id.btn_nodefilter_0, R.id.btn_nodefilter_1, R.id.btn_nodefilter_2, R.id.btn_nodefilter_3, R.id.btn_nodefilter_4, R.id.btn_nodefilter_5, R.id.btn_nodefilter_6, R.id.btn_nodefilter_7plus }; 
	private final static int[] NumFilterOffIcons = new int[]{R.drawable.filter_cost_rank_all, R.drawable.filter_cost_rank_0, R.drawable.filter_cost_rank_1, R.drawable.filter_cost_rank_2, R.drawable.filter_cost_rank_3, R.drawable.filter_cost_rank_4, R.drawable.filter_cost_rank_5, R.drawable.filter_cost_rank_6, R.drawable.filter_cost_rank_7plus }; 
	private final static int[] NumFilterOnIcons = new int[]{R.drawable.filter_cost_rank_all_on, R.drawable.filter_cost_rank_0_on, R.drawable.filter_cost_rank_1_on, R.drawable.filter_cost_rank_2_on, R.drawable.filter_cost_rank_3_on, R.drawable.filter_cost_rank_4_on, R.drawable.filter_cost_rank_5_on, R.drawable.filter_cost_rank_6_on, R.drawable.filter_cost_rank_7plus_on }; 
	
	//-- Inner Classes and Structures --------------------------
	//-- Delegates and Events --------------------------	
	//-- Instance and Shared Fields --------------------------	
	// GUI controls
	//private TextView _tv_content;
	//private ImageView _iv_content;
	private ProgressDialog _pd;
	private ImageNodeView _inv_nodecontent;
	private Spinner _spinner;
	private Button _btn_OK;
	private Button _btn_nodefilter_0;
	private Button _btn_nodefilter_1;
	private Button _btn_nodefilter_2;
	private Button _btn_nodefilter_3;
	private Button _btn_nodefilter_4;
	private Button _btn_nodefilter_5;
	private Button _btn_nodefilter_6;
	private Button _btn_nodefilter_7plus;
	private Button _btn_nodefilter_all;
	private Button _btn_nodefilter_custom;
	
	// FragmentCallbacks
	private FragmentCallbacks _listenerFromActivity;

	// MVVM
	private WorkareaViewModel _viewModel = null;
	/**
	 * listen to relative ViewModel. SHOULD be an instance variable.<br>
	 * Registration side (ViewModel) will only keep listener's WeakReference, so that View can be safely released.
	 */
	private PropertyChangeListener _listenToVM = null; 
														
	//-- Properties --------------------------
	//-- Constructors --------------------------
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public WorkareaFragment() {
	}

	//-- Destructors --------------------------
	//-- Base Class Overrides --------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onCreate", getClass().getSimpleName()));
		super.onCreate(savedInstanceState);

		final WorkareaFragment thisInstance = this;
		if(_viewModel == null){
			_viewModel = WorkareaViewModel.getInstance();
			_listenToVM = new PropertyChangeListener(){
				@Override
				public void onPropertyChanged(PropertyChangeEvent event) {
					Util.trace(LogTag.ViewModel, String.format("[IProperty] PropertyChanged(name=%s, value=%s, listener=%s)", event.getPropertyName(), event.getNewValue(), thisInstance.getClass().getSimpleName()));
					if (event.getPropertyName().equals(Constant.PropertyName.Node)){
						updateWorkarea((UINode)event.getNewValue());
					}
					else if (event.getPropertyName().equals(Constant.PropertyName.IsBusy)){
						//turn on/off circular progress bar 
						showProgressBar((Boolean)event.getNewValue());
					}
				}
			};
			_viewModel.addPropertyChangeListener(Constant.PropertyName.Node, _listenToVM);
		}

//		if (getArguments().containsKey(ARG_ITEM_ID)) {
//			// Load the dummy content specified by the fragment
//			// arguments. In a real-world scenario, use a Loader
//			// to load content from a content provider.
//			String item_id = getArguments().getString(ARG_ITEM_ID);
//			_item = _viewModel.getItem(item_id);
//		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onCreateView", getClass().getSimpleName()));
		View rootView = inflater.inflate(R.layout.workarea,
				container, false); //kw: 3rd param must be false -- Android SDK

		//initialize Editor's canvas based on DOs (Page[])
		_btn_OK = (Button) rootView.findViewById(R.id.btn_ok); 
		_btn_OK.setVisibility(View.INVISIBLE);
		_inv_nodecontent = (ImageNodeView) rootView.findViewById(R.id.iv_nodecontent); 
		//NOTE: if ImageView/ImageNodeView is instantiated dynamically without its dimensions specified, use the following callback instead. 

//		if (_item != null) {
//			final String imagePath = (String)_item.getProperty(Constant.PropertyName.Content);
//			_iv_content.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_image)); //default image (inner resource)
//			//IMPROVE: (in onCreateView()) rootView.getWidth()/getHeight() or container.getWidth()/getHeight() can only get 0 value.
//			//IMPROVE: (in onResume()) _iv_content's w/h cannot be retrieved even here.., even recursively to its parents.
//			//int width_image = Util.getActualLayoutWidth(_iv_content);
//			//int height_image = Util.getActualLayoutHeight(_iv_content);
//			_iv_content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
//				@Override
//				public boolean onPreDraw() {
//					_iv_content.getViewTreeObserver().removeOnPreDrawListener(this);
//					int width_image = Util.getActualLayoutWidth(_iv_content);
//					int height_image = Util.getActualLayoutHeight(_iv_content);
//					Util.trace(null, LogTag.ResourceThread, "(width_image,height_image)=("+width_image+","+height_image+")");
//					//Bitmap contentImage = (Bitmap)MediaManager.getInstance().get(contentText, width_image, height_image, true); //only use this for commonly used media
//					//iv_content.setImageBitmap(contentImage);
//					MediaAsyncLoader.asyncLoadImageFile(
//							Util.appendPath(Config.getPhotoResourcePath(), imagePath),
//							width_image, height_image, _onResourceLoadListener);
//					_viewModel.setBusy(true); //if we can also put ImageView into ViewModel, isBusy flag can be encapsulated within ImageView.loadBitmap()
//
//					return true;
//				}
//			});
//		}

		// Spinner init
		int[] images = {R.drawable.attr_cost, R.drawable.attr_hp, R.drawable.attr_atk};
		int[] values = {Constant.FilterType.CRYSTAL, Constant.FilterType.HP, Constant.FilterType.ATTACK};
		_spinner = (Spinner)rootView.findViewById(R.id.btn_nodefilter_type);
		CardFilterTypeAdapter adapter = new CardFilterTypeAdapter(getActivity(), images, values);
		_spinner.setAdapter(adapter);
		_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				_listenerFromActivity.onAction(_spinner.getId(), arg2);
			}
		});

		// button event register
		_btn_nodefilter_0 = (Button) rootView.findViewById(R.id.btn_nodefilter_0); 
		_btn_nodefilter_0.setOnClickListener(this);
		_btn_nodefilter_1 = (Button) rootView.findViewById(R.id.btn_nodefilter_1); 
		_btn_nodefilter_1.setOnClickListener(this);
		_btn_nodefilter_2 = (Button) rootView.findViewById(R.id.btn_nodefilter_2); 
		_btn_nodefilter_2.setOnClickListener(this);
		_btn_nodefilter_3 = (Button) rootView.findViewById(R.id.btn_nodefilter_3); 
		_btn_nodefilter_3.setOnClickListener(this);
		_btn_nodefilter_4 = (Button) rootView.findViewById(R.id.btn_nodefilter_4); 
		_btn_nodefilter_4.setOnClickListener(this);
		_btn_nodefilter_5 = (Button) rootView.findViewById(R.id.btn_nodefilter_5); 
		_btn_nodefilter_5.setOnClickListener(this);
		_btn_nodefilter_6 = (Button) rootView.findViewById(R.id.btn_nodefilter_6); 
		_btn_nodefilter_6.setOnClickListener(this);
		_btn_nodefilter_7plus = (Button) rootView.findViewById(R.id.btn_nodefilter_7plus); 
		_btn_nodefilter_7plus.setOnClickListener(this);
		_btn_nodefilter_all = (Button) rootView.findViewById(R.id.btn_nodefilter_all); 
		_btn_nodefilter_all.setOnClickListener(this);
		_btn_nodefilter_custom = (Button) rootView.findViewById(R.id.btn_nodefilter_custom); 
		_btn_nodefilter_custom.setOnClickListener(this);
		
		return rootView;
	}

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onViewCreated", getClass().getSimpleName()));
		super.onViewCreated(view, savedInstanceState);

		//_viewModel.onXxx();
	}
	
	@Override
	public void onAttach(Activity activity) {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onAttach", getClass().getSimpleName()));
		super.onAttach(activity);

		//ViewModel may check type of parent Activity here. 
		if (!(activity instanceof FragmentCallbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		_listenerFromActivity = (FragmentCallbacks) activity;
	}

	@Override
	public void onDetach() {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onDetach", getClass().getSimpleName()));
		super.onDetach();

		// Reset the active callbacks interface to null.
		_listenerFromActivity = null;
	}
	
	@Override
	public void onDestroy() {
		Util.trace(LogTag.LifeCycleManagement, String.format("%s: onDestroy", getClass().getSimpleName()));
		super.onDestroy();
		_viewModel.removePropertyChangeListener(Constant.PropertyName.Node, _listenToVM);
	}

	//-- Public and internal Methods --------------------------	
	//-- Private and Protected Methods --------------------------	
	private void showProgressBar(boolean bFlag) {
		if (bFlag){
			_pd = ProgressDialog.show(this.getActivity(), this.getActivity().getString(R.string.loading), "");
		}
		else{
			if(_pd!=null){
				Util.trace(LogTag.ViewModel, "ProgressBar turned off");
				_pd.dismiss();
				_pd = null;
			}
		}
	}

	private void updateWorkarea(UINode uiNode){
		if (uiNode == null){
			_inv_nodecontent.setImageBitmap(null);
			_btn_OK.setVisibility(View.INVISIBLE);
			return;
		}

		INodeView nodeView = uiNode.getView();
		if(nodeView==null){
			nodeView = uiNode.attachView(_inv_nodecontent); //attach and update uiData to an existed raw ImageView 
			_btn_OK.setVisibility(View.VISIBLE);
		}
		else if (nodeView instanceof View){
			//...
		}
		else {
			Util.error(TAG, "updateWorkarea(): uiNode's view type is incompatible: " + ((nodeView==null)?null:nodeView.getClass().getSimpleName()));
			return;
		}
//		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
//		_rl_workarea.addView((View)nodeView, rlp);
//		//nodeView.onParentViewChanged(_rl_workarea);
//		//Otherwise, use INodeView.attachedToView(_rl_workarea) instead.
	}
	
	//-- Event Handlers --------------------------
	@Override
	public void onClick(View arg0) {

		// clicked button is numeric button?
		int idxNumFilterClicked = -1;
		for (int i = 0; i < NumFilterIds.length; i++) {
			if (arg0.getId() == NumFilterIds[i]) {
				idxNumFilterClicked = i;
				break;
			}
		}
		
		//IMPROVE: encapsulate a 2-state image button class
		// numeric button status update
		if (idxNumFilterClicked >= 0) {
			_btn_nodefilter_all.setBackgroundResource(NumFilterOffIcons[0]);
			_btn_nodefilter_0.setBackgroundResource(NumFilterOffIcons[1]);
			_btn_nodefilter_1.setBackgroundResource(NumFilterOffIcons[2]);
			_btn_nodefilter_2.setBackgroundResource(NumFilterOffIcons[3]);
			_btn_nodefilter_3.setBackgroundResource(NumFilterOffIcons[4]);
			_btn_nodefilter_4.setBackgroundResource(NumFilterOffIcons[5]);
			_btn_nodefilter_5.setBackgroundResource(NumFilterOffIcons[6]);
			_btn_nodefilter_6.setBackgroundResource(NumFilterOffIcons[7]);
			_btn_nodefilter_7plus.setBackgroundResource(NumFilterOffIcons[8]);
			arg0.setBackgroundResource(NumFilterOnIcons[idxNumFilterClicked]);
		}
		
		//IMPROVE: avoid using numeric across different classes (esp. different domains) to avoid unnecessary coupling. 
		Integer type = (Integer)_spinner.getSelectedItem();
		_listenerFromActivity.onAction(arg0.getId(), type.intValue());
	}

	public void setFilterDialogStatus(boolean isBkgDark) {
		if (isBkgDark) {
			_btn_nodefilter_custom.setBackgroundResource(R.drawable.btn_gold);
		} else {
			_btn_nodefilter_custom.setBackgroundResource(R.drawable.btn_gold_on);
		}
	}
}

