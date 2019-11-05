package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

public class MultipartResponsePojo
{
    @SerializedName("status")
    private String status;

    public  String getStatus()
    {
        return status;
    }

    @SerializedName("msg")
    private String msg;

    public  String getMsg()
    {
        return msg;
    }

}
