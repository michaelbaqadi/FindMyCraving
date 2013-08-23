package com.example.cse190_listapp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.restaurant.com.DisplayRestaurantInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;
//hello
public class DishDetailsActivity extends Activity implements AsyncResponse {
	private Dish selectedDish = null;
	//private String prices = null;
	ArrayList<DishPrice> prices = null;
	ArrayList<DishCalories> calories = null;
	List<Review> reviews = new ArrayList<Review>();
	private final static String _getReviewsURL = "https://www.cakesbyannonline.com/cse190/sql_getReviews.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dish_details);
		
		selectedDish = getIntent().getExtras().getParcelable("currDish");
		prices = getIntent().getExtras().getParcelableArrayList("prices");
		calories = getIntent().getExtras().getParcelableArrayList("calories");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("dishID", selectedDish.getDishId()));
        initiateDataConnection(_getReviewsURL, params);
		
		populateDishView();
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
	public void processFinish(String result) {
		// TODO Auto-generated method stub
		populateReviewList(result);
		populateListView();
	}

	private void populateListView() {
		// TODO Auto-generated method stub
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter adapter = new ArrayAdapter(this,
				R.layout.display_review, reviews.toArray()) {
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
					    View row = convertView;
					    final int pos = position;
					    if(row == null){
					        //getting custom layout for the row
					        LayoutInflater inflater=getLayoutInflater();
					        row = inflater.inflate(R.layout.display_review, parent, false);
					    }
					    
					    Review currReview = reviews.get(position);
					    
					    TextView dateText = (TextView) row.findViewById(R.id.dishname);
						dateText.setText(currReview.getTimeStamp().substring(0, 9));
						
						TextView ratingText = (TextView) row.findViewById(R.id.rating_text);
						ratingText.setText(currReview.getComments());
					    
					    RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rating);
						ratingBar.setRating(currReview.getRating());
				 
					    return row; //the row that ListView draws
					}
				};
	}

	private void populateReviewList(String rawJSON) {
		// TODO Auto-generated method stub
		if(rawJSON.contains(new StringBuffer("ratingUserID"))){
			JSONArray jArry = null;
			try {
				jArry = new JSONArray(rawJSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0; i<jArry.length(); i++){
				JSONObject jsonDish = null;
				try {
					 jsonDish = jArry.getJSONObject(i);
					 reviews.add(new Review(jsonDish.getString("ratingUserID"),
							 jsonDish.getDouble("ratingRating"),
							 jsonDish.getString("ratingComments"),
							 jsonDish.getString("ratingTimeStamp"),
							 jsonDish.getString("userImagesURL")
							 ));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
				
			}
		}
	}

	private void populateDishView() {
		// TODO Auto-generated method stub
		TextView dishName = (TextView) findViewById(R.id.dish_name);
		dishName.setText(selectedDish.getDishName());
		
		TextView price = (TextView) findViewById(R.id.price);
		price.setText(getPricesString());
		
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
		
		Button call = (Button) findViewById(R.id.call_button);
		call.setText("Call: " + selectedDish.getRestaurantPhone());
		
	}

	private String getCaloriesString() {
		// TODO Auto-generated method stub
		String caloriesString = "Calories: ";
		for(int i = 0; i < calories.size(); i++){
			//DishPrice price = prices.get(i);
			//priceString += price.getDishPortion() + ": " + price.getDishPrice() + " ";
			caloriesString += calories.get(i).getPortionSize() + ": " + calories.get(i).getCalories() + " ";
		}
		return caloriesString;
	}

	private String getPricesString() {
		String priceString = "Price: ";
		for(int i = 0; i < prices.size(); i++){
			//DishPrice price = prices.get(i);
			//priceString += price.getDishPortion() + ": " + price.getDishPrice() + " ";
			priceString += (prices.get(i).getDishPortion() + ": " /*+ prices.get(i).getDishPrice() + " "*/);
		}
		return priceString;
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
				
		Intent intent = new Intent(this, WriteReviewActivity.class).putExtra("currDish",selectedDish);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Menu button */
	public void menuClick(View view) {
				
		Intent intent = new Intent(this, DisplayRestaurantMenu.class).putExtra("restaurantID", selectedDish.getRestaurantID());
		startActivity(intent);
	}
	
	public void goToRestaurantInfoPage(View view)
	{
		Intent intent = new Intent(this, DisplayRestaurantInfo.class).putExtra("restaurantID", selectedDish.getRestaurantID());
		startActivity(intent);
	}


}
