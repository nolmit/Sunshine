package com.example.ktarasevich.sunshine.app;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by kit on 1/21/15.
 */
public class ForecastFragment extends Fragment {
   // String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=kiev&cnt=7&mode=json&units=metric";
    String protocol="http";
    String address="api.openweathermap.org/data/2.5/forecast/daily";
    String city = "Kiev";
    String mode ="json";
    String days = "7";
    String units = "metric";


    public ForecastFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


  @Override
  public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater)
  {
      menuInflater.inflate(R.menu.forecast_fragment_menu,menu);
  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
   {
       int id = item.getItemId();
       if (id==R.id.action_refresh)
       {
           FetchWeatherTask();
           return  true;

       }

       return super.onOptionsItemSelected(item);
    }




    public void FetchWeatherTask() {
        Uri.Builder myUri = new Uri.Builder();
        myUri.scheme(protocol).encodedAuthority(address).appendQueryParameter("q",city).appendQueryParameter("mode",mode)
                .appendQueryParameter("days",days).appendQueryParameter("units",units);
        String url =myUri.build().toString();
        BackThread inflateWeather = new BackThread();
        inflateWeather.execute(url);
        Log.i("String", url);
    }


    String[] forecastDummyList = {

            "Today - Sunny - 20/15",
            "Yesterday - Cloudy - 15/10",
            "Tomorrow - Mist - 10/10"

    };

    List<String> listForecast = new ArrayList<String>(Arrays.asList(forecastDummyList));

    private ArrayAdapter<String> myAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        myAdapter=  new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,R.id.list_item_forecast_textview,listForecast);

        ListView myListView = (ListView) rootView.findViewById(R.id.listView_forecast);
        myListView.setAdapter(myAdapter);



        return rootView;


    }
}
