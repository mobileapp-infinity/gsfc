package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;


public class ChartDataRemainingAttendance
{

    @SerializedName("department")
    public String department;

    @SerializedName("dm_id")
    public  String dm_id;

    @SerializedName("ac_id")
    public  String ac_id;

    @SerializedName("total")
    public  String total;

    public  String getDm_id()
    {
        return  dm_id;
    }

    public  String getAc_id()
    {
        return  ac_id;
    }

    public  String getTotal()
    {
        return  total;
    }

    public  String getDepartment()
    {
        return department;
    }

    @SerializedName("Completed")
    public String Completed;

    public  String getCompleted()
    {
        return Completed;
    }
    @SerializedName("remaining")
    public String Remaining;

    public  String getRemaining()
    {
        return Remaining;
    }


}
