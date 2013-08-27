package com.example.cse190_listapp;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.helpermethods.com.*;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logged_out, menu);
		return true;
		//setVlauesToTextView(R.id.hello_id,"abcd");
	}

	private void setValuesToTextView(int ID, String value) {
		TextView textview = null;
		View view = null;
		 textview = (TextView) view.findViewWithTag(ID);
		 textview.setText(value);				 
		
	}

}


