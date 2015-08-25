package com.or_oz.ultimatewifitool.model;

import com.google.gson.annotations.Expose;

/**
 * Created by oroz7_000 on 8/24/2015.
 */
//used with GSON conversion and retrofit
public class passwdModel {

    @Expose
    private Integer length;
    @Expose
    private String password;


    public Integer getLength() {
        return length;
    }
    public void setLength(Integer length) {
        this.length = length;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
