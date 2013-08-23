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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;
import com.example.cse190_listapp.R;

public class DisplayRestaurantInfo extends Activity implements AsyncResponse {
	private static final String HTML_FORMAT = "<html><body style=\"text-align: center; background-color: black; vertical-align: center;\"><img src = \"%s\" /></body></html>";
	String imgUrl = " ";
	private static final String _getresInfo 
			= "https://www.cakesbyannonline.com/cse190/sql_getRestaurantInfo.php";
	DownloadWebpageTask webtask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurantinfo);;
		webtask = new DownloadWebpageTask();
		webtask.delegate = this;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
       // params.add(new BasicNameValuePair("lat", "1"));
        //params.add(new BasicNameValuePair("long", "1"));
			
		
		initiateDataConnection(_getresInfo, params);
    	TextView line1 = (TextView)findViewById(R.id.line1); 
    	line1.setText("Michael");
		
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
			
			final String HTML_FORMAT = 
						"<html><body style=\"text-align: center; background-color: black; vertical-align: center;\"><img src = \"%s\" /></body></html>";
			WebView mWebView = null;
			mWebView = (WebView) findViewById(R.id.restaurantpicture);
			imgUrl  ="http://tineye.com/images/widgets/mona.jpg";
		    final String html = String.format(HTML_FORMAT, imgUrl);
		    
		    mWebView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
		    mWebView.getSettings().setUseWideViewPort(false); 
    }
		
}
