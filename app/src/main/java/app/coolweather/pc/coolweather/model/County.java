package app.coolweather.pc.coolweather.model;

/**
 * Created by pc on 2017/2/14.
 */

public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;
    public int getId() {
        return id;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
