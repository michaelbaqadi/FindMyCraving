package com.restaurant.com;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cse190_listapp.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayRestaurantInfo extends Activity {

	// The JSON REST Service I will pull from
	static String url = "https://www.cakesbyannonline.com/cse190/sql_getRestaurantInfo.php";
	// Will hold the values I pull from the JSON 
	static String restaurantName = "";
	static String restaurantStreet1 = "";
	static String restaurantStreet2 = "";
	static String restaurantCity = "";
	static String restaurantState = "";
	static String restaurantZip = "";
	static String restaurantPhone = "";
	static String restaurantEmail = "";
	static String restaurantURL = "";
	static String restaurantCategory = "";
	static String restaurantAvgPriceRating = "";
	static String restaurantTimestamp= "";
	static String restaurantImageURL = "";
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Get any saved data
		super.onCreate(savedInstanceState);
		
		// Point to the name for the layout xml file used
		setContentView(R.layout.restaurantinfo);

		// Call for doInBackground() in MyAsyncTask to be executed
		new MyAsyncTask().execute();

	}
	// Use AsyncTask if you need to perform background tasks, but also need
	// to change components on the GUI. Put the background operations in
	// doInBackground. Put the GUI manipulation code in onPostExecute

	private class MyAsyncTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... arg0) {

			 
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			
			// Define that I want to use the POST method to grab data from
			// the provided URL
			HttpPost httppost = new HttpPost(url);
			
			// Web service used is defined
			httppost.setHeader("Content-type", "application/json");

			// Used to read data from the URL
			InputStream inputStream = null;
			
			// Will hold the whole all the data gathered from the URL
			String result = null;

			try {
				
				// Get a response if any from the web service
				HttpResponse response = httpclient.execute(httppost);        
				
				// The content from the requested URL along with headers, etc.
				HttpEntity entity = response.getEntity();

				// Get the main content from the URL
				inputStream = entity.getContent();
				
				// JSON is UTF-8 by default
				// BufferedReader reads data from the InputStream until the Buffer is full
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				
				// Will store the data
				StringBuilder theStringBuilder = new StringBuilder();

				String line = null;
				
				// Read in the data from the Buffer untilnothing is left
				while ((line = reader.readLine()) != null)
				{
					
					// Add data from the buffer to the StringBuilder
					theStringBuilder.append(line + "\n");
				}
				
				// Store the complete data in result
				result = theStringBuilder.toString();

			} catch (Exception e) { 
				e.printStackTrace();
			}
			finally {
				
				// Close the InputStream when you're done with it
				try{if(inputStream != null)inputStream.close();}
				catch(Exception e){}
			}

			// Holds Key Value pairs from a JSON source
			JSONObject jsonObject;
			try {

				// Delete cbfunc( and ); from the results
				
				result = result.substring(1, result.length()-1);

				jsonObject = new JSONObject(result);
				 
				//JSONObject res = jsonObject.getJSONObject("restaurantName");
				 restaurantName = jsonObject.getString("restaurantName");
				 restaurantStreet1 = jsonObject.getString("restaurantStreet1");
				 restaurantStreet2 = jsonObject.getString("restaurantStreet2");
				 restaurantCity = jsonObject.getString("restaurantCity");
				 restaurantState = jsonObject.getString("restaurantName");
				 restaurantZip = jsonObject.getString("restaurantState");
				 restaurantPhone = jsonObject.getString("restaurantPhone");
				 restaurantEmail = jsonObject.getString("restaurantEmail");
				 restaurantURL = jsonObject.getString("restaurantURL");
				 restaurantCategory = jsonObject.getString("restaurantCategory");
				 restaurantAvgPriceRating = jsonObject.getString("restaurantAvgPriceRating");
				 restaurantTimestamp= jsonObject.getString("restaurantTimestamp");
				 restaurantImageURL = jsonObject.getString("restaurantImageURL");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;

		}

		protected void onPostExecute(String result){

			// Gain access so I can change the TextViews
			TextView line1 = (TextView)findViewById(R.id.line1); 
			TextView line2 = (TextView)findViewById(R.id.line2); 
			TextView line3 = (TextView)findViewById(R.id.line3); 
			TextView line4 = (TextView)findViewById(R.id.line4); 
			TextView line5 = (TextView)findViewById(R.id.line5); 
			TextView line6 = (TextView)findViewById(R.id.line6); 
			TextView line7 = (TextView)findViewById(R.id.line7); 
			TextView line8 = (TextView)findViewById(R.id.line8); 
			TextView line9 = (TextView)findViewById(R.id.line9); 
			TextView line10 = (TextView)findViewById(R.id.line10); 
			TextView line11 = (TextView)findViewById(R.id.line11); 
			TextView line12 = (TextView)findViewById(R.id.line12); 
 
				
			line1.setText("Name: " + restaurantName);
			line2.setText("restaurantStreet1: " +restaurantStreet1);
			line3.setText("restaurantStreet2: " + restaurantStreet2);
			line4.setText("City: " + restaurantCity);
			line5.setText("State: " + restaurantState);
			line6.setText("ZipCode: " + restaurantZip);
			line7.setText("Phone: " + restaurantPhone);
			line8.setText("Email: " + restaurantEmail);
			line9.setText("WebSite: " + restaurantURL);
			line10.setText ("Category: " + restaurantCategory);
			line11.setText("Average Price: " + restaurantAvgPriceRating);
			line12.setText("Opening Hours: " + restaurantTimestamp);
		 

		}

	}

}
