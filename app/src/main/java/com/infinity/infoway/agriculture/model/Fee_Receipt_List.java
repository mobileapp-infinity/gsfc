package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user02 on 10/6/2017.
 */

public class Fee_Receipt_List {

    @SerializedName("Fee_Receipt_No")
    private String feereceiptno;

    public String getFeereceiptno()
    {
        return feereceiptno;
    }

    @SerializedName("Fee_Receipt_Date")
    private String feerecepitdate;

    public String getFeerecepitdate()
    {
        return feerecepitdate;
    }


    @SerializedName("Fee_Course_Name")
    private String feecoursename;

    public String getFeecoursename()
    {
        return feecoursename;
    }

    @SerializedName("Fee_Class_Name")
    private String feeclassname;

    public String getFeeclassname()
    {
        return feeclassname;
    }

    @SerializedName("Fee_Amt")
    private String feeamount;

    public String getFeeamount()
    {
        return feeamount;
    }



}
