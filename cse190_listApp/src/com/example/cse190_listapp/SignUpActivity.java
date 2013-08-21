package com.example.cse190_listapp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;

public class SignUpActivity extends Activity implements AsyncResponse {
	private static final String _checkUser = "http://cakesbyannonline.com/cse190/sql_createUser.php";
	
	EditText userNameInput;
	EditText userEmailInput;
	EditText userPassword1;
	EditText userPassword2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singup);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void addUser(String userName, String userEmail, String userPassword){
		// Call the database and see if the user name and email are already in use.
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", userName));
		params.add(new BasicNameValuePair("userEmail", userEmail));
        params.add(new BasicNameValuePair("userPassword", userPassword));
        
        initiateDataConnection(_checkUser, params);
        
        
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

	
	/** Called when the user clicks the Sign Up button */
	public void signUpClick(View view) {
		
		//TODO: authenticate sign-up data
		
		// Do some validation
		
		//Check to see if the current user email is being used
		
		userEmailInput = (EditText) findViewById(R.id.email2);
		userNameInput = (EditText) findViewById(R.id.userNameInput);
		userPassword1 = (EditText) findViewById(R.id.password1Input);
		userPassword2 = (EditText) findViewById(R.id.password2Input);
		
		String pw1 = userPassword1.getText().toString().trim();
		String pw2 = userPassword2.getText().toString().trim();
		String userName = userNameInput.getText().toString().trim();
		String userEmail = userEmailInput.getText().toString().trim();
		
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
			 addUser(userName, userEmail, pw1);
		 }
		
		/*
		Intent intent = new Intent(this, DisplayDishesActivity.class);
		startActivity(intent);
		*/
	}

	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		
    	String status = null;
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
		
		if(status.equalsIgnoreCase("user exists")){
			//Create a toast popup
        	Context context = getApplicationContext();
        	CharSequence text = "User Email Already Exists";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
		}
		else if(status.equalsIgnoreCase("user added")){
			// should move to the main activity at this point
			// putting in toast for now
			//Create a toast popup
        	Context context = getApplicationContext();
        	CharSequence text = "User Added";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();	
		}
		else {
			Context context = getApplicationContext();
        	CharSequence text = "No Status";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
		}
	}
}
