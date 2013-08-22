package com.example.cse190_listapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class WriteReviewActivity extends Activity {

	private Dish selectedDish = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_review);
		
		selectedDish = getIntent().getExtras().getParcelable("currDish");
		TextView dishName = (TextView) findViewById(R.id.dish_name);
		dishName.setText(selectedDish.getDishName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		//setVlauesToTextView(R.id.hello_id,"abcd");
	}
	
	/** Called when the user clicks the Update button */
	public void postClick(View view) {
		
		Context context = getApplicationContext();
    	CharSequence text = "Your review was posted!";
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
		
		Intent intent = new Intent(this, DishDetailsActivity.class);
		startActivity(intent);
	}

}