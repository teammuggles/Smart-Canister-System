package com.himanshu.smartcanister.models;

/**
 * Created by ritwick on 11/8/17.
 */

public class Canister
{
    private String contentname, percentageleft,imageurl;

    public Canister(String contentname,String percentageleft, String imageurl)
    {
        this.contentname=contentname;
        this.percentageleft=percentageleft;
        this.imageurl=imageurl;
    }

    public String getContentName()
    {
        return contentname;
    }

    public String getPercentageLeft()
    {
        return percentageleft;
    }

    public String getImageurl()
    {
        return imageurl;
    }
}
