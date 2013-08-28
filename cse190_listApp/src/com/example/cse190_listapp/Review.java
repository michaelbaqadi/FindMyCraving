package com.example.cse190_listapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public  class Review implements Parcelable{
	
	private String userID;
	private String userName; 
	private float rating;
	private String comments;
	private String timeStamp;
	private String userPicture;

	public Review(String userID, String userName, double rating, String comments, String timeStamp, String userPicture) {
	
		this.userID = userID;
		this.userName = userName;
		this.rating = (float) rating;
		this.comments = comments;
		this.timeStamp = timeStamp;
		this.userPicture = userPicture;
	}
	
	//for parcelable
	public Review(Parcel in ) {
        readFromParcel( in );
    }
	
	/**
	 * @return the rating
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the rating
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * @return the rating
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	/**
	 * @return the rating
	 */
	public String getUserPicture() {
		return userPicture;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	
	
	private void readFromParcel(Parcel in) {
		userID = in.readString();
		userName = in.readString();
		rating = in.readFloat();
		comments = in.readString();
		timeStamp = in.readString();
		userPicture = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	//hello
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userID);
		dest.writeString(userName);
        dest.writeFloat(rating);
        dest.writeString(comments);
        dest.writeString(timeStamp);
        dest.writeString(userPicture);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Review createFromParcel(Parcel in ) {
            return new Review( in );
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}