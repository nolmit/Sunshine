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
    String days = "15";
    String units = "metric";


  public ForecastFragment() {


    }



    List<String> listForecast;
    @Override
    public void onCreate(Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            FetchWeatherTask();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
           try {
               FetchWeatherTask();
           } catch (ExecutionException e) {
               e.printStackTrace();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return  true;

       }

       return super.onOptionsItemSelected(item);
    }




    public void FetchWeatherTask() throws ExecutionException, InterruptedException {
        Uri.Builder myUri = new Uri.Builder();

        myUri.scheme(protocol).encodedAuthority(address).appendQueryParameter("q",city).appendQueryParameter("mode",mode)
                .appendQueryParameter("cnt",days).appendQueryParameter("units",units);

        String url = myUri.build().toString();

        BackThread inflateWeather = new BackThread();

        listForecast = new ArrayList<String>(Arrays.asList(inflateWeather.execute(url).get()));

    }






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
