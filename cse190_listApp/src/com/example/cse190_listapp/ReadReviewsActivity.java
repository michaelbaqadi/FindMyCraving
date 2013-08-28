package com.example.cse190_listapp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;
//hello
public class ReadReviewsActivity extends Activity implements AsyncResponse {
	private Dish selectedDish = null;
	List<DishPrice> prices = new ArrayList<DishPrice>();
	List<DishCalories> calories = new ArrayList<DishCalories>();
	List<Review> reviews = new ArrayList<Review>();
	private final static String _getReviewsURL = "https://www.cakesbyannonline.com/cse190/sql_getReviews.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#881113")));
		getActionBar().show();
		setContentView(R.layout.read_reviews);
		
		selectedDish = getIntent().getExtras().getParcelable("currDish");
		prices = getIntent().getExtras().getParcelableArrayList("prices");
		calories = getIntent().getExtras().getParcelableArrayList("calories");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("dishID", selectedDish.getDishId()));
        initiateDataConnection(_getReviewsURL, params);
        
        TextView calories = (TextView) findViewById(R.id.reviews_dish_name);
		calories.setText(selectedDish.getDishName());
		
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
				R.layout.display_review, reviews) {
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
					    
					    TextView userName = (TextView) row.findViewById(R.id.username);
						userName.setText(currReview.getUserName());
					    
					    TextView dateText = (TextView) row.findViewById(R.id.date_text);
						dateText.setText(formatDate(currReview.getTimeStamp()));
						
						TextView ratingText = (TextView) row.findViewById(R.id.rating_text);
						ratingText.setText(currReview.getComments());
					    
					    RatingBar ratingBar = (RatingBar) row.findViewById(R.id.rating);
						ratingBar.setRating(currReview.getRating());
				 
					    return row; //the row that ListView draws
					}
				};
				
				ListView reviewsList = (ListView)findViewById(R.id.reviews_list);
				reviewsList.setAdapter(adapter);
	}

	protected String formatDate(String timeStamp) {
		// TODO Auto-generated method stub
		return timeStamp.substring(5, 10) +  "-" + timeStamp.substring(0, 4);
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
				JSONObject jsonReview = null;
				try {
					 jsonReview = jArry.getJSONObject(i);
					 reviews.add(0, new Review(jsonReview.getString("ratingUserID"),
							 jsonReview.getString("userName"),
							 jsonReview.getDouble("ratingRating"),
							 jsonReview.getString("ratingComments"),
							 jsonReview.getString("ratingTimeStamp"),
							 //jsonReview.getString("userImagesURL")
							 "null user image"
							 ));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
				
			}
		}
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

}