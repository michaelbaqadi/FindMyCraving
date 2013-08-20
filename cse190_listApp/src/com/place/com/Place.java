package com.place.com;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
/*
 *  This Class Will have information about the the personal position 
 *  In addition to  info about places near the android user
 * */
public class Place {
    /*public Place(String id, String icon, String name, String vicinity,
			Double latitude, Double longitude) {
		super();
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.vicinity = vicinity;
		this.latitude = latitude;
		this.longitude = longitude;
	}*/
	public Place(String id2, String icon2, String name2, String vicinity2,
			Double latitude2, Double longitude2) {
		super();
		this.id = id2;
		this.icon = icon2;
		this.name = name2;
		this.vicinity = vicinity2;
		this.latitude = latitude2;
		this.longitude = longitude2;
	}
	public Place()
	{
		
	}
	private String id;
    private String icon;
    private String name;
    private String vicinity;
    private Double latitude;
    private Double longitude;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    /*
     *  This will get data of the position on map 
     * */
    public static Place jsonToPontoReferencia(JSONObject pontoReferencia) {
        try {
            Place result = new Place();
            JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(pontoReferencia.getString("icon"));
            result.setName(pontoReferencia.getString("name"));
            result.setVicinity(pontoReferencia.getString("vicinity"));
            result.setId(pontoReferencia.getString("id"));
            return result;
        } catch (JSONException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /*
     * create JSON object 
     * */
    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

}