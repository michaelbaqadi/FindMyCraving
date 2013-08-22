package com.example.cse190_listapp;
import com.restaurant.com.DisplayRestaurantInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DishDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dish_details);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/** Called when the user clicks the Write Review button */
	public void writeReviewClick(View view) {
				
		Intent intent = new Intent(this, WriteReviewActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Menu button */
	public void menuClick(View view) {
				
		Intent intent = new Intent(this, RestaurantMenuActivity.class);
		startActivity(intent);
	}
	public void goToRestaurantInfoPage(View view)
	{
		Intent intent = new Intent(this, DisplayRestaurantInfo.class);
		startActivity(intent);
	}

}
