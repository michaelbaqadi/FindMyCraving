package com.example.cse190_listapp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;


public class EditProfileActivity extends Activity implements AsyncResponse {
	// Class Variables
	String userName;
	String userPassword;
	String userEmail;
	EditText user;
	EditText password1;
	EditText password2;
	EditText email;
	private final String _updateProfile = "https://cakesbyannonline.com/cse190/sql_updateProfile.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#881113")));
		getActionBar().show();
		setContentView(R.layout.editprofile);
		// Populate content from preferences 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		userName = prefs.getString("userName", "false");
		userPassword = prefs.getString("userPassword", "false");
		userEmail = prefs.getString("userEmail", "false");
		Log.d("EditProfile Credentials: ", userName + " " + userPassword + " " + userEmail);
		
		if(!userEmail.equals("false")){
			user = (EditText)findViewById(R.id.editProUserName);
			user.setText(userName);
			email = (EditText)findViewById(R.id.editProEmail);
			email.setText(userEmail);
			password1 = (EditText)findViewById(R.id.editProPassword1);
			password1.setText(userPassword);
			password2 = (EditText)findViewById(R.id.editProPassword2);
			password2.setText(userPassword);
			
			
		}
	}
	public void initiateDataConnection(String url, List<NameValuePair> params ){
        ConnectivityManager connMgr = (ConnectivityManager) 
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        	DownloadWebpageTask webtask = new DownloadWebpageTask();
        	webtask.delegate = this;
        	webtask.setParams(params);
        	webtask.execute(url);        	
        } else {
        	//Create a toast popup
        	Context context = getApplicationContext();
        	CharSequence text = "No network connection available.";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
        }
    }
	/** Called when the user clicks the Update button */
	public void updateClick(View view) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String oldUserEmail = prefs.getString("userEmail", "false");
		
		//TODO: client side authenticate edited data
		
		//TODO: authenticate data
		String pw1 = password1.getText().toString().trim();
		String pw2 = password2.getText().toString().trim();
		String userName = user.getText().toString().trim();
		String userEmail = email.getText().toString().trim();
		
		if(!pw1.equalsIgnoreCase(pw2)){
			Context context = getApplicationContext();
		    CharSequence text = "Passwords Do Not Match, Try Again!";
		    int duration = Toast.LENGTH_SHORT;
		    Toast toast = Toast.makeText(context, text, duration);
		    toast.show();
		} else if (pw1.equalsIgnoreCase("") || pw2.equalsIgnoreCase("")){
			Context context = getApplicationContext();
		    CharSequence text = "Please Enter a Password";
		    int duration = Toast.LENGTH_SHORT;
		    Toast toast = Toast.makeText(context, text, duration);
		    toast.show();
		} else if (userName.length() == 0){
			// Issue validating empty text field once user clicks into it.
			Context context = getApplicationContext();
		    CharSequence text = "Please Enter a User Name";
		    int duration = Toast.LENGTH_SHORT;
		    Toast toast = Toast.makeText(context, text, duration);
		    toast.show();
		} else if (!(userEmail.contains("@") && userEmail.contains("."))){
			Context context = getApplicationContext();
		    CharSequence text = "Please Enter a Valid Email Address";
		    int duration = Toast.LENGTH_SHORT;
		    Toast toast = Toast.makeText(context, text, duration);
		    toast.show();
			
		 } else {
			// Add parameters for php server
				List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("userName", user.getText().toString()));
		        params.add(new BasicNameValuePair("userPassword", password1.getText().toString()));
		        params.add(new BasicNameValuePair("newUserEmail", email.getText().toString()));
		        params.add(new BasicNameValuePair("userEmail", oldUserEmail));
		        
		        //Send Data to server
		        initiateDataConnection(_updateProfile, params);
		 }		
	}
	//Called when data from server is returned
	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		
		// Process Response
		String status = "";
		JSONObject json = null;
		// Create json object for status
		try {
			json = new JSONObject(output);
			status = json.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(status.equals("update successful")){
			//if successful goto main dish page
			Context context = getApplicationContext();
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			  SharedPreferences.Editor editor = preferences.edit();
			  editor.putString("userEmail", email.getText().toString());
			  editor.putString("userPassword", password1.getText().toString());
			  editor.putString("userName", user.getText().toString());
			  editor.commit();
			Toast toast = Toast.makeText(context, "Update Successful", Toast.LENGTH_LONG);
		    toast.show();
		    
		    
			Intent intent = new Intent(this, DisplayDishesActivity.class);
			startActivity(intent);
		} else {
			
			Context context = getApplicationContext();
			Toast toast = Toast.makeText(context, "Update Faied, Please Try Again", Toast.LENGTH_LONG);
		    toast.show();
			// Display Retry Do not change activities
			
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
	
	

}
