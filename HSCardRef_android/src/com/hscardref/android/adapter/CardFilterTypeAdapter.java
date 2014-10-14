package com.hscardref.android.adapter;

import com.hscardref.android.common.Constant;
import com.hscardref.R;
import com.thinkalike.generic.common.Util;
import com.thinkalike.generic.viewmodel.control.INodeView;
import com.thinkalike.generic.viewmodel.control.UINode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class CardFilterTypeAdapter extends BaseAdapter {
	//-- Constants and Enums ----------------------------------------------
	public static final String TAG = CardFilterTypeAdapter.class.getSimpleName();
	
	//-- Inner Classes and Structures -------------------------------------
	//-- Delegates and Events ---------------------------------------------
	//-- Instance and Shared Fields ---------------------------------------
	private LayoutInflater mInflater;
	protected int[] _images;
	protected int[] _values;
	
	//-- Properties -------------------------------------------------------
	//-- Constructors -----------------------------------------------------
	public CardFilterTypeAdapter(Context context, int[] images, int[] values) {
		assert (images != null);
		mInflater = LayoutInflater.from(context);
		_images = images;
		_values = values;
	}
	
	//-- Destructors ------------------------------------------------------
	//-- Base Class Overrides ---------------------------------------------
	@Override
	public int getCount() {
		return _images.length;
	}
	@Override
	public Object getItem(int position) {
		return _values[position];
	}
	@Override
	public long getItemId(int position) {
		return position; 
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Util.trace(TAG, "getView:+ " + position + " " + convertView);
		convertView = mInflater.inflate(R.layout.item,null);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.iv_item);
		imageView.setBackgroundResource(_images[position]);
		return convertView;
	}
	
	//-- Public and internal Methods --------------------------------------
	//-- Private and Protected Methods ------------------------------------
	//-- Event Handlers ---------------------------------------------------

}
