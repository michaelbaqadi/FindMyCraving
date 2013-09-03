package com.example.cse190_listapp;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;
import com.restaurant.com.DisplayRestaurantInfo.DownloadImageTask;

public class DisplayDishesActivity extends Activity implements AsyncResponse {
	List<Dish>  dish = new  ArrayList<Dish>();
	List<DishPrice> prices = new ArrayList<DishPrice>();
	List<DishCalories> calories = new ArrayList<DishCalories>();
	double longitude=0;
	double latitude=0;
	
	static int imgCounter = 0;
	private static final boolean DEBUG_GPS = true;
	private static final String _getDishURL = "https://www.cakesbyannonline.com/cse190/sql_getDishTest.php";
	ProgressDialog progress_dialog ;
	final String _server = "http://www.cakesbyannonline.com/cse190/image_dish_sm/";

	//private static int numReturns = 0;
	private int searchClick = 0;
	ArrayAdapter<Dish> adapter;
	
	private LocationManager lm;
	
	private final LocationListener locationListener = new LocationListener() {
	    
		public void onLocationChanged(Location location) {
	        longitude = location.getLongitude();
	        latitude = location.getLatitude();
	        /*
	        Context context = getApplicationContext();
	    	CharSequence text = new StringBuilder().append(latitude).toString() + new StringBuilder().append(longitude).toString();
	    	int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
	        */
	    }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			lm.removeUpdates(locationListener);
			Context context = getApplicationContext();
	    	CharSequence text = "GPS Unavailable";
	    	int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Context context = getApplicationContext();
	    	CharSequence text = "GPS Enabled";
	    	int duration = Toast.LENGTH_SHORT;
	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#881113")));
		getActionBar().show();
		//popup dialog for loading
		progress_dialog = new ProgressDialog(DisplayDishesActivity.this);
	    progress_dialog.setMessage("Loading please wait..");
	    progress_dialog.setCancelable(false);
	    progress_dialog.show();
		
		setContentView(R.layout.mainpage);
		/************** GPS ****************/
		
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
		Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		DecimalFormat df = new DecimalFormat("###.######");
		
		 //Debug gps
		 if (loc != null ){
	            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
	            longitude =  Double.parseDouble(df.format(loc.getLongitude()));
	    		latitude = Double.parseDouble(df.format(loc.getLatitude()));
	        }else{
	        	Toast.makeText(this, "GPS is Disabled in your devide", Toast.LENGTH_SHORT).show();
	        }
		
				
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 100, locationListener);
		
		/************** DATA REQUESTS **************/
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lat", new StringBuilder().append(latitude).toString()));
        params.add(new BasicNameValuePair("long", new StringBuilder().append(longitude).toString()));
        
		getDishData(params);
		
		
		EditText search = (EditText)findViewById(R.id.autoCompleteTextView1);
		search.setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	
		        	
		        	//Hid the keyboard after hitting search
		    		InputMethodManager inputManager = (InputMethodManager)
		                    getSystemService(Context.INPUT_METHOD_SERVICE); 

		    		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
		                       InputMethodManager.HIDE_NOT_ALWAYS);
		    		
		    		EditText search = (EditText)findViewById(R.id.autoCompleteTextView1);
		    		if(!search.getText().toString().equals("")){
		    			search = (EditText)findViewById(R.id.autoCompleteTextView1);
			    		
			    		// Create paramters for the data request
			    		List<NameValuePair> params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("lat", new StringBuilder().append(latitude).toString()));
			            params.add(new BasicNameValuePair("long", new StringBuilder().append(longitude).toString()));
			            params.add(new BasicNameValuePair("searchTerm", search.getText().toString()));
			            
			            //Show popup
			            progress_dialog = new ProgressDialog(DisplayDishesActivity.this);
			    	    progress_dialog.setMessage("Loading please wait..");
			    	    progress_dialog.setCancelable(false);
			    	    progress_dialog.show();
			            
			            //Empty Dish, prices, calories for new data
			            dish.removeAll(dish);
			        	prices.removeAll(prices);
			        	calories.removeAll(calories);
			        	
			        	// Get the dish data
			        	getDishData(params);
			        	//getDishImages();
			    		searchClick = 1;			        	
			        	
			    		//if(dish.size() == 0){
						//	progress_dialog.cancel();
						//}
			        	/* Done Button Hit Debug
			        	Context context = getApplicationContext();
				    	CharSequence text = "Done Button Hit";
				    	int duration = Toast.LENGTH_SHORT;
				    	Toast toast = Toast.makeText(context, text, duration);
				    	toast.show();
			        	*/
		    		}
			            return true;
			        }
			        else {
			            return false;
			        }
			    }
		    
		});
		
		//when you click a dish, go to dish details page
		ListView lv = (ListView) findViewById(R.id.dishlistview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                	Dish selectedDish = dish.get(position);
                	//TextView dishPrice = (TextView) findViewById(R.id.dishPrice1);
                	//String dishPrices = (String) dishPrice.getText();
                	Intent intent = new Intent(getApplicationContext(), DishDetailsActivity.class);
                	intent.putExtra("currDish", selectedDish);
                	intent.putParcelableArrayListExtra("prices", (ArrayList<? extends Parcelable>) selectedDish.getPrices());
                	intent.putParcelableArrayListExtra("calories", (ArrayList<? extends Parcelable>) selectedDish.getCalories());
                	//intent.putExtra("prices", dishPrices);
            		startActivity(intent);
                }
            });
	}
	//Used for image requests
	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	    int position;

	    public DownloadImageTask(int position) {
	       // this.bmImage = bmImage;
	        this.position = position;
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
	        // set the dish Bitmap
	    	dish.get(position).setDishImage(result);
	    	//Increment image counter
	    	imgCounter++;
	    	//Log.d("ImgCount", Integer.toString(imgCounter));
	    	//Log.d("dishSize", Integer.toString(dish.size()));
	    	if(imgCounter == dish.size()){
	    		ArrayAdapter<Dish> adapter = new MyListAdapter();
				ListView list = (ListView)findViewById(R.id.dishlistview);
				list.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				progress_dialog.cancel();
				imgCounter = 0;
	    	}
	    	
	    	
	    }
	}
	
	public void clearSearch(View view){
	
		EditText search = (EditText)findViewById(R.id.autoCompleteTextView1);
		String testStr = search.getText().toString().trim();
		Log.d("search_string", testStr);
		if(testStr.length() > 0){
			search.setText("");
			search.requestFocus();
			//Empty Dish, prices, calories for new data
	        dish.removeAll(dish);
	    	prices.removeAll(prices);
	    	calories.removeAll(calories);
	    	
	    	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("lat", new StringBuilder().append(latitude).toString()));
	        params.add(new BasicNameValuePair("long", new StringBuilder().append(longitude).toString()));
	        
	        //POPUP
	        //Show popup
	        progress_dialog = new ProgressDialog(DisplayDishesActivity.this);
		    progress_dialog.setMessage("Loading please wait..");
		    progress_dialog.setCancelable(false);
		    progress_dialog.show();
		    
	    	getDishData(params);
	    	getDishImages();
		}
	}
	
	private void getDishData(List<NameValuePair> params){
		initiateDataConnection(_getDishURL, params);
		//initiateDataConnection(_getDishCaloriesURL, params);
		//initiateDataConnection(_getDishPriceURL, params);
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
	public void processFinish(String result){
		
		try {
			populateDishList(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(searchClick == 0){
			populateListView();
		} else {	
			ArrayAdapter<Dish> adapter = new MyListAdapter();
			ListView list = (ListView)findViewById(R.id.dishlistview);
			list.setAdapter(adapter);
			//list.requestFocus();
			Log.v("status:", "Dish Size " + Integer.toString(dish.size()));
			//list.setEmptyView(findViewById(R.id.emptyElement));
			if(dish.size()==0){
				progress_dialog.cancel();
				adapter.clear();
			} else {
			adapter.notifyDataSetChanged();
			}
		}		
	}
	
	//Populates dish List, price List, and Calories List
	private void populateDishList(String rawJSON) throws JSONException {
		
		//Get the main JSON Object
		JSONObject json = null;
		json = new JSONObject(rawJSON);
		Log.d("dishJsonConverted: ", "true");
		
		//Get each JSON Array to work with (dishes, prices, calories)
		String dishStr = json.getString("dishes");
		
		JSONArray dishJsonArry = new JSONArray(dishStr);
		
		String priceStr = json.getString("prices");
		JSONArray priceJsonArry = new JSONArray(priceStr);
		
		String calorieStr = json.getString("calories");
		JSONArray calorieJsonArry = new JSONArray(calorieStr);
		
		// Populate dish, price, calories array lists
		
		for(int i=0; i<dishJsonArry.length(); i++){
			JSONObject jsonDish = dishJsonArry.getJSONObject(i);
			String rating="";
			if(!jsonDish.getString("rating").equals("null")){
				rating = jsonDish.getString("rating");
			}else{
				rating = "0";
			}
			
			dish.add(new Dish(jsonDish.getString("dishID"),
			 jsonDish.getString("dishName"),
			 jsonDish.getString("dishDescription"),
			 //jsonDish.getDouble("rating"),
			 Double.parseDouble(rating),
			 jsonDish.getString("dishImageSmURL"),
			 jsonDish.getString("dishImageLrgURL"),
			 jsonDish.getString("restaurantID"),
			 jsonDish.getString("restaurantName"),
			 jsonDish.getString("restaurantPhone"),
			 jsonDish.getString("restaurantStreet1"),
			 jsonDish.getString("restaurantStreet2"),
			 jsonDish.getString("restaurantCity"),
			 jsonDish.getString("restaurantState"),
			 jsonDish.getString("restaurantZip"),
			 jsonDish.getString("restaurantURL")
			 ));
		}
		
		
		for(int i=0; i<calorieJsonArry.length(); i++){
			 JSONObject jsonDish = calorieJsonArry.getJSONObject(i);
			 String cal = jsonDish.getString("dishCalories");
			 String dishid = jsonDish.getString("dishCalorieDishID");
			 String caloriePortion = jsonDish.getString("dishCaloriePortionSize");
			 calories.add(new DishCalories(dishid, cal, caloriePortion));
		}
		
		for(int i=0; i<priceJsonArry.length(); i++){
		 	 JSONObject jsonDish = priceJsonArry.getJSONObject(i);
			 String price = jsonDish.getString("dishPrice");
			 String dishid = jsonDish.getString("dishPriceDishID");
			 String dishPortion = jsonDish.getString("dishPricePortionSize");
			 prices.add(new DishPrice(dishid, price, dishPortion));
		}
		if(dish.size() > 0){
			getDishImages();
			populateDishesWithPrices();
			populateDishesWithCalories();
		} 
	}
	private void getDishImages(){
		for(int i = 0; i<dish.size(); i++){
			new DownloadImageTask(i).execute(_server + dish.get(i).getPictureSm());
		}
	}
	
	private void populateDishesWithPrices(){
		for(int i = 0; i<dish.size(); i++){
			for(int j = 0; j<prices.size(); j++){
				if(prices.get(j).getDishID().equalsIgnoreCase(dish.get(i).getDishId())){
					dish.get(i).setPrices(prices.get(j).getDishPrice(), prices.get(j).getDishPortion());
				}
			}
		}
	}
	
	private void populateDishesWithCalories(){
		for(int i = 0; i<dish.size(); i++){
			for(int j = 0; j<calories.size(); j++){
				if(calories.get(j).getDishID().equalsIgnoreCase(dish.get(i).getDishId())){
					dish.get(i).setCalories(calories.get(j).getCalories(), calories.get(j).getPortionSize());
				}
			}
		}
	}
	private void populateListView() { 
				
			ArrayAdapter<Dish> adapter = new MyListAdapter();
			ListView list = (ListView)findViewById(R.id.dishlistview);
			list.setAdapter(adapter);
			list.setEmptyView(findViewById(R.id.emptyElement));
			
			//list.requestFocus();
		
		
	}
	public class MyListAdapter extends ArrayAdapter<Dish>
	{

		public MyListAdapter() {
			super(DisplayDishesActivity.this, R.layout.mainpage,dish);
			
		} 
		@Override
	    public View getView(int position, View convertView, ViewGroup parent)
		{  	
		// make sure we have a view to work with
		View itemview = convertView;
		if(itemview == null)
		{
			itemview = getLayoutInflater().inflate(R.layout.itemview,parent,false);
		}
		
			Dish d = dish.get(position);
			
			ImageView imageview = (ImageView)itemview.findViewById(R.id.dishpicture);
			imageview.setImageResource(R.drawable.sm_place_holder);
			if(d.getDishImage() != null){
				imageview.setImageBitmap(d.getDishImage());
			}
			
			// Create a String of Prices
			String priceString = "";
			for(int i = 0; i<prices.size(); i++){
				if(d.getDishId().equalsIgnoreCase(prices.get(i).getDishID())){
					priceString += "$" + prices.get(i).getDishPrice() + "  ";
				}
			}
			//Create a String of Calories
			String caloriesString = "";
			for(int i = 0; i<calories.size(); i++){
				if(d.getDishId().equalsIgnoreCase(calories.get(i).getDishID())){
					caloriesString += calories.get(i).getCalories() + "  ";
				}
			}
			
			TextView dishName = (TextView) itemview.findViewById(R.id.dishname);
			dishName.setText(d.getDishName());
			
			TextView dishPrice = (TextView) itemview.findViewById(R.id.dishPrice1);

			dishPrice.setText("Price: " + priceString);
			
			TextView dishCalories = (TextView) itemview.findViewById(R.id.dishCalories2);
			dishCalories.setText("Calories: " + caloriesString);
			
			TextView restaurantName = (TextView) itemview.findViewById(R.id.restaurantname3);
			restaurantName.setText(d.getRestaurantName());
			
			RatingBar ratingBar = (RatingBar) itemview.findViewById(R.id.rating2);
			ratingBar.setRating(d.getRating());
			
			return itemview;
			
				
		}
	}
	
	private void setListAdapter(ArrayAdapter<String> arrayAdapter) {
		// TODO Auto-generated method stub
		
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
