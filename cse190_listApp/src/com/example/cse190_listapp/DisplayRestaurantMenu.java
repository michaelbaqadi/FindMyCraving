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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;

public class DisplayRestaurantMenu extends Activity implements AsyncResponse {
	List<Dish>  dish = new  ArrayList<Dish>();
	List<DishPrice> prices = new ArrayList<DishPrice>();
	List<DishCalories> calories = new ArrayList<DishCalories>();
	static int counter = 0;
	private static final String _getDishURL = "https://www.cakesbyannonline.com/cse190/sql_getDish.php";
	private static final String _getDishCaloriesURL = "https://www.cakesbyannonline.com/cse190/sql_getDishCalories.php";
	private static final String _getDishPriceURL = "https://www.cakesbyannonline.com/cse190/sql_getDishPrice.php";
	private static int numReturns = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayrestaurantmenu);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lat", "1"));
        params.add(new BasicNameValuePair("long", "1"));
        
	 
		params.add(new BasicNameValuePair
				("restID", getIntent().getExtras().getString("restaurantID")));
		
		initiateDataConnection(_getDishURL, params);
		initiateDataConnection(_getDishCaloriesURL, params);
		initiateDataConnection(_getDishPriceURL, params);
		
		
		//when you click a dish, go to dish details page
		ListView lv = (ListView) findViewById(R.id.dishlistview6);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                	Dish selectedDish = dish.get(position);
                	Intent intent = new Intent(getApplicationContext(), DishDetailsActivity.class).putExtra("currDish",selectedDish);
            		startActivity(intent);
                }
            });
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
		populateDishList(result);
		if(numReturns >=3){
			populateDishesWithPrices();
			populateDishesWithCalories();
			Log.d("numReturns: ", new StringBuilder(numReturns).toString());
			populateListView();
			numReturns = 0;
			
		}
	     // Parse JSON String
	     // Create Objects
	    // Populate views, etc
	}
	//Populates dish List, price List, and Calories List
	private void populateDishList(String rawJSON) {
		if(rawJSON.contains(new StringBuffer("dishName"))){
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
					 dish.add(new Dish(jsonDish.getString("dishID"),
							 jsonDish.getString("dishName"),
							 jsonDish.getString("dishDescription"),
							 jsonDish.getDouble("rating"),
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
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
				
			}
			numReturns++;
		} else if(rawJSON.contains(new StringBuffer("dishCalorieDishID"))){
			JSONArray jArry = null;
			try {
				jArry = new JSONArray(rawJSON);
				Log.v("Price Json: ", rawJSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0; i<jArry.length(); i++){
				JSONObject jsonDish = new JSONObject();
				try { 
					jsonDish = jArry.getJSONObject(i);
					 String cal = jsonDish.getString("dishCalories");
					 String dishid = jsonDish.getString("dishCalorieDishID");
					 String caloriePortion = jsonDish.getString("dishCaloriePortionSize");
					 calories.add(new DishCalories(dishid, cal, caloriePortion));
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.v("status:", "Left Json Parser");
			}
			numReturns++;

		} else if(rawJSON.contains(new StringBuffer("dishPriceDishID"))){
			JSONArray jArry = null;
			try {
				jArry = new JSONArray(rawJSON);
				Log.v("Price Json: ", rawJSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0; i<jArry.length(); i++){
				JSONObject jsonDish = new JSONObject();
				try { 
					jsonDish = jArry.getJSONObject(i);
					 String price = jsonDish.getString("dishPrice");
					 String dishid = jsonDish.getString("dishPriceDishID");
					 String dishPortion = jsonDish.getString("dishPricePortionSize");
					 prices.add(new DishPrice(dishid, price, dishPortion));
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.v("status:", "Left Json Parser");
			}
			numReturns++;
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
					dish.get(i).setPrices(calories.get(j).getCalories(), calories.get(j).getPortionSize());
				}
			}
		}
	}
	private void populateListView() {
		ArrayAdapter<Dish> adapter = new MyListAdapter(); 
		ListView list = (ListView)findViewById(R.id.dishlistview6);
		list.setAdapter(adapter);
		
	}
	public class MyListAdapter extends ArrayAdapter<Dish>
	{

		public MyListAdapter() {
			super(DisplayRestaurantMenu.this, R.layout.displayrestaurantmenu,dish);
			
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
			//ImageView imageview = (ImageView)itemview.findViewById(R.id.dishpicture);
			//imageview.setImageResource(d.getDishId());
			
			// Create a String of Prices
			String priceString = "";
			for(int i = 0; i<prices.size(); i++){
				if(d.getDishId().equalsIgnoreCase(prices.get(i).getDishID())){
					priceString += prices.get(i).getDishPortion() + ": " + prices.get(i).getDishPrice() + " ";
					d.setPrices(prices.get(i).getDishPrice(), prices.get(i).getDishPrice());
				}
			}
			//Create a String of Calories
			String caloriesString = "";
			for(int i = 0; i<calories.size(); i++){
				if(d.getDishId().equalsIgnoreCase(calories.get(i).getDishID())){
					caloriesString += calories.get(i).getPortionSize() + ": " + calories.get(i).getCalories() + " ";
				}
			}
			
			TextView dishName = (TextView) itemview.findViewById(R.id.dishname);
			dishName.setText(d.getDishName());
			
			TextView dishPrice = (TextView) itemview.findViewById(R.id.dishPrice1);

			dishPrice.setText("Price: " + priceString);
			
			TextView dishCalories = (TextView) itemview.findViewById(R.id.dishCalories2);
			dishCalories.setText("Calories: " + caloriesString);
			
			TextView restaurantName = (TextView) itemview.findViewById(R.id.restaurantname3);
			restaurantName.setText(" ");
			
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
		getMenuInflater().inflate(R.menu.main, menu);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
