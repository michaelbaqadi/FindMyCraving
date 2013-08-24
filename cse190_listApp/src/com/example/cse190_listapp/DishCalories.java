package com.example.cse190_listapp;

import android.os.Parcel;
import android.os.Parcelable;

public class DishCalories implements Parcelable{
	String dishID;
	String calories;
	String portionSize;
	
	DishCalories(String dishID, String cal, String portion){
		this.dishID = dishID;
		this.calories = cal;
		this.portionSize = portion;
	}
	
	//for parcelable
	public DishCalories(Parcel in ) {
        readFromParcel( in );
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(dishID);
        dest.writeString(calories);
        dest.writeString(portionSize);
	}
	
	private void readFromParcel(Parcel in) {
		// TODO Auto-generated method stub
		dishID = in.readString();
		calories = in.readString();
		portionSize = in.readString();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DishCalories createFromParcel(Parcel in ) {
            return new DishCalories( in );
        }

        public DishCalories[] newArray(int size) {
            return new DishCalories[size];
        }
    };
}
