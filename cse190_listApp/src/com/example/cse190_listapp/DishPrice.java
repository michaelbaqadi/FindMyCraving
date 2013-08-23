package com.example.cse190_listapp;

import android.os.Parcel;
import android.os.Parcelable;

public class DishPrice implements Parcelable{
	String dishID;
	String dishPrice;
	String dishPortion;
	
	DishPrice(String id, String price, String portion){
		this.dishID = id;
		this.dishPrice = price;
		this.dishPortion = portion;
		
	}
	
	//for parcelable
	public DishPrice(Parcel in ) {
        readFromParcel( in );
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
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(dishID);
        dest.writeString(dishPrice);
        dest.writeString(dishPortion);
	}
	
	private void readFromParcel(Parcel in) {
		// TODO Auto-generated method stub
		dishID = in.readString();
		dishPrice = in.readString();
		dishPortion = in.readString();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DishPrice createFromParcel(Parcel in ) {
            return new DishPrice( in );
        }

        public DishPrice[] newArray(int size) {
            return new DishPrice[size];
        }
    };
}
