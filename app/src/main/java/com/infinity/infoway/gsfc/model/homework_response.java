package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class homework_response
{
    @SerializedName("day_id")
    private String day_id;

    public   String getDay_id()
    {
        return day_id;
    }

    @SerializedName("day_name")
    private String day_name;

    public  String getDay_name()
    {
        return day_name;
    }


    @SerializedName("date")
    private String date;

    public  String getDate()
    {
        return date;
    }

    @SerializedName("homework_array")
    private ArrayList<homework_array> homework_array;

    public ArrayList<homework_array> getHomework_array()
    {
        return homework_array;
    }

    public void setHomework_array(ArrayList<homework_array> homework_array)
    {
        this.homework_array = homework_array;
    }

}
