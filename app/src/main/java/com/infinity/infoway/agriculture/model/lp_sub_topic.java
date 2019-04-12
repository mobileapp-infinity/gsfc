package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 5/14/2018.
 */

public class lp_sub_topic
{

    @SerializedName("sub_sr_no")
    public String sub_sr_no;
    public String getSub_sr_no()
    {
        return  sub_sr_no;
    }

    @SerializedName("sub_topic_Name")
    public String sub_topic_Name;

    public String getSub_topic_Name()
    {
        return sub_topic_Name;
    }

    @SerializedName("sub_topic_method")
    public String sub_topic_method;

    public String getSub_topic_method()
    {
        return sub_topic_method;
    }

    @SerializedName("sub_topic_aid")
    public String sub_topic_aid;

    public String getSub_topic_aid()
    {
        return sub_topic_aid;
    }

    @SerializedName("sub_topic_co")
    public String sub_topic_co;

    public String getSub_topic_co()
    {
        return sub_topic_co;
    }


    @SerializedName("sub_topic_op")
    public String sub_topic_op;

    public  String getSub_topic_op()
    {
        return  sub_topic_op;
    }
}

