package com.example.cse190_listapp;

public  class Dish {
	public Dish(String dishName, String picture, Double calories, Double price,
			String discription, int rate, int dishId, String res) {
		super();
		this.dishName = dishName;
		this.picture = picture;
		this.calories = calories;
		this.price = price;
		this.discription = discription;
		this.rating = rate;
		this.dishId = dishId;
		this.res = res;
	}
	private String  dishName;
	private String  picture;
	private Double  calories;
	private Double  price;
	private String discription;
	private int rating;
	private int dishId;
	private String res;
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
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * @return the calories
	 */
	public double getCalories() {
		return calories;
	}
	/**
	 * @param calories the calories to set
	 */
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the discription
	 */
	public String getDiscription() {
		return discription;
	}
	/**
	 * @param discription the discription to set
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getDishId() {
		return dishId;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	
	public String getRes() {
		return res;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setDishId(String res) {
		this.res = res;
	}
	
}
