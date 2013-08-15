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

public class DisplayDishesActivity extends Activity {
	List<Dish>  dish = new  ArrayList<Dish>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		
		populateDishList();
		populateListView();
	}


	/*
	 * public Dish(String dishName, String picture, String calories, String price,
			String discription, String rating
	 * */
	private void populateDishList() {
		dish.add(new Dish("Food1","png",3.4,4.0,"It's good",3,R.drawable.ic_launcher,"Subway"));
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
			TextView restaurantName = (TextView) itemview.findViewById(R.id.restaurantname);
			restaurantName.setText(d.getRes());
			return itemview;
				
		}
	}
	
	private void setListAdapter(ArrayAdapter<String> arrayAdapter) {
		// TODO Auto-generated method stub
		
	}
}
