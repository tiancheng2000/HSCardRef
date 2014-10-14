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

package com.hscardref.generic.common;

/**
 * Manage properties/constants which can be tuned by developers.
 * <i>Project-specific</i> contents should be moved to project-scope generic.common.Constant class.
 */
public class Constant {
	//--- Application related -----------------------------
	public static final String APP_FULLNAME = "Hearthstone Card Reference";
	public static final String APP_SHORTNAME = "HSCardRef";
	public static final String UNCAUGHTEXCEPTION_RECEIVER_MAIL = "tchu@psh.com.cn";

	//--- System dependent --------------------------------

	//--- File Operation ----------------------------------
    
	//--- Data Access -------------------------------------
	//--- Data Representation -----------------------------

    //--- Screen Layout -----------------------------------
    public static class Layout{
        public class Orientation{
        	public static final int HORIZONTAL = 1;
        	public static final int VERTICAL = 2;
        }
        public class Dimension{
        	public static final int MATCH_PARENT = -1;
        	public static final int WRAP_CONTENT = -2;
        }
    }

	//--- [Project-specific] Property Change Event, Event Observer/Handler -----------------------------------
    //FD: Notify the property change of XxxViewModel (by using ViewModelBase's pcs scheme). Subscribers(XxxView/Control) will judge acceptance based on Event.source,.propertyName.
    public static class PropertyName{
    	public static final String ID = "id";
    	public static final String Name = "name";
    	public static final String Content = "content";
    	public static final String IsBusy = "isBusy";
    	//public static final String NodeType = "nodeType";
    	public static final String NodeList = "nodeList";
    	public static final String Node = "node";
    }
    
    //--- [Project-specific] NodeSelector ------------------------------------
    public static final String[] EXTENSIONS_IMAGE = new String[]{"jpg", "png", "gif", "jpeg", "bmp"};
    public static final String[] EXTENSIONS_TEXT = new String[]{"txt"};
    public static final String[] EXTENSIONS_VIDEO = new String[]{"3gp", "mp4", "wmv", "avi"};
    public static final String[] EXTENSIONS_AUDIO = new String[]{"mp3", "wma", "mid"};
    public static final String[] EXTENSIONS_XMLDATA = new String[]{"xml"};

    public class FilterType{
    	public static final int CRYSTAL	= 0;
    	public static final int HP 		= 1;
    	public static final int ATTACK	= 2;
    }

    public class CardType{
    	public static final String MINION 	= "M";
    	public static final String SPELL 		= "S";
    	public static final String WEAPON 	= "W";
    }
    
    public class CardRace{
    	public static final String MURLOC 	= "MU";
    	public static final String DEMON 		= "DE";
    	public static final String BEAST 		= "BE";
    	public static final String DRAGON 	= "DR";
    	public static final String PIRATE 	= "PI";
    	public static final String TOTEM 	= "TO";  //TODO
    }

    public class CardAbility{
    	public static final String BATTLECRY 		= "battlecry";
    	public static final String CHARGE 		= "charge";
    	public static final String COMBO 			= "combo";
    	public static final String DEATHRATTLE 	= "deathrat";
    	public static final String DIVINESHIELD 	= "divineshd";
    	public static final String OVERLOAD 		= "overload";
    	public static final String SECRET 		= "secret";
    	public static final String SILENCE 		= "silence";
    	public static final String STEALTH 		= "stealth";
    	public static final String TAUNT 			= "taunt";
    	public static final String WINDFURY 		= "windfury";
    	public static final String CHOICE 		= "choice";
    	public static final String ENRAGE 		= "enrage";
    	public static final String SPELLDAMAGE	= "spellpw";
    }
    
    //--- [Project-specific] Drag & Drop -------------------------------------
	public static class DragLabel{
		//public static final String Resource_Text = "Resource_Text";
		public static final String Nothing = "Nothing";
	}
	public static String getDragLabel(Object object){
		//if(object instanceof TextResource){
		//	return DragLabel.Resource_Text;
		//}
		return DragLabel.Nothing;
	}

    //--- Log related -------------------------------------
    //Log Tags -- reference to LogTag.java  
    //Log Messages: internally used only!!

}
