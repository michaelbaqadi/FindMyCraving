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

public class FirstLaunchActivity extends Activity implements AsyncResponse{
	private static final String _loginUrl = "https://cakesbyannonline.com/cse190/sql_login.php";
	
	EditText userEmail;
	EditText userPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().show();
		setContentView(R.layout.first_launch);
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
	
	/** Called when the user clicks the Log In button */
	public void logInClick(View view) {
		
		//TODO: authenticate data
		userEmail = (EditText) findViewById(R.id.username_edit);
		userPassword = (EditText) findViewById(R.id.password_edit);
		String email = userEmail.getText().toString().trim();
		String password = userPassword.getText().toString().trim();
		
		if (!(email.contains("@") && email.contains("."))){
		Context context = getApplicationContext();
	    CharSequence text = "Please Enter a Valid Email Address";
	    int duration = Toast.LENGTH_SHORT;
	    Toast toast = Toast.makeText(context, text, duration);
	    toast.show();
		} else if (password.equalsIgnoreCase("")){
			Context context = getApplicationContext();
		    CharSequence text = "Please Enter a Password";
		    int duration = Toast.LENGTH_SHORT;
		    Toast toast = Toast.makeText(context, text, duration);
		    toast.show();
		} else {
			login(email, password);
		}
	}
	public void login(String userEmail, String userPassword){
		// Call the database and see if the user name and email are already in use.
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userEmail", userEmail));
        params.add(new BasicNameValuePair("userPassword", userPassword));
        
        initiateDataConnection(_loginUrl, params);
        
        
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

	@Override
	public void processFinish(String output) {
		// Need to add a preference state on login / logout
		// Also change first launch pref
		
		String status = null;
		String userName = "";
		JSONObject json = null;
		try {
			json = new JSONObject(output);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			status = json.getString("status");
			Log.v("status", status);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			userName = json.getString("userName");
			Log.v("status", status);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(status.equalsIgnoreCase("match")){
			//Create a toast popup
        	Context context = getApplicationContext();
        	CharSequence text = "login Successful";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
        	
        	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			  SharedPreferences.Editor editor = preferences.edit();
			  editor.putString("userEmail", userEmail.getText().toString());
			  editor.putString("userPassword", userPassword.getText().toString());
			  editor.putString("userName", userName);
			  editor.putString("isLoggedIn", "true");
			  editor.commit();
        	// Create user object
        	
        	Intent intent = new Intent(this, DisplayDishesActivity.class);
			startActivity(intent);
        	
		}
		else {
			Context context = getApplicationContext();
        	CharSequence text = "Login Failed, Please Try Again";
        	int duration = Toast.LENGTH_LONG;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
		}
		
		
	}

}
