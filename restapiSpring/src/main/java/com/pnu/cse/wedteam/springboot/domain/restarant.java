package com.pnu.cse.wedteam.springboot.domain;

public class restarant {
    private String name;
    private String address;
    private String menus;
    private String latitude;
    private String longitude;

    public restarant(String name, String address, String menus, String latitude, String longitude){
        this.name = name;
        this.address = address;
        this.menus = menus;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getname(){
        return this.name;
    }
    public String getAddress(){
        return this.address;
    }
    public String getMenus(){
        return this.menus;
    }
    public String getLatitude() { return this.latitude;}
    public String getLongitude() {return this.longitude;}
}
