package com.example.cse190_listapp;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RestaurantMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayrestaurantmenu);
		
		//when you click a dish, go to dish details page
		ListView lv = (ListView) findViewById(R.id.dishlistview2);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                	Intent intent = new Intent(getApplicationContext(), DishDetailsActivity.class);
            		startActivity(intent);
                }

            });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Sign Up button */
	public void signUpClick(View view) {
		
		//TODO: authenticate sign-up data
		
		Intent intent = new Intent(this, DisplayDishesActivity.class);
		startActivity(intent);
	}

}