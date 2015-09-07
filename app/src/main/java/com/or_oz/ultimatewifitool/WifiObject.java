package com.or_oz.ultimatewifitool;

/**
 * Created by oroz7_000 on 9/6/2015.
 */
//object to be used with root feature. Easier to extract data into listview
public class WifiObject {
    String ssid;
    String password;
    String type;

    public void WifiObject(String ssid, String password, String type){
        this.ssid = ssid;
        this.password = password;
        this.type = type;
    }


    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
