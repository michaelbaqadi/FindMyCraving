package com.example.cse190_listapp;
import java.util.List;



public class Restaurant {
	private Double distance; 
	private String r_name;
	private List<Dish> dish; 
	public Restaurant(Double distance, String r_name, List<Dish> dish) {
		super();
		this.distance = distance;
		this.r_name = r_name;
		this.dish = dish;
	}
	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	/**
	 * @return the r_name
	 */
	public String getR_name() {
		return r_name;
	}
	/**
	 * @param r_name the r_name to set
	 */
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	/**
	 * @return the dish
	 */
	public List<Dish> getDish() {
		return dish;
	}
	/**
	 * @param dish the dish to set
	 */
	public void setDish(List<Dish> dish) {
		this.dish = dish;
	}
}
