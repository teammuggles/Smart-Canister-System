package com.himanshu.smartcanister.models;

/**
 * Created by Himanshu Gupta on 8/12/2017.
 */

public class CanisterCart {
    private String contentname,imageurl;

    public CanisterCart(String contentname,String imageurl)
    {
        this.contentname=contentname;
        this.imageurl=imageurl;
    }

    public String getContentName()
    {
        return contentname;
    }


    public String getImageurl()
    {
        return imageurl;
    }
}
