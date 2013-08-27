package com.example.cse190_listapp;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;


public class EditProfileActivity extends Activity {
	// Class Variables
	String userName;
	String userPassword;
	String userEmail;
	EditText user;
	EditText password1;
	EditText password2;
	EditText email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().show();
		setContentView(R.layout.editprofile);
		// Populate content from preferences 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		userName = prefs.getString("userName", "false");
		userPassword = prefs.getString("userPassword", "false");
		userEmail = prefs.getString("userEmail", "false");
		
		if(!userEmail.equalsIgnoreCase("false")){
			user = (EditText)findViewById(R.id.editProUserName);
			user.setText(userName);
			email = (EditText)findViewById(R.id.editProEmail);
			email.setText(userEmail);
			password1 = (EditText)findViewById(R.id.editProPassword1);
			password1.setText(userPassword);
			password2 = (EditText)findViewById(R.id.editProPassword1);
			password2.setText(userPassword);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String username = preferences.getString("isLoggedIn", "false");
		if(username.equals("false"))
		{
			getMenuInflater().inflate(R.menu.logged_out, menu);
		}
		else{		// 
			getMenuInflater().inflate(R.menu.logged_in, menu);
		}
		return true;
		//setVlauesToTextView(R.id.hello_id,"abcd");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_edit_profile:
	        	Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
	    		startActivity(intent);
	            return true;
	        case R.id.action_log_out:
	        	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	        	SharedPreferences.Editor editor = preferences.edit();
				editor.putString("isLoggedIn", "false");
				editor.commit();
				Intent intent2 = new Intent(getApplicationContext(), DisplayDishesActivity.class);
	    		startActivity(intent2);
	            return true;
	        case R.id.action_log_in_sign_up:
	        	Intent intent3 = new Intent(getApplicationContext(), FirstLaunchActivity.class);
	    		startActivity(intent3);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/** Called when the user clicks the Update button */
	public void updateClick(View view) {
		
		//TODO: authenticate edited data
		
		Intent intent = new Intent(this, DisplayDishesActivity.class);
		startActivity(intent);
	}

}
