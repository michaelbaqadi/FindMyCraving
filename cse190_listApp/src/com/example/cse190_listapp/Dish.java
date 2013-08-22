package com.example.cse190_listapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public  class Dish implements Parcelable{
	
	private String  dishName;
	private String description;
	private float rating;
	private String dishId;
	private String pictureSm;
	private String pictureLrg;
	private String restaurantID;
	private String restaurantName;
	private String restaurantPhone;
	private String restaurantStreet1;
	private String restaurantStreet2;
	private String restaurantCity;
	private String restaurantState;
	private String restaurantZip;
	private String restaurantURL;
	public List<DishPrice> prices;
	public List<DishCalories> calories;

	public Dish(String dishId, String  dishName, String description, double rating,
	String pictureSm, String pictureLrg, String restaurantID, String restaurantName,
	String restaurantPhone, String restaurantStreet1, String restaurantStreet2, 
	String restaurantCity, String restaurantState, String restaurantZip, String restaurantURL) {
	
		this.dishId = dishId;
		this.dishName = dishName;
		this.description = description;
		this.pictureSm = pictureSm;
		this.pictureLrg = pictureLrg;
		this.rating = (float) rating;
		this.restaurantName = restaurantName;
		this.restaurantPhone = restaurantPhone;
		this.restaurantID = restaurantID;
		this.restaurantStreet1 = restaurantStreet1;
		this.restaurantStreet2 = restaurantStreet2;
		this.restaurantCity = restaurantCity;
		this.restaurantState = restaurantState;
		this.restaurantZip = restaurantZip;
		this.restaurantURL = restaurantURL;
		this.prices = new ArrayList<DishPrice>();
		this.calories = new ArrayList<DishCalories>();
	}
	
	//for parcelable
	public Dish(Parcel in ) {
        readFromParcel( in );
    }

	public List<DishPrice> getPrices() {
		return prices;
	}

	public void setPrices(String price, String portion) {
		this.prices.add(new DishPrice(this.dishId, price, portion));
	}
	public int getSizeOfPriceArrayList(){
		return prices.size();
	}
	public List<DishCalories> getCalories() {
		return calories;
	}

	public void setCalories(String calorie, String portion) {
		this.calories.add(new DishCalories(this.dishId, calorie, portion));
	}
	public int getSizeOfCaloriesArrayList(){
		return calories.size();
	}

	/**
	 * @return the dishName
	 */
	public String getDishName() {
		return dishName;
	}
	/**
	 * @param dishName the dishName to set
	 */
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param descripiton the dish description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the small picture url
	 */
	public String getPictureSm() {
		return pictureSm;
	}
	/**
	 * @param pictureSm the small picture url to set
	 */
	public void setPictureSm(String pictureSm) {
		this.pictureSm = pictureSm;
	}
	/**
	 * @return the largePicture url
	 */
	public String getPictureLrg() {
		return pictureLrg;
	}
	/**
	 * @param pictureLrg the large picture url to set
	 */
	public void setPictureLrg(String pictureLrg) {
		this.pictureLrg = pictureLrg;
	}
	
	/**
	 * @return the restaurantID related to this dish
	 */
	public String getRestaurantID() {
		return restaurantID;
	}
	/**
	 * @param restaurantID the restaurant ID to set
	 */
	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}
	/**
	 * @return the restaurant name
	 */
	public String getRestaurantName() {
		return restaurantName;
	}
	/**
	 * @param restaurantName the restaurant name to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	/**
	 * @return the restaurant phone
	 */
	public String getRestaurantPhone() {
		return restaurantPhone;
	}
	/**
	 * @param restaurantPhone the restaurant phone to set
	 */
	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}
	/**
	 * @return the Restaurant Street 1
	 */
	public String getRestaurantStreet1() {
		return restaurantStreet1;
	}
	/**
	 * @param restaurantStreet1 the restaurantStreet1 to set
	 */
	public void setRestaurantStreet1(String restaurantStreet1) {
		this.restaurantStreet1 = restaurantStreet1;
	}
	/**
	 * @return the Restaurant Street 2
	 */
	public String getRestaurantStreet2() {
		return restaurantStreet2;
	}
	/**
	 * @param restaurantStreet2 the restaurantStreet2 to set
	 */
	public void setRestaurantStreet2(String restaurantStreet2) {
		this.restaurantStreet2 = restaurantStreet2;
	}
	/**
	 * @return the restaurant City
	 */
	public String getRestaurantCity() {
		return restaurantCity;
	}
	/**
	 * @param restaurantCity the restaurantCity to set
	 */
	public void setRestaurantCity(String restaurantCity) {
		this.restaurantCity = restaurantCity;
	}
	/**
	 * @return the Restaurant State
	 */
	public String getRestaurantState() {
		return restaurantState;
	}
	/**
	 * @param restaurantState the restaurantState to set
	 */	
	public void setRestaurantState(String restaurantState) {
		this.restaurantState = restaurantState;
	}
	
	/**
	 * @return the Restaurant Zip
	 */
	public String getRestaurantZip() {
		return restaurantZip;
	}
	/**
	 * @param restaurantZip the restaurantZip to set
	 */	
	public void setRestaurantZip(String restaurantZip) {
		this.restaurantZip = restaurantZip;
	}
	/**
	 * @return the Restaurant URL
	 */
	public String getRestaurantURL() {
		return restaurantURL;
	}
	/**
	 * @param restaurantURL the restaurantURL to set
	 */	
	public void setRestaurantURL(String restaurantURL) {
		this.restaurantURL = restaurantURL;
	}

	/**
	 * @return the description
	 */
	public String getDiscription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDiscription(String discription) {
		this.description = discription;
	}
	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}
	/**
	 * @return the dishId
	 */	
	public String getDishId() {
		return dishId;
	}
	/**
	 * @param dishId the dishId to set
	 */
	public void setDishId(String dishId) {
		this.dishId = dishId;
	}

	public void setCaloriesArray(String price2) {
		// TODO Auto-generated method stub
		
	}
	
	private void readFromParcel(Parcel in) {
		dishName = in.readString();
		description = in.readString();
		rating = in.readFloat();
		dishId = in.readString();
		pictureSm = in.readString();
		pictureLrg = in.readString();
		restaurantID = in.readString();
		restaurantName = in.readString();
		restaurantPhone = in.readString();
		restaurantStreet1 = in.readString();
		restaurantStreet2 = in.readString();
		restaurantCity = in.readString();
		restaurantState = in.readString();
		restaurantZip = in.readString();
		restaurantURL = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(dishName);
        dest.writeString(description);
        dest.writeFloat(rating);
        dest.writeString(dishId);
        dest.writeString(pictureSm);
        dest.writeString(pictureLrg);
        dest.writeString(restaurantID);
        dest.writeString(restaurantName);
        dest.writeString(restaurantPhone);
        dest.writeString(restaurantStreet1);
        dest.writeString(restaurantStreet2);
        dest.writeString(restaurantCity);
        dest.writeString(restaurantState);
        dest.writeString(restaurantZip);
        dest.writeString(restaurantURL);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Dish createFromParcel(Parcel in ) {
            return new Dish( in );
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };
}
