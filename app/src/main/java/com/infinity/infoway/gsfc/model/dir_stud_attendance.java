package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 2/2/2018.
 */

public class dir_stud_attendance
{
    @SerializedName("Total")
    private String Total;
    @SerializedName("P")
    private String P;
    @SerializedName("AB")
    private String AB;
    @SerializedName("P_per")
    private String P_per;
    @SerializedName("AB_per")
    private String AB_per;

    public String getTotal()
    {
        return Total;
    }

    public String getP()
    {
        return P;
    }
    public  String getAB()
    {
        return AB;
    }
    public String getP_per()
    {
        return P_per;
    }
    public String getAB_per()
    {
        return AB_per;
    }

}
