package com.example.myapplication;


import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> groups = new ArrayList<String>();
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<Map<String, String>> groupData;
    private ArrayList<Map<String, String>> childDataItem;
    private ArrayList<ArrayList<Map<String, String>>> childData;
    private Map<String, String> m;
    private ExpandableListView elvMain;

    ListView selectCity;
    public static String city = "Добрянка";
    public static String amountDay = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = new TextView(this);
        textView = (TextView)findViewById(R.id.cityID);

        textView.setText(city);


        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city, amountDay});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, Settings.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, JSONWeatherParser> {

        @Override
        protected JSONWeatherParser doInBackground(String... params) {
            JSONWeatherParser parser = new JSONWeatherParser();
            String data = (new WeatherHttpClient()).getWeatherData(params[0], params[1]);//получаем json строку
            try {
                parser.getWeather(data);//парсим ее на состовляющие
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return parser;
        }

        @Override
        protected void onPostExecute(JSONWeatherParser parser) {
            super.onPostExecute(parser);
            groupData = new ArrayList<Map<String, String>>();

            for(int i = 0; i < parser.dataWeather.length; i++ ) {

                data.add(parser.dataWeather[i].currentCondition.getDescr() + ",\n\n"
                        + "температура : " + Math.round((parser.dataWeather[i].temperature.getTemp())) + "°C,\n\n"
                        + "влажность : " + parser.dataWeather[i].currentCondition.getHumidity() + "%,\n\n"
                        + "давление : " + parser.dataWeather[i].currentCondition.getPressure() * 0.75 + " мм.рт.ст.,\n\n"
                        + "ветер : " + parser.dataWeather[i].wind.getSpeed() + " м/c,\n\n");

                groups.add(parser.dataWeather[i].currentCondition.getCondition()
                        + ",\n " + Math.round((parser.dataWeather[i].temperature.getTemp()))+"°C");
            }
            for (String item : groups ) {
                    m = new HashMap<String, String>();
                    m.put("date", item);
                groupData.add(m);
            }

            childData = new ArrayList<ArrayList<Map<String, String>>>();

            for (String item : data) {
                childDataItem = new ArrayList<Map<String, String>>();
                m = new HashMap<String, String>();
                m.put("attribute", item); //
                childDataItem.add(m);
                childData.add(childDataItem);
            }

            String groupFrom[] = new String[] {"date"};
            int groupTo[] = new int[] {android.R.id.text1};
            String childFrom[] = new String[] {"attribute"};
            int childTo[] = new int[] {android.R.id.text1};

            SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                    MainActivity.this,
                    groupData,
                    android.R.layout.simple_expandable_list_item_1,
                    groupFrom,
                    groupTo,
                    childData,
                    android.R.layout.simple_list_item_1,
                    childFrom,
                    childTo);
            elvMain = (ExpandableListView) findViewById(R.id.elvMain);
            elvMain.setAdapter(adapter);
        }
    }
}
