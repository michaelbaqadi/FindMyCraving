package com.example.cse190_listapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class EditProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		//setVlauesToTextView(R.id.hello_id,"abcd");
	}
	
	/** Called when the user clicks the Update button */
	public void updateClick(View view) {
		
		//TODO: authenticate edited data
		
		Intent intent = new Intent(this, DisplayDishesActivity.class);
		startActivity(intent);
	}

}
