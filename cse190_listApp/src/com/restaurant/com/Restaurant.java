package com.restaurant.com;
//import com.dishes.com.*;

import java.util.List;

import com.place.com.*;

public class Restaurant extends Place {
	
	public Restaurant(String id, String icon, String name, String vicinity,
			Double latitude, Double longitude, Double distance, String r_name,
			String hours, String phoneNumber, String address, String category,
			String averagePrice, String date, String url) {
		super(id, icon, name, vicinity, latitude, longitude);
		this.r_name = r_name;
		this.hours = hours;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.category = category;
		this.averagePrice = averagePrice;
		this.date = date;
		this.url = url;
		
	}
	public Restaurant( String r_nameString,
			String hoursString, String phoneNumberString, String addressString,
			String categoryString, String averagePriceString,
			String dateString, String urlString) {
		
			this.r_name = r_nameString;
			this.hours = hoursString;
			this.phoneNumber = phoneNumberString;
			this.address = addressString;
			this.category = categoryString;
			this.averagePrice = averagePriceString;
			this.date = dateString;
			this.url = urlString; 
	}
	private String r_name;
	private String hours;
	private String phoneNumber;
	private String address;
	private String category;
	private String averagePrice;
	private String date;
	private String url; 

	
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
