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

package com.hscardref.generic.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.hscardref.generic.common.Constant;
import com.hscardref.generic.dal.CardLoader;
import com.hscardref.generic.domain.Card;
import com.hscardref.generic.domain.CardFilterCollection;
import com.hscardref.generic.domain.CardType;
import com.thinkalike.generic.Loader;
import com.thinkalike.generic.common.LogTag;
import com.thinkalike.generic.common.Util;
import com.thinkalike.generic.domain.INodeRO;
import com.thinkalike.generic.domain.ImageNode;
import com.thinkalike.generic.domain.NodeType;
import com.thinkalike.generic.viewmodel.ViewModelBase;
import com.thinkalike.generic.viewmodel.control.UIImageNode;
import com.thinkalike.generic.viewmodel.control.UINode;

/**
 * managed Constant.PropertyName: Node, NodeList
 */
/**
 * @author Timothy
 *
 */
public class NodeSelectorViewModel extends ViewModelBase{
	//-- Constants and Enums --------------------------
	//-- Inner Classes and Structures --------------------------
	//IMPROVE: if the following factors can be retrieved dynamically (and efficiently),
	//  we can make this class an independent helper class(e.g. generic.event.VMListenerAdapter<L>):
	//  1.onXxxChanged() method name  2.xxx returned value type  
//	public class VMListenerAdapter implements PropertyChangeListener{
//		
//		private List<VMListener> _listenerList = new ArrayList<VMListener>();
//		public void addVMListener(VMListener l){_listenerList.add(l);}
//		public void removeVMListener(VMListener l){_listenerList.remove(l);}
//
//		@Override
//		public void onPropertyChanged(PropertyChangeEvent event) {
//			assert(event.getPropertyName().equals(Constant.PropertyName.NodeType));
//			NodeType nodeType = (NodeType)event.getNewValue(); 
//			for (VMListener l : _listenerList){
//				l.onCurrentResoruceTypeChanged(nodeType);
//			}
//		}
//	}
//	public interface VMListener{
//		public void onCurrentNodeTypeChanged(NodeType nodeType);
//	}

	//-- Delegates and Events --------------------------	
	//-- Instance and Shared Fields --------------------------
	private static NodeSelectorViewModel _this;
	//IMPROVE: directly use CardType instead of using NodeType
	private NodeType _currentNodeType = NodeType.TypeA;
	private List<UINode> _uiNodeList = null;
	private List<Card> _cardList = null;
	private CardFilterCollection _cardFilter = new CardFilterCollection();
	//private Node _nodeSelected;
	private INodeRO _nodeSelected_RO = null;

	//-- Properties --------------------------
	public NodeType getCurrentNodeType(){return _currentNodeType;}
	public CardFilterCollection getCardFilter(){return _cardFilter;}
	public void setCardFilter(CardFilterCollection cardFilter){_cardFilter = cardFilter;}
	
	//-- Constructors --------------------------
	private NodeSelectorViewModel()
	{
	}
	
	//-- Destructors --------------------------
	public void dispose()
	{
	}
	
	//-- Base Class Overrides --------------------------
	//-- Public and internal Methods --------------------------
	public static NodeSelectorViewModel getInstance() {
		if(_this == null)
			_this = new NodeSelectorViewModel();
		return _this;
	}
	

	//-- Private and Protected Methods --------------------------
//	private void initData() {
//	}
//	
//	private void initLayout() {
//	}

	private List<UINode> getUINodeList(boolean isForcedReload)
	{
		//MVVM::DataSource: list need to be fetched from somewhere 
		if(_uiNodeList == null || isForcedReload){
			//IMPROVE: 1.consider the UIContext could still be invalid by now -- UINode's createView() should be modified
			//          2.consider the case that one ViewModel can serve several Views -- a.multiple uiContexts b.multiple views?
			Object uiContext = Loader.getInstance().getPlatform().getUIContext();
			Util.trace(LogTag.ViewModel, String.format("Load DO: uiContext=%s", (uiContext==null)? "null" : uiContext.getClass().getSimpleName()));

			//IMPROVE: directly use CardType instead of using NodeType
			CardType currentCardType = CardType.values()[_currentNodeType.ordinal()];
			//1.implements NodeLoader 2.Card list -> ImageNode list.
			_cardList = CardLoader.loadCardsInfoWithType(currentCardType);
			convertCardToNode();
			Util.trace(LogTag.ViewModel, String.format("Load DO: _uiNodeList.size=%s", (_uiNodeList==null) ? "n/a" : _uiNodeList.size()));
		}
		return _uiNodeList;
	}

	private void convertCardToNode() {
		if (_cardList == null) 
			return;

		//IMPROVE: 1.consider the UIContext could still be invalid by now -- UINode's createView() should be modified
		//          2.consider the case that one ViewModel can serve several Views -- a.multiple uiContexts b.multiple views?
		Object uiContext = Loader.getInstance().getPlatform().getUIContext();
		Util.trace(LogTag.ViewModel, String.format("Load DO: uiContext=%s", (uiContext==null)? "null" : uiContext.getClass().getSimpleName()));
		
		_uiNodeList = new ArrayList<UINode>();
		for (int i = 0; i < _cardList.size(); i++) {
			Card card = _cardList.get(i);
			if (_cardFilter.accept(card)){
				String fileName = card.getId() + ".png";
				ImageNode imageNode = new ImageNode(Util.appendPath(card.getRelativePath(), fileName));
				_uiNodeList.add(new UIImageNode(uiContext, imageNode, false));
			}
		}
	}

	//-- Event Handlers --------------------------
	public void onNodeTypeChanged(){
		onNodeTypeChanged(_currentNodeType);
	}
	public void onNodeTypeChanged(NodeType nodeType){
		Util.trace(LogTag.ViewModel, "[ICommand]::VM onNodeTypeChanged()");
		_currentNodeType = nodeType; //no matter equal or not
		onRefreshNodeList();
	}

	public void onRefreshNodeList(){
		//TODO: ICommand should also be implemented through Event scheme -- maybe a EventBus for both ICommand and IProperty
		Util.trace(LogTag.ViewModel, "[ICommand]::VM onRefreshNodeList()");
		boolean isForcedReload = true; 
		Object oldValue = _uiNodeList;
		_uiNodeList = getUINodeList(isForcedReload);
		//IMPROVE: clear selected Node when NodeList get refreshed, except nothing changed at all
		if(isForcedReload || oldValue!=_uiNodeList)
			onNodeSelected(null); //clear selected Node in most refresh cases 
		this.firePropertyChange(Constant.PropertyName.NodeList, isForcedReload?null:oldValue, _uiNodeList); //force refresh
	}

	/**
	 * @param uiNode  if null, means Node is unselected. IProperty handler of Constant.PropertyName.Node SHOULD correctly handle it.  
	 */
	public void onNodeSelected(UINode uiNode){
		Util.trace(LogTag.ViewModel, "[ICommand]::VM onNodeSelected()");
		if(uiNode==null){
			this.firePropertyChange(Constant.PropertyName.Node, _nodeSelected_RO, null);
		}
		else{
			//NOTE: We choose to transfer read-only DTO here. For simple projects that do not care about 
			//      Access Privilege, or difference between DTO and DO, you can just transfer DO.
			//Node oldValue = _nodeSelected;
			//_nodeSelected = uiNode.getData();
			INodeRO oldValue = _nodeSelected_RO;
			_nodeSelected_RO = uiNode.getDataRO();
			
			this.firePropertyChange(Constant.PropertyName.Node, oldValue, _nodeSelected_RO);
		}
	}

	public void onFilterChanged(){
		Util.trace(LogTag.ViewModel, "[ICommand]::VM onFilterChanged()");
		
		convertCardToNode();
		
		onNodeSelected(null); //clear selected Node in most refresh cases 
		this.firePropertyChange(Constant.PropertyName.NodeList, null, _uiNodeList); //force refresh
	}
	
}

