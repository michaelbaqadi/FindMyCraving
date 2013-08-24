package com.example.cse190_listapp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
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
		CharSequence toastMessage = "Your review was posted!";
	
		// get the rating value in float here
		RatingBar mBar = (RatingBar) findViewById(R.id.user_rating);
		float rating = mBar.getRating();
		
		//getting the text from the textbox
		EditText reviewBox = (EditText) findViewById(R.id.reviewtextbox);
		if (reviewBox.getText().toString().length() != 0){
			String textValue = reviewBox.getText().toString();
			
		List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userID", "1"));
			params.add(new BasicNameValuePair("rating",String.valueOf(rating) ));
			params.add(new BasicNameValuePair("comments", textValue));
			params.add(new BasicNameValuePair("dishID", selectedDish.getDishId()));
			params.add(new BasicNameValuePair("restID", selectedDish.getRestaurantID()));
		   	int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(context, toastMessage, duration);
	    	toast.show();
			
			Intent intent = new Intent(getApplicationContext(), DishDetailsActivity.class);
			startActivity(intent);
			
		}
		else 
		{
		   	int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(context, "You need to enter a review", duration);
	    	toast.show();
		} 
	}

}