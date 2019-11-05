package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user02 on 8/11/2017.
 */

public class RemainingAttResponse
{

    @SerializedName("dl_date")
    private String date;

    public String getDate()
    {
        return date;
    }

    @SerializedName("course")
    private String course;

    public String getCourse()
    {
        return course;
    }

    @SerializedName("sem_name")
    private String semname;

    public String getSemname()
    {
        return semname;
    }


    @SerializedName("division")
    private String division;

    public String getDivision()
    {
        return division;
    }


    @SerializedName("lec_no")
    private String lecno;

    public String getLecno()
    {
        return lecno;
    }
}

