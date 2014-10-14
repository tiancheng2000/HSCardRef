package com.hscardref.generic.dal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hscardref.generic.common.Config;
import com.hscardref.generic.domain.Card;
import com.hscardref.generic.domain.CardType;
import com.thinkalike.generic.common.Util;

public class CardLoader {

	//-- Constants and Enums --------------------------
	public static final String TAG = CardLoader.class.getSimpleName();
	// NodeType-AssetsPath Map
	private final static Map<CardType, CardRepositoryInfo> NodeType_CardRepositoryInfo_Map = new HashMap<CardType, CardRepositoryInfo>();
	private final static CardType[] CardTypeList = new CardType[]{CardType.Druid, CardType.Hunter, CardType.Mage, CardType.Paladin, CardType.Priest, CardType.Rogue, CardType.Shaman, CardType.Warlock, CardType.Warrior, CardType.Neutral, CardType.Customize }; 
	//Define a struct composing of CardType-specific assetsPath and dataPath, and map CardType to it. 
	private final static CardRepositoryInfo[] CardRepositoryInfoList = new CardRepositoryInfo[]{
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_A, Config.PATH_ASSETS_TYPE_A),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_B, Config.PATH_ASSETS_TYPE_B),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_C, Config.PATH_ASSETS_TYPE_C),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_D, Config.PATH_ASSETS_TYPE_D),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_E, Config.PATH_ASSETS_TYPE_E),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_F, Config.PATH_ASSETS_TYPE_F),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_G, Config.PATH_ASSETS_TYPE_G),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_H, Config.PATH_ASSETS_TYPE_H),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_I, Config.PATH_ASSETS_TYPE_I),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_J, Config.PATH_ASSETS_TYPE_J),
		new CardRepositoryInfo(Config.PATH_DATA_TYPE_K, Config.PATH_ASSETS_TYPE_K)};
	static{
		assert(CardTypeList.length == CardRepositoryInfoList.length);
		for(int i=0; i<CardTypeList.length; i++){
			CardType nodeType = CardTypeList[i];
			CardRepositoryInfo cardRepositoryInfo = CardRepositoryInfoList[i];
			NodeType_CardRepositoryInfo_Map.put(nodeType, cardRepositoryInfo);
		}
	}

	//-- Inner Classes and Structures --------------------------
	private static class CardRepositoryInfo{
		private String dataPath;
		private String assetsPath;
		public CardRepositoryInfo(String dataPath, String assetsPath) {
			this.dataPath = dataPath;
			this.assetsPath = assetsPath;
		}
		public String getDataPath() {return dataPath;}
		public String getAssetsPath() {return assetsPath;}
	}
	//-- Delegates and Events ----------------------------------
	//-- Instance and Shared Fields ----------------------------
	//-- Properties --------------------------------------------
	//-- Constructors ------------------------------------------
	//-- Destructors -------------------------------------------
	//-- Base Class Overrides ----------------------------------
	//-- Public and internal Methods ---------------------------
	public static List<Card> loadCardsInfoWithType(CardType cardType){
		List<Card> result = new ArrayList<Card>();
		
		CardRepositoryInfo cardRepositoryInfo = NodeType_CardRepositoryInfo_Map.get(cardType);
		String dataPath = cardRepositoryInfo.getDataPath();
		String assetsPath = cardRepositoryInfo.getAssetsPath();
		
		try {
			File file = new File(Util.getAbsolutePath(dataPath));
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
	        Element root = doc.getRootElement();

	        //card_info node loop
	        for (Iterator<?> ite = root.elementIterator(); ite.hasNext();) {
	        	
	        	// get current element
	            Element ele = (Element) ite.next();
	            
	            // Card Object create
	            Card card = new Card();
	            card.setCardType(cardType);
	            card.setId(ele.element("id").getText());
	            card.setType(ele.element("type") == null ? null : ele.element("type").getText());
	            card.setCost(Integer.parseInt(ele.element("cost") == null ? "0" : ele.element("cost").getText()));
	            card.setAtk(Integer.parseInt(ele.element("atk") == null ? "0" : ele.element("atk").getText()));
	            card.setHp(Integer.parseInt(ele.element("hp") == null ? "0" : ele.element("hp").getText()));
	            card.setRace(ele.element("subtype") == null ? null : ele.element("subtype").getText());
	            card.setAbility(ele.element("abilities") == null ? null : ele.element("abilities").getText());
	    		String relativeFolderPath = assetsPath;
	    		card.setRelativePath(relativeFolderPath);
	            result.add(card);
	        } 
		} catch (Exception e) {
			Util.error(TAG, String.format("loadCardsInfoWithType(%s) failed:%s", cardType.toString(), e.getMessage()));
		}

		return result;
	}

	//-- Private and Protected Methods -------------------------
	//-- Event Handlers ----------------------------------------

}
