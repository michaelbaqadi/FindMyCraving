package com.example.cse190_listapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
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

	}

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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		// setVlauesToTextView(R.id.hello_id,"abcd");
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