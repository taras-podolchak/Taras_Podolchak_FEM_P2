package com.example.taras_podolchak_fem_p2.meteorologia.pojo;

public class City {

    int id;
    String cityName;



    public City(int i, String cityName) {
        super();
        this.id = i;
        this.cityName = cityName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
