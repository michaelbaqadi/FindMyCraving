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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendWebService.AsyncResponse;
import com.backendWebService.DownloadWebpageTask;

public class DisplayDishesActivity extends Activity implements AsyncResponse {
	List<Dish>  dish = new  ArrayList<Dish>();
	private static final String _getDishURL = "https://www.cakesbyannonline.com/cse190/sql_getDish.php";
	DownloadWebpageTask webtask = new DownloadWebpageTask();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		webtask.delegate = this;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lat", "1"));
        params.add(new BasicNameValuePair("long", "1"));

		
		initiateDataConnection(_getDishURL, params);
		
		//when you click a dish, go to dish details page
		ListView lv = (ListView) findViewById(R.id.dishlistview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                	Intent intent = new Intent(getApplicationContext(), DishDetailsActivity.class);
            		startActivity(intent);
                }

            });
		
		//populateDishList();
		//populateListView();
	}
	public void initiateDataConnection(String url, List<NameValuePair> params ){
        ConnectivityManager connMgr = (ConnectivityManager) 
            getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
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
		populateListView();
	     // Parse JSON String
	     // Create Objects
	    // Populate views, etc
	}


	/*
	 * public Dish(String dishName, String picture, String calories, String price,
			String discription, String rating
	 * */
	private void populateDishList(String rawJSON) {
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
				 dish.add(new Dish(jsonDish.getString("dishName") ,"png",3500,24.00,
						 jsonDish.getString("dishDescription"),3,R.drawable.ic_launcher,jsonDish.getString("restaurantName")));
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		dish.add(new Dish("Food2","png",3.4,4.0,"It's good",3,R.drawable.ic_launcher,"pizaa around table"));
		
	}
	private void populateListView() {
		ArrayAdapter<Dish> adapter = new MyListAdapter(); 
		ListView list = (ListView)findViewById(R.id.dishlistview);
		list.setAdapter(adapter);
		
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
			imageview.setImageResource(d.getDishId());
			TextView dishName = (TextView) itemview.findViewById(R.id.dishname);
			dishName.setText(d.getDishName());		
			TextView restaurantName = (TextView) itemview.findViewById(R.id.restaurantname3);
			restaurantName.setText(d.getRes());
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
	
	/** Called when the user clicks on a list item */
	/*public void dishListItemClick(View view) {
			
		Intent intent = new Intent(this, DishDetailsActivity.class);
		startActivity(intent);
	}*/
}
