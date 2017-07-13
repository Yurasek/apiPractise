/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package com.example.myapplication;

import com.example.myapplication.model.Location;
import com.example.myapplication.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

	public static Weather[] dataWeather;

	public static Weather[] getWeather(String data) throws JSONException  {
		Location loc = new Location();
		JSONObject jObj = new JSONObject(data);

		JSONObject cityObj = getObject("city", jObj);
		JSONArray listArr = jObj.getJSONArray("list");
		
		JSONObject coordObj = getObject("coord", cityObj);
		loc.setLatitude(getFloat("lat", coordObj));
		loc.setLongitude(getFloat("lon", coordObj));

		loc.setSize(getInt("cnt", jObj));

		loc.setCountry(getString("country", cityObj));
		loc.setCity(getString("name", cityObj));
		dataWeather = new Weather[loc.getSize()];

		for(int i = 0;i < dataWeather.length;i++){
			dataWeather[i] = new Weather();
		}

		for(int i = 0; i < dataWeather.length; i++ ){
			JSONObject listItemArr = listArr.getJSONObject(i);
			JSONArray weatherArr = listItemArr.getJSONArray("weather");
			JSONObject weatherItemArr = weatherArr.getJSONObject(0);
			dataWeather[i].currentCondition.setDescr(getString("description", weatherItemArr));
			dataWeather[i].location = loc;
		}

		for (int i = 0; i < dataWeather.length; i++ ){
			JSONObject listItemArr = listArr.getJSONObject(i);
			JSONObject tempObj = listItemArr.getJSONObject("temp");
			dataWeather[i].wind.setSpeed(getFloat("speed", listItemArr));
			dataWeather[i].currentCondition.setHumidity(getFloat("humidity", listItemArr));
			dataWeather[i].currentCondition.setPressure(getInt("pressure", listItemArr));
			dataWeather[i].temperature.setTemp(getFloat("day", tempObj));
			dataWeather[i].currentCondition.setCondition(getString("dt", listItemArr));
		}
		
		return dataWeather;
	}
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}
	
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}
	
}