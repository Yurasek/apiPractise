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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//класс для получения json строки

public class WeatherHttpClient {
	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	private static String APP_ID = "&appid=ed1fcbcb3471142d64350aa40ad8e008";
	private static String UNITS = "&units=metric&cnt=";
	private static String LANG = "&lang=ru";

	
	public String getWeatherData(String location, String mnt) {
		HttpURLConnection connection = null ;
		InputStream is = null;

		try {
			connection = (HttpURLConnection) ( new URL(BASE_URL
					+ URLEncoder.encode(location, "UTF-8")
					+ UNITS
					+ mnt
					+ LANG
					+ APP_ID)).openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();

			StringBuffer buffer = new StringBuffer();
			is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line = null;
			while ( (line = br.readLine()) != null )
				buffer.append(line + "\r\n");
			
			is.close();
			connection.disconnect();
			return buffer.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { connection.disconnect(); } catch(Throwable t) {}
		}

		return null;
	}
}
