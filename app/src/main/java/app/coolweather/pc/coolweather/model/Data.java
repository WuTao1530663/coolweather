package app.coolweather.pc.coolweather.model;

/**
 * Created by pc on 2017/2/15.
 */


public class Data {
    private String cityName;
    private String temp1,temp2,weather,date;
    public String getCityName() {
        return cityName;
    }

    public String getDate() {
        return date;
    }

    public String getTemp1() {
        return temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

}
