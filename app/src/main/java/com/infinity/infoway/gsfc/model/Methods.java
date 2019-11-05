package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 7/13/2017.
 */

public class Methods {
    @SerializedName("method_name")
    public String method_name;

    public String getmethod_name() {
        return method_name;
    }

    @SerializedName("att_status")
    public String att_status;

    public String getatt_status() {
        return att_status;
    }

    @SerializedName("lec_status")
    public String lec_status;

    public String getlec_status() {
        return lec_status;
    }
}
