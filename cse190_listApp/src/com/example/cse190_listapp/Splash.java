
package com.example.cse190_listapp;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;

public class Splash extends Activity {
//hello
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		   getActionBar().hide();
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    //getActionBar().hide();
	    
		setContentView(R.layout.splash);
 		Thread timer = new Thread()
		{
			public void run()
			{
				try{
					sleep(5000);
				}
				catch(InterruptedException e) 
				{
					e.printStackTrace();
				}
				finally
				{
					finish();
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					String username = preferences.getString("isLoggedIn", "false");
					if(username.equals("false"))
					{
						Intent mainScreen = new Intent("android.intent.action.start");
						startActivity(mainScreen);
					}
					else {
						Intent browse = new Intent(getApplicationContext(), DisplayDishesActivity.class);
						startActivity(browse);
					}
				}
			}
		};
		timer.start();
	}

}