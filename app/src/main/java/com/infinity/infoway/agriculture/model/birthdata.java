package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;



public class birthdata {

    @SerializedName("emp_id")
    public String emp_id;

    public  String getemp_id()
    {
        return emp_id;
    }

    @SerializedName("emp_name")
    public String emp_name;

    public String getemp_name()
    {
        return emp_name;
    }

    @SerializedName("emp_mobile_no")
    public  String emp_mobile_no;

    public String getemp_mobile_no()
    {
        return  emp_mobile_no;
    }




}
