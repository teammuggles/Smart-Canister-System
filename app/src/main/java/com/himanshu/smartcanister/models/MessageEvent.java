package com.himanshu.smartcanister.models;

/**
 * Created by ritwick on 11/8/17.
 */

public class MessageEvent
{
    public String message;
    public int visibility;
    public MessageEvent(String message,int visibility)
    {
        this.message=message;
        this.visibility=visibility;
    }
}
