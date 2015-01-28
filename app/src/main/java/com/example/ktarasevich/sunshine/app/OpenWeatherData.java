package com.example.ktarasevich.sunshine.app;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by kit on 1/20/15.
 */
public class OpenWeatherData {


    private String _url;

   public OpenWeatherData(String urlQuery){
       this._url=urlQuery;
   }

    public String GetRawWeather() throws  InterruptedException{
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Integer tries=5;
        InputStream inputStream=null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {

            URL url = new URL(_url);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String

             do {
                 InputStream stream = urlConnection.getInputStream();
                 if (stream != null)
                 {   inputStream=stream;
                     break;
                 }
                 Thread.sleep(1000);
                 tries--;
             }
             while (tries>0);
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr=null;
            }
            else {
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                Log.i("WeatherApp", forecastJsonStr);
            }
        } catch (IOException e) {
            Log.e("WeatherAPP", "Error IO ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr=null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("WeatherAPP", "Error closing stream", e);
                }
            }
        }

        return forecastJsonStr;
    }
    }
