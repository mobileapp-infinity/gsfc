package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;



public class homework_array
{
    @SerializedName("lect_no")
    private String  lect_no;

    public  String getLect_no()
    {
        return lect_no;
    }


    @SerializedName("subject_name")
    private String subject_name;

    public  String getSubject_name()
    {
        return subject_name;
    }


    @SerializedName("cont_deli_desc")
    private String cont_deli_desc;

    public  String getCont_deli_desc()
    {
        return cont_deli_desc;
    }


    @SerializedName("homework_desc")
    private String homework_desc;

    public  String getHomework_desc()
    {
        return homework_desc;
    }

}

