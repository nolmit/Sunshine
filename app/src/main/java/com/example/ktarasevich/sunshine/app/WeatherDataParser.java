package com.example.ktarasevich.sunshine.app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kit on 1/26/15.
 */
public class WeatherDataParser {

    final private String LOG_TAG =WeatherDataParser.class.getSimpleName();

    String rawJsonString;

    public WeatherDataParser(String rawJson)
    {
        this.rawJsonString=rawJson;
    }

    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException
    {
        JSONObject json = new JSONObject(weatherJsonStr);
        int days = json.getInt("cnt");
        JSONArray list = json.getJSONArray("list");
        if (dayIndex<=days)
        {
           JSONObject dayList = list.getJSONObject(dayIndex);
            JSONObject temp = dayList.getJSONObject("temp");
            Double maxTemp =temp.getDouble("max");
            return  maxTemp;
        }


        return  0;
    }


    /* The date/time conversion code is going to be moved outside the asynctask later,
     * so for convenience we're breaking it out into its own method now.
     */
    private String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    public String[] getWeatherDataFromJson()
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DATETIME = "dt";
        final String OWM_DESCRIPTION = "main";
        final  String DAYS ="cnt";
try {


    JSONObject forecastJson = new JSONObject(rawJsonString);
    int days = forecastJson.getInt(DAYS);
    JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
    String[] resultStrs = new String[days];

    for (int i = 0; i < weatherArray.length(); i++) {
        // For now, using the format "Day, description, hi/low"
        String day;
        String description;
        String highAndLow;

        // Get the JSON object representing the day
        JSONObject dayForecast = weatherArray.getJSONObject(i);

        // The date/time is retured as a long.  We need to convert that
        // into something human-readnable, since most people won't read "1400356800" as
        // "this saturday".
        long dateTime = dayForecast.getLong(OWM_DATETIME);
        day = getReadableDateString(dateTime);

        // description is in a child array called "weather", which is 1 element long.
        JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
        description = weatherObject.getString(OWM_DESCRIPTION);

        // Temperatures are in a child object called "temp".  Try not to name variables
        // "temp" when working with temperature.  It confuses everybody.
        JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
        double high = temperatureObject.getDouble(OWM_MAX);
        double low = temperatureObject.getDouble(OWM_MIN);

        highAndLow = formatHighLows(high, low);
        resultStrs[i] = day + " - " + description + " - " + highAndLow;
    }

return  resultStrs;

}
catch (JSONException ex)
{
    Log.e(LOG_TAG,"Failed on json parsing");
    ex.printStackTrace();}

        return null;
    }

}
