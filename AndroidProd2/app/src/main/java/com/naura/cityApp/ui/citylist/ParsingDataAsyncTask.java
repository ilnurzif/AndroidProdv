package com.naura.cityApp.ui.citylist;

import android.os.AsyncTask;
import android.util.Log;

import com.naura.cityApp.ui.theatherdata.TheatherData;
import com.naura.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParsingDataAsyncTask extends AsyncTask<ICallData, Void, List<TheatherData>> {
    private ICallData loadedData;
    JSONObject jsonObject;
    private String LOG_TAG = "Debug";
    List<TheatherData> cityTheatherList;
    String cityName;

    @Override
    protected void onPostExecute(List<TheatherData> theatherData) {
        super.onPostExecute(theatherData);
        loadedData.execute(cityTheatherList, cityName);
    }

    @Override
    protected List<TheatherData> doInBackground(ICallData... callData) {
        loadedData = callData[0];
        try {
            jsonObject = new JSONObject(loadedData.getLoadedData());
            convertData(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private void convertData(JSONObject jsonObject) {
        try {
            cityName = jsonObject.getJSONObject("city").getString("name");
            cityTheatherList = new ArrayList<>();
            cityTheatherList.add(jsonToTheatherData(jsonObject, 0));
            cityTheatherList.add(jsonToTheatherData(jsonObject, 6));
            cityTheatherList.add(jsonToTheatherData(jsonObject, 11));
        } catch (Exception exc) {
            exc.printStackTrace();
            Log.e(LOG_TAG, "One or more fields not found in the JSON data");
        }
    }

    private TheatherData jsonToTheatherData(JSONObject jsonObject, int index) {
        try {
            JSONObject list = jsonObject.getJSONArray("list").getJSONObject(index);
            JSONObject main = list.getJSONObject("main");
            String temper = String.format(Locale.getDefault(), "%.0f", main.getDouble("temp")) + "\u2103";
            String humidity = String.format(Locale.getDefault(), "%.0f", main.getDouble("humidity"));
            String pressure = String.format(Locale.getDefault(), "%.0f", main.getDouble("pressure")) + "hPa";

            JSONObject details = list.getJSONArray("weather").getJSONObject(0);
            String descriptionStr = details.getString("description");

            String dateS = list.getString("dt_txt");
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date tempDate = dateFormat.parse(dateS);
            SimpleDateFormat resDate = new SimpleDateFormat("E  dd.MM");

            return new TheatherData(temper, pressure, humidity, resDate.format(tempDate), descriptionStr, R.drawable.kweather);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
