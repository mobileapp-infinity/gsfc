package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user02 on 8/3/2017.
 */

public class FcmResponse
{

    @SerializedName("Message")
    private String message;

    public String getMessage()
    {
        return message;
    }

}
