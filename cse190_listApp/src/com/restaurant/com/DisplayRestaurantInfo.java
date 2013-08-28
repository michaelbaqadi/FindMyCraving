package com.restaurant.com;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;
import com.example.cse190_listapp.DisplayDishesActivity;
import com.example.cse190_listapp.EditProfileActivity;
import com.example.cse190_listapp.FirstLaunchActivity;
import com.example.cse190_listapp.R;

public class DisplayRestaurantInfo extends Activity implements AsyncResponse {
	private static final String HTML_FORMAT = "<html><body style=\"text-align: center; background-color: black; vertical-align: center;\"><img src = \"%s\" /></body></html>";
	String imgUrl  = "";
	private static final String _getresInfo 
			= "https://www.cakesbyannonline.com/cse190/sql_getRestaurantInfo.php";
	String urlres = " ";
	DownloadWebpageTask webtask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().show();
		setContentView(R.layout.restaurantinfo);;
		webtask = new DownloadWebpageTask();
		webtask.delegate = this;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("restID", getIntent().getExtras().getString("restID")));
        new DownloadImageTask((ImageView) findViewById(R.id.restaurantpicture))
        .execute(imgUrl);	
        TextView line8 = (TextView)findViewById(R.id.line8); 
		initiateDataConnection(_getresInfo, params);
		line8.setOnClickListener(new OnClickListener() {	 
			public void onClick(View arg0) {
	 
				Intent intent = new Intent(Intent.ACTION_VIEW, 
				     Uri.parse(urlres));
				startActivity(intent);
	 
			}
	 
		});
	}
	public void uploadWebsite(View view)
	{
		Context context = getApplicationContext();
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, "it's clickable", duration);
    	toast.show();
		Intent intent = new Intent(Intent.ACTION_VIEW, 
				Uri.parse("http://www.mkyong.com"));
			startActivity(intent);
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


	public void initiateDataConnection(String url, List<NameValuePair> params ){
    	TextView line1 = (TextView)findViewById(R.id.line1); 
    	line1.setText("Michael");
        ConnectivityManager connMgr = (ConnectivityManager) 
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        	webtask.setParams(params);
        	webtask.execute(url);   
        } else {
        	TextView line2 = (TextView)findViewById(R.id.line1); 
        	line1.setText("Michael");
        	//Create a toast popup
        	Context context = getApplicationContext();
        	CharSequence text = "No network connection available.";
        	int duration = Toast.LENGTH_SHORT;
        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
        }
    }
	public void processFinish(String result){
		populateResList(result);
		
	}


	/*
	 * public Dish(String dishName, String picture, String calories, String price,
			String discription, String rating
	 * */
	private void populateResList(String result) {
		String restaurantName = "";
		String restaurantStreet1 = "";
		String restaurantStreet2 = "";
		String restaurantCity = "";
		String restaurantState = "";
		String restaurantZip = "";
		String restaurantPhone = "";
		String restaurantEmail = "";
		String restaurantURL = "";
		String restaurantCategory = "";
		String restaurantAvgPriceRating = "";
		String restaurantTimestamp= "";
		String restaurantImageURL = "";
		
		result = result.substring(1, result.length()-1);
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(result);
			 restaurantName = jsonObject.getString("restaurantName");
			 restaurantStreet1 = jsonObject.getString("restaurantStreet1");
			 restaurantStreet2 = jsonObject.getString("restaurantStreet2");
			 restaurantCity = jsonObject.getString("restaurantCity");
			 restaurantState = jsonObject.getString("restaurantState");
			 restaurantZip = jsonObject.getString("restaurantZip");
			 restaurantPhone = jsonObject.getString("restaurantPhone");
			 restaurantEmail = jsonObject.getString("restaurantEmail");
			 restaurantURL = jsonObject.getString("restaurantURL");
			 restaurantCategory = jsonObject.getString("restaurantCategory");
			 restaurantAvgPriceRating = jsonObject.getString("restaurantAvgPriceRating");
			 restaurantTimestamp= jsonObject.getString("restaurantTimestamp");
			 restaurantImageURL = jsonObject.getString("restaurantImageURL");
			 restaurantImageURL = "http://cakesbyannonline.com/cse190/image_dish_lrg/Tomato-Cheese-Pizza.jpg";
			 urlres = restaurantURL;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//JSONObject res = jsonObject.getJSONObject("restaurantName");

			 
			TextView line1 = (TextView)findViewById(R.id.line1); 
			TextView line2 = (TextView)findViewById(R.id.line2); 
			TextView line3 = (TextView)findViewById(R.id.line3); 
			TextView line4 = (TextView)findViewById(R.id.line4); 
			TextView line5 = (TextView)findViewById(R.id.line5); 
			TextView line6 = (TextView)findViewById(R.id.line6); 
			TextView line7 = (TextView)findViewById(R.id.line7); 
			TextView line8 = (TextView)findViewById(R.id.line8); 

			line1.setText(restaurantName);
			line2.setText("Category: " + restaurantCategory);
			line3.setText("Average Price: " + restaurantAvgPriceRating + "$");
			
			line4.setText(restaurantStreet1 + " " +restaurantStreet2);
			line5.setText(restaurantCity + ", " + restaurantState + " " + restaurantZip);
			line6.setText(restaurantPhone);
			
			line7.setText(restaurantEmail);
			line8.setText(restaurantURL);
			
			new DownloadImageTask((ImageView) findViewById(R.id.restaurantpicture))
	        .execute(restaurantImageURL);	
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
		
}
