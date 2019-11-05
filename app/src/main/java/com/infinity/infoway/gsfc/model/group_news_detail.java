package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 5/18/2018.
 */

public class group_news_detail
{

    @SerializedName("cn_id")
    private  String cn_id;

    public String getCn_id()
    {
        return cn_id;
    }
    @SerializedName("cn_subject")
    private  String cn_subject;
    public String getCn_subject(){return  cn_subject;}

    @SerializedName("cn_content")
    private String cn_content;

    public String getCn_content()
    {
        return  cn_content;
    }

    @SerializedName("cn_date")
    public  String cn_date;

    public  String getCn_date(){return  cn_date;}


    @SerializedName("ph_1")
    private  String ph_1;

    public String getPh_1(){return  ph_1;}

    @SerializedName("ph_2")
    private String ph_2;


    public  String getPh_2(){return ph_2;}
}
