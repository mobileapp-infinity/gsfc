package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

public class assignment_response
{

    @SerializedName("sm_name")
    private String sm_name;

    public void setSm_name(String sm_name)
    {
        this.sm_name = sm_name;
    }

    public  String getSm_name(){
        return  sm_name;
    }

    @SerializedName("dvm_name")
    private String dvm_name;

    public void setDvm_name(String dvm_name)
    {
        this.dvm_name = dvm_name;
    }


    public String getDvm_name()
    {
        return dvm_name;
    }

    @SerializedName("sub_fullname")
    private String sub_fullname;


    public void setSub_fullname(String sub_fullname)
    {
        this.sub_fullname = sub_fullname;
    }


    public String getSub_fullname()
    {
        return sub_fullname;
    }


    @SerializedName("PDF_URL")
    private String PDF_URL;


    public void setPDF_URL(String PDF_URL)
    {
        this.PDF_URL = PDF_URL;
    }


    public String getPDF_URL()
    {
        return PDF_URL;
    }




    @SerializedName("am_last_seen_date")
    private String am_last_seen_date;


    public void setAm_last_seen_date(String am_last_seen_date)
    {
        this.am_last_seen_date = am_last_seen_date;
    }


    public String getAm_last_seen_date()
    {
        return am_last_seen_date;
    }



    @SerializedName("am_name")
    private  String am_name;

    public  String getAm_name ()
    {
        return  am_name;
    }



}
