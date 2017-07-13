package com.example.myapplication.model;

import java.io.Serializable;

public class Location implements Serializable {

	private float longitude;
	private float latitude;

	private String country;
	private String city;

	private int listSize;
	
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getSize() {
		return listSize;
	}
	public void setSize(int listSize) {
		this.listSize = listSize;
	}
	
}
