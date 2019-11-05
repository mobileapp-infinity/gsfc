package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class activity
{
    @SerializedName("activity_date")
    private String activity_date;

    public  String getActivity_date()
    {
        return activity_date;
    }

    @SerializedName("Activity_File")
    private ArrayList<Activity_File_array> Activity_File;

    public ArrayList<Activity_File_array> getActivity_File()
    {
        return Activity_File;
    }

    public void setActivity_File(ArrayList<Activity_File_array> Activity_File)
    {
        this.Activity_File = Activity_File;
    }

    @SerializedName("da_description")
    private  String da_description;

    public String getDa_description()
    {
        return da_description;
    }


    @SerializedName("activity_photo1")
    private  String activity_photo1;

    public String getActivity_photo1()
    {
        return  activity_photo1;
    }


    @SerializedName("activity_photo2")
    private String activity_photo2;

    public String getActivity_photo2()
    {
        return activity_photo2;
    }

}
