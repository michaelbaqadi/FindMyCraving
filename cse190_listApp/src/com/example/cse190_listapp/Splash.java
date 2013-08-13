
package com.example.cse190_listapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
 		Thread timer = new Thread()
		{
			public void run()
			{
				try{
					sleep(10000);
				}
				catch(InterruptedException e) 
				{
					e.printStackTrace();
				}
				finally
				{
					Intent mainScreen = new Intent("android.intent.action.start");
					startActivity(mainScreen);
				}
			}
		};
		timer.start();
	}

}