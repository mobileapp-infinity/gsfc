package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

public class change_psw
{

    @SerializedName("status")
    private String status;
    public String getStatus()
    {
        return status;
    }

    @SerializedName("message")
    private String message;
    public String getMessage()
    {
        return message;
    }
}
