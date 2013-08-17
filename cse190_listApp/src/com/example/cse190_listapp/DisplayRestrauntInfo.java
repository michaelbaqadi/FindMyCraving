package com.example.cse190_listapp;

import java.util.List;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
/*
 	private Double distance; 
	private String r_name;
	private String hours;
	private String phoneNumber;
	private String address;
	private String category;
	private String averagePrice;
	private String date;
	private List<Dish> dish;
 * */
	public class DisplayRestrauntInfo extends MainActivity {
		
		Restaurant res;
		String r_nameString, hoursString,phoneNumberString,
			addressString,categoryString,averagePriceString,dateString,imageString,urlString;// this is the string used for the constructor
		double distance;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.restaurantinfo);
			
			populateRestrauntInfo();
			populateRestrauntInfoLayout();
		}

		/*
			Double distance, String r_name, String hours,
			String phoneNumber, String address, String category,
			String averagePrice, String date
		 * */

		private void populateRestrauntInfo() {
			urlString = "www.subway.com";distance =3.0;
			r_nameString = "subway";hoursString ="5-6pm";phoneNumberString = "619-415-9863";
			addressString = "1535 Granite Hills Dr"; 
			categoryString= "Chinease Food";averagePriceString = "11.99$";dateString = "20/12/2009";
			res = new Restaurant(distance,r_nameString,hoursString,phoneNumberString,addressString,
					categoryString,averagePriceString,dateString, urlString);
				
		}
		private void populateRestrauntInfoLayout() {
		 ImageView picture ;
		 /**
		  * This code here is not implemented will insert a picture for the restraunt
		  */
		 
		 
		 
		 
		 /**
		  *  Creating The text variables to setup text
		  * */
		 TextView r_nameText, hoursText,phoneNumberText,
			addressText,categoryText,averagePriceText,dateText,imageText, urlText;
		 r_nameText = (TextView) findViewById(R.id.restaurantname3);
		 hoursText = (TextView) findViewById(R.id.hours3);
		 phoneNumberText = (TextView) findViewById(R.id.phone3);
		 categoryText = (TextView) findViewById(R.id.category3);
		 averagePriceText = (TextView) findViewById(R.id.averageprice3);
		 dateText = (TextView) findViewById(R.id.date3);
		 urlText = (TextView) findViewById(R.id.url3);
		 
		 /**
		  *  inserting the text
		  * */
		 r_nameText.setText(res.getR_name());
		 hoursText.setText(res.getHours());
		 phoneNumberText.setText(res.getPhoneNumber());
		 categoryText.setText(res.getCategory());
		 averagePriceText.setText(res.getAveragePrice());
		 dateText.setText(res.getDate());
		 urlText.setText(res.getUrl());
		}
		
		
}
