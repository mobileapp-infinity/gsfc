package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user02 on 6/19/2017.
 */

public class LessionResponse
{

    @SerializedName("Subject_name")
    private String subname;

    public String getSubname()
    {
        return  subname;
    }

    @SerializedName("ref_book_name")
    private String refname;

    public String getRefname()
    {
        return refname;
    }


    @SerializedName("topic_name")
    private String topicname;

    public String getTopicname()
    {
        return topicname;
    }

    @SerializedName("aid_name")
    private String aidname;

    public String getAidname()
    {
        return aidname;
    }





    @SerializedName("method_array")
    private ArrayList<LessionArray> methodarray;

    public ArrayList<LessionArray> getMethodarray()
    {
        return  methodarray;
    }


}
