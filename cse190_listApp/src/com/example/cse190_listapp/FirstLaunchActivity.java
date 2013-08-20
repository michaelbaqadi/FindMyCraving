package com.example.cse190_listapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class FirstLaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_launch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Log In button */
	public void logInClick(View view) {
		
		//TODO: authenticate data
		
		Intent intent = new Intent(this, DisplayDishesActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Sign Up button */
	public void signUpClick(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Browse button */
	public void browseClick(View view) {
		Intent intent = new Intent(this, DisplayDishesActivity.class);
		startActivity(intent);
	}

}
