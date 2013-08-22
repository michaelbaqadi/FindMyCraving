package com.example.cse190_listapp;

public class DishCalories {
	String dishID;
	String calories;
	String portionSize;
	
	DishCalories(String dishID, String cal, String portion){
		this.dishID = dishID;
		this.calories = cal;
		this.portionSize = portion;
	}

	public String getDishID() {
		return dishID;
	}

	public void setDishID(String dishID) {
		this.dishID = dishID;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getPortionSize() {
		return portionSize;
	}

	public void setPortionSize(String portionSize) {
		this.portionSize = portionSize;
	}
}
