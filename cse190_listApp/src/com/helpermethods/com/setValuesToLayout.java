package com.helpermethods.com;

import android.R;
import android.R.id;
import android.R.layout;
import android.view.View;
import android.widget.TextView;

public class setValuesToLayout {
    /*
     *  This function set value of text to a specfic id of type TextView in the layout
     * */
	public void _setText(id ID,String value)
	{
		TextView textview = null;
		View view = null;
		 textview = (TextView) view.findViewWithTag(ID);
		 textview.setText(value);
				 
	}
}
