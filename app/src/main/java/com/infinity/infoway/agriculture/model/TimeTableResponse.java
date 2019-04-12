package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class TimeTableResponse
{
    @SerializedName("day_name")
    private String day_name;

    @SerializedName("inout_array1")
    private ArrayList<Lecturedetail> inout_array1;

    public void setDay_name(String day_name)
    {
        this.day_name = day_name;
    }

    public String getday_name()
    {

        return day_name;
    }

    public ArrayList<Lecturedetail> getLecturedetail()
    {
        return inout_array1;
    }

    public void setLecturedetail(ArrayList<Lecturedetail> inout_array1)
    {
        this.inout_array1 = inout_array1;
    }

}
