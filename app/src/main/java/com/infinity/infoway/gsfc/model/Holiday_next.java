package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 1/16/2018.
 */

public class Holiday_next
{

    @SerializedName("Sr. No")
    public String Sr_No;

    public  String getSr_No()
    {
        return Sr_No;
    }

    @SerializedName("Holiday Name")
    public String Holiday_Name;

    public  String getHoliday_Name()
    {
        return Holiday_Name ;
    }
    @SerializedName("ac_full_name")
    public String ac_full_name;

    public  String getAc_full_name()
    {
        return ac_full_name;
    }
    @SerializedName("From Date")
    public String From_Date;

    public  String getFrom_Date()
    {
        return From_Date;
    }

    @SerializedName("To Date")
    public String To_Date;

    public  String getTo_Date()
    {
        return To_Date;
    }
    @SerializedName("Description")
    public String Description;

    public  String getDescription()
    {
        return Description;
    }


}
