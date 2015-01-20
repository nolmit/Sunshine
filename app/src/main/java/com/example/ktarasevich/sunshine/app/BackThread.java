package com.example.ktarasevich.sunshine.app;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by kit on 1/21/15.
 */
 class BackThread extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... params) {
        String url=params[0];
        OpenWeatherData weatherData = new OpenWeatherData(url);
        String rawAnswer = weatherData.GetRawWeather();
        return rawAnswer;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}

