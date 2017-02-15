package app.coolweather.pc.coolweather.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.coolweather.pc.coolweather.R;
import app.coolweather.pc.coolweather.model.Weather;
import app.coolweather.pc.coolweather.util.HttpUtil;
import app.coolweather.pc.coolweather.util.Utility;

/**
 * Created by pc on 2017/2/15.
 */

public class WeatherActivity extends Activity implements View.OnClickListener {
    private LinearLayout weatherInfoLayout;

    private TextView cityNameText;
    private TextView weatherText;
    private TextView dateText;
    private TextView temp1Text;
    private TextView temp2Text;

    private Button switchCity;
    private Button refreshWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        weatherText = (TextView)findViewById(R.id.weather_desp);
        dateText = (TextView)findViewById(R.id.current_date);
        temp1Text = (TextView)findViewById(R.id.temp1);
        temp2Text = (TextView)findViewById(R.id.temp2);
        switchCity = (Button)findViewById(R.id.switch_city);
        refreshWeather = (Button)findViewById(R.id.refresh);
        String countyCode = getIntent().getStringExtra("county_code");
        if(!TextUtils.isEmpty(countyCode)){
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }
        else {
            showWeather();
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(WeatherActivity.this,ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh:
                Toast.makeText(this,"同步中",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = preferences.getString("weather_code","");
                if(!TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }
                break;
            default:break;
        }
    }

    public void queryWeatherCode(String countyCode){
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address,"countyCode");
    }
    public void queryWeatherInfo(String weatherCode){
        String address = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + weatherCode;
        queryFromServer(address,"weatherCode");
    }
    private void queryFromServer(final String address,final String type){
        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {

                if("weatherCode".equals(type)){
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
                else if("countyCode".equals(type)){
                    if(!TextUtils.isEmpty(response)){
                        String[] array = response.split("\\|");
                        if(array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }

                    }
                }

            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"同步失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void showWeather(){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        String cityName = preferences.getString("city_name","");
        cityNameText.setText(preferences.getString("city_name",""));
        temp1Text.setText(preferences.getString("temp1","").substring(2));
        temp2Text.setText(preferences.getString("temp2","").substring(2));
        weatherText.setText(preferences.getString("weather",""));
        dateText.setText(preferences.getString("date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);

    }





}
