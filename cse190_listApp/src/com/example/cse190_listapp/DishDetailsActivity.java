package com.example.cse190_listapp;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.com.DisplayRestaurantInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DishDetailsActivity extends Activity{
	private Dish selectedDish = null;
	List<DishPrice> prices = new ArrayList<DishPrice>();
	List<DishCalories> calories = new ArrayList<DishCalories>();
	String imgUrl  = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#881113")));
		getActionBar().show();
		setContentView(R.layout.dish_details);
		
		selectedDish = getIntent().getExtras().getParcelable("currDish");
		prices = getIntent().getExtras().getParcelableArrayList("prices");
		calories = getIntent().getExtras().getParcelableArrayList("calories");
		
		//set the write review button to not clickable if user is not logged in
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String username = preferences.getString("isLoggedIn", "false");
		if(username.equals("false"))
		{
			ImageButton writeReview = (ImageButton) findViewById(R.id.write_review_button);
			writeReview.setImageResource(R.drawable.details_write_review_inactive);
			/*writeReview.setVisibility(View.GONE);
			writeReview.setVisibility(View.INVISIBLE);
			writeReview.setClickable(false);*/
		}
		
        //imgUrl = "http://cakesbyannonline.com/cse190/image_dish_lrg/Tomato-Cheese-Pizza.jpg";
        imgUrl = "http://cakesbyannonline.com/cse190/image_dish_lrg/"+ selectedDish.getPictureLrg();
        new DownloadImageTask((ImageView) findViewById(R.id.dish_picture)).execute(imgUrl);
		
		populateDishView();
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}

	private void populateDishView() {
		// TODO Auto-generated method stub
		TextView dishName = (TextView) findViewById(R.id.dish_name);
		dishName.setText(selectedDish.getDishName());
		
		TextView price1 = (TextView) findViewById(R.id.price1);
		TextView price2 = (TextView) findViewById(R.id.price2);
		TextView price3 = (TextView) findViewById(R.id.price3);
		
		if(prices.size() >= 1) { 
			price1.setText("$" + prices.get(0).getDishPrice());
		}
		else {
			price1.setVisibility(View.INVISIBLE);
		}
		
		if(prices.size() >= 2) {
			price2.setText("$" + prices.get(1).getDishPrice());
		}
		else {
			price2.setVisibility(View.INVISIBLE);
		}
		
		if(prices.size() >= 3) {
			price3.setText("$" + prices.get(2).getDishPrice());
		}
		else {
			price3.setVisibility(View.INVISIBLE);
		}
		
		TextView calories = (TextView) findViewById(R.id.calories);
		calories.setText(getCaloriesString());
		
		RatingBar rating = (RatingBar) findViewById(R.id.rating);
		rating.setRating(selectedDish.getRating());
		
		TextView dishDescription = (TextView) findViewById(R.id.dish_description);
		dishDescription.setText(selectedDish.getDescription());
		
		TextView restaurantName = (TextView) findViewById(R.id.restaurant_name);
		restaurantName.setText(selectedDish.getRestaurantName());
		
		String address = "";
		address += selectedDish.getRestaurantStreet1() + "\n";
		if(!selectedDish.getRestaurantStreet2().equals("")) {
			address += selectedDish.getRestaurantStreet2() + "\n";
		}
		address += selectedDish.getRestaurantCity() + ", ";
		address += selectedDish.getRestaurantState() + " ";
		address += selectedDish.getRestaurantZip();
		TextView restaurantAddress = (TextView) findViewById(R.id.restaurant_address);
		restaurantAddress.setText(address);
		
		//not needed because now it's an Image button
		//Button call = (Button) findViewById(R.id.call_button);
		//call.setText("Call: " + selectedDish.getRestaurantPhone());
		
	}

	private String getCaloriesString() {
		// TODO Auto-generated method stub
		String caloriesString = "Calories: ";
		for(int i = 0; i < calories.size(); i++){
			caloriesString += /*calories.get(i).getPortionSize() + ": " +*/ calories.get(i).getCalories() + " ";
		}
		return caloriesString;
	}

	private String getPricesString() {
		String priceString = "";
		for(int i = 0; i < prices.size(); i++){
			priceString += /*prices.get(i).getDishPortion() + ": " +*/  "$" + prices.get(i).getDishPrice();
		}
		return priceString;
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
	
	/** Called when the user clicks the Write Review button */
	public void writeReviewClick(View view) {
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		 String username = preferences.getString("isLoggedIn", "false");
		 if(username.equals("false"))
		 {
			Context context = getApplicationContext();
			CharSequence toastMessage = "Please log in/sign up to write a review";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, toastMessage, duration);
			toast.show();
		 }
		 else{		// 
			//finish();
			Intent intent = new Intent(this, WriteReviewActivity.class).putExtra("currDish",selectedDish);
			intent.putParcelableArrayListExtra("prices", (ArrayList<? extends Parcelable>) prices);
	    	intent.putParcelableArrayListExtra("calories", (ArrayList<? extends Parcelable>) calories);
			startActivity(intent);
		}			
	}
	
	/** Called when the user clicks the Read Reviews button */
	public void readReviewsClick(View view) {
		Intent intent = new Intent(this, ReadReviewsActivity.class).putExtra("currDish",selectedDish);
		intent.putParcelableArrayListExtra("prices", (ArrayList<? extends Parcelable>) prices);
    	intent.putParcelableArrayListExtra("calories", (ArrayList<? extends Parcelable>) calories);
		startActivity(intent);		
	}
	
	/** Called when the user clicks the Menu button */
	public void menuClick(View view) {
				
		Intent intent = new Intent(this, DisplayRestaurantMenu.class).putExtra("restaurantID", selectedDish.getRestaurantID());
		startActivity(intent);
	}
	
	public void goToRestaurantInfoPage(View view)
	{
		Intent intent = new Intent(this, DisplayRestaurantInfo.class).putExtra("restID", selectedDish.getRestaurantID());
		startActivity(intent);
	}
	
	
	/** Called when the user clicks the Call button */
	public void callClick(View view) {
				
		Intent callIntent = new Intent(Intent.ACTION_CALL);
	    callIntent.setData(Uri.parse("tel:"+selectedDish.getRestaurantPhone()));
	    startActivity(callIntent);
	}
	
	/** Called when the user clicks the Call button */
	public void directionsClick(View view) {
		
		String address = "";
		address += selectedDish.getRestaurantStreet1() + "\n";
		if(!selectedDish.getRestaurantStreet2().equals("")) {
			address += selectedDish.getRestaurantStreet2() + "\n";
		}
		address += selectedDish.getRestaurantCity() + ", ";
		address += selectedDish.getRestaurantState() + " ";
		address += selectedDish.getRestaurantZip();
				
		Intent directions = new Intent(android.content.Intent.ACTION_VIEW,
			    Uri.parse("google.navigation:q=" + address));
		startActivity(directions);
	}


}
