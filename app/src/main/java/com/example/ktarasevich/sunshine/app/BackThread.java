package com.example.ktarasevich.sunshine.app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by kit on 1/21/15.
 */
 class BackThread extends AsyncTask<String,Void,String[]>{

    @Override
    protected String[] doInBackground(String... params) throws NullPointerException {
        String url=params[0];

        //get raw json string as answer
        OpenWeatherData weatherData = new OpenWeatherData(url);
        String rawAnswer = null;
        try {
            rawAnswer = weatherData.GetRawWeather();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] answer;

        //parse json to get array of weather
        try{
            WeatherDataParser weatherArray = new WeatherDataParser(rawAnswer);
            answer= weatherArray.getWeatherDataFromJson();
            return  answer;

        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        catch (JSONException e)
        {e.printStackTrace();}




        return null;
    }


    @Override
    protected void onPostExecute(String[] answer) {
        super.onPostExecute(answer);

    }
}

