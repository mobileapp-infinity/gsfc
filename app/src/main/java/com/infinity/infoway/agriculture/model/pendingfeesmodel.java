package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

public class pendingfeesmodel
{
    @SerializedName("status")
    public String status;

    public  String getStatus()
    {
        return status;
    }

    @SerializedName("response")
    public String response;

    public  String getresponse()
    {
        return response;
    }

    @SerializedName("Filename")
    public String Filename;

    public  String getFilename()
    {
        return Filename;
    }

    @SerializedName("base64string")
    public String bytearr;

    public  String getbytearr()
    {
        return bytearr;
    }
}
