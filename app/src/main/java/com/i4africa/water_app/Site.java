package com.i4africa.water_app;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Site {
    private String siteName, picURL;

    public Site(){

    }

    public Site(String siteNameS, String picURLS){
        this.siteName = siteNameS;
        this.picURL = picURLS;
    }

    public String getSiteName(){
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPicURL(){
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }


}
