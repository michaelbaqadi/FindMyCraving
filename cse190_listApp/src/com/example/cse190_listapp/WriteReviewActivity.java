package com.example.cse190_listapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class WriteReviewActivity extends Activity implements AsyncResponse {

	private Dish selectedDish = null;
	List<DishPrice> prices = new ArrayList<DishPrice>();
	List<DishCalories> calories = new ArrayList<DishCalories>();
	DownloadWebpageTask webtask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String imgUrl ="";
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().show();
		setContentView(R.layout.write_review);

		webtask = new DownloadWebpageTask();
		webtask.delegate = this;

		selectedDish = getIntent().getExtras().getParcelable("currDish");
		prices = getIntent().getExtras().getParcelableArrayList("prices");
		calories = getIntent().getExtras().getParcelableArrayList("calories");
		TextView dishName = (TextView) findViewById(R.id.dish_name);
		dishName.setText(selectedDish.getDishName());
		
		TextView resName = (TextView) findViewById(R.id.resnamewrite);
		resName.setText(selectedDish.getRestaurantName());
		
		
		// to download a picture
		
		// send restaurant id
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("restID", getIntent().getExtras().getString("restID")));
        //imgUrl = selectedDish.getPictureLrg();
        imgUrl = "http://cakesbyannonline.com/cse190/image_dish_lrg/Tomato-Cheese-Pizza.jpg";
        new DownloadImageTask((ImageView) findViewById(R.id.dish_picture))
        .execute(imgUrl);
        
        
        
        
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
	//////////////////////////////////////

	public void initiateDataConnection(String url, List<NameValuePair> params) {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			DownloadWebpageTask webtask = new DownloadWebpageTask();
			webtask.delegate = this;
			webtask.setParams(params);
			webtask.execute(url);
		} else {
			// Create a toast popup
			Context context = getApplicationContext();
			CharSequence text = "No network connection available.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
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

	/** Called when the user clicks the Update button */
	public void postClick(View view) {
		String _getReviewsURL = "https://www.cakesbyannonline.com/cse190/sql_createReview.php";
		Context context = getApplicationContext();
		CharSequence toastMessage = "Your review was posted!";

		// get the rating value in float here
		RatingBar mBar = (RatingBar) findViewById(R.id.user_rating);
		float rating = mBar.getRating();

		// getting the text from the textbox
		EditText reviewBox = (EditText) findViewById(R.id.reviewtextbox);
		if (reviewBox.getText().toString().length() != 0) {
			String textValue = reviewBox.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userID", "1"));
			params.add(new BasicNameValuePair("rating", String.valueOf(rating)));
			params.add(new BasicNameValuePair("comments", textValue));
			params.add(new BasicNameValuePair("dishID", selectedDish
					.getDishId()));
			params.add(new BasicNameValuePair("restID", selectedDish
					.getRestaurantID()));
			initiateDataConnection(_getReviewsURL, params);

		} else {
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, "You need to enter a review",
					duration);
			toast.show();
		}
	}

	@Override
	public void processFinish(String output) {

		Context context = getApplicationContext();
		CharSequence toastMessage = "Your review was posted!";

		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, toastMessage, duration);
		toast.show();

		Intent intent = new Intent(context, DishDetailsActivity.class);
		intent.putExtra("currDish", selectedDish);
		intent.putParcelableArrayListExtra("prices",
				(ArrayList<? extends Parcelable>) prices);
		intent.putParcelableArrayListExtra("calories",
				(ArrayList<? extends Parcelable>) calories);
		startActivity(intent);

	}

}