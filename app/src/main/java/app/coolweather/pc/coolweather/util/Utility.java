package app.coolweather.pc.coolweather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.coolweather.pc.coolweather.activity.WeatherActivity;
import app.coolweather.pc.coolweather.db.CoolWeatherDB;
import app.coolweather.pc.coolweather.model.City;
import app.coolweather.pc.coolweather.model.County;
import app.coolweather.pc.coolweather.model.Province;
import app.coolweather.pc.coolweather.model.Weather;

/**
 * Created by pc on 2017/2/14.
 */

public class Utility {
    public static void handleWeatherResponse(Context context, String response){

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            String cityName = data.getString("city");
            JSONArray jsonArray = data.getJSONArray("forecast");
            JSONObject today = jsonArray.getJSONObject(0);
            String temp1 = today.getString("low"),temp2 = today.getString("high"),weather = today.getString("type"),date = today.getString("date");

            saveWeatherInfo(context,cityName,temp1,temp2,weather,date);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static void saveWeatherInfo(Context context,String cityName,String temp1,String temp2,String weather,String date){
        SharedPreferences.Editor editor = context.getSharedPreferences("data",Context.MODE_PRIVATE).edit();

        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather",weather);
        editor.putString("date",date);
        editor.commit();
    }
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces!= null && allProvinces.length > 0){
                for(String p: allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities!= null && allCities.length > 0){
                for(String c: allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties!= null && allCounties.length > 0){
                for(String c: allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    }

