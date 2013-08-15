package com.example.cse190_listapp;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class displayRestaurantMenu extends Activity {
	List<Dish>  dish = new  ArrayList<Dish>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayrestaurantmenu);
		
		populateDishList();
		populateListView();
	}


	/*
			public Dish(String dishName, String picture, double calories, Double price,
			String discription, int rate, int dishId, String res)
	 * */
	private void populateDishList() {
		dish.add(new Dish("Food1","png",3.6,5.0,"It's good",3,R.drawable.ic_launcher,"Subway"));
		dish.add(new Dish("Food2","png",3.7,4.0,"It's good",3,R.drawable.ic_launcher,"pizaa around table"));
		
	}
	private void populateListView() {
		ArrayAdapter<Dish> adapter = new MyListAdapter(); 
		ListView list = (ListView)findViewById(R.id.dishlistview2);
		list.setAdapter(adapter);
		
	}
	public class MyListAdapter extends ArrayAdapter<Dish>
	{

		public MyListAdapter() {
			super(displayRestaurantMenu.this, R.layout.mainpage,dish);
			
		} 
		@Override
	    public View getView(int position, View convertView, ViewGroup parent)
		{  	
			// make sure we have a view to work with
			View itemview = convertView;
			if(itemview == null)
			{
				itemview = getLayoutInflater().inflate(R.layout.displaymenuitem,parent,false);
			}
			Dish d = dish.get(position);
			ImageView imageview = (ImageView)itemview.findViewById(R.id.dishpicture2);
			imageview.setImageResource(d.getDishId());
			TextView dishName = (TextView) itemview.findViewById(R.id.dishname2);
			dishName.setText(d.getDishName());		 
			
			TextView price = (TextView) itemview.findViewById(R.id.price2);
			String price_str= Double.toString(d.getPrice());
			price.setText(price_str + "$");
			
			TextView calories = (TextView) itemview.findViewById(R.id.calories2);
			String cal_str= Double.toString(d.getCalories());
			calories.setText(cal_str + " Calories");
			
			TextView rate = (TextView) itemview.findViewById(R.id.rating2);
			String rating_str= Integer.toString(d.getRating());
			rate.setText("Rating: " + rating_str);
			
			return itemview;
				
		}
	}
	
	private void setListAdapter(ArrayAdapter<String> arrayAdapter) {
		// TODO Auto-generated method stub
		
	}
}
