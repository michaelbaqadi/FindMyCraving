package com.example.cse190_listapp;

public class DishPrice {
	String dishID;
	String dishPrice;
	String dishPortion;
	
	DishPrice(String id, String price, String portion){
		this.dishID = id;
		this.dishPrice = price;
		this.dishPortion = portion;
		
	}
	public String getDishPortion() {
		return dishPortion;
	}
	public void setDishPortion(String dishPortion) {
		this.dishPortion = dishPortion;
	}
	public String getDishID() {
		return dishID;
	}
	public void setDishID(String dishID) {
		this.dishID = dishID;
	}
	public String getDishPrice() {
		return dishPrice;
	}
	public void setDishPrice(String dishPrice) {
		this.dishPrice = dishPrice;
	}
}
