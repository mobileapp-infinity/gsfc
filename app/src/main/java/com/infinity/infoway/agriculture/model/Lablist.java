package com.infinity.infoway.agriculture.model;


import com.google.gson.annotations.SerializedName;

public class Lablist
{
    @SerializedName("lect_name")
    private String lect_name;
    @SerializedName("lect_st_time")
    private String lect_st_time;
    @SerializedName("lect_end_time")
    private String lect_end_time;
    @SerializedName("sm_name")
    private String sm_name;
    @SerializedName("emp_name")
    private String emp_name;
    @SerializedName("rm_name")
    private String rm_name;
    @SerializedName("sub_short_name")
    private String sub_short_name;
    @SerializedName("dvm_name")
    private String dvm_name;


    public  String getlect_name()
    {
        return lect_name;
    }
    public  void setlect_name(String lect_name)
    {
        this.lect_name=lect_name;
    }

    public  String getlect_st_time()
    {
        return lect_st_time;
    }
    public  void setlect_st_time(String lect_st_time)
    {
        this.lect_st_time=lect_st_time;
    }
    public  String getlect_end_time()
    {
        return lect_end_time;
    }
    public  void setlect_end_time(String lect_end_time)
    {
        this.lect_end_time=lect_end_time;
    }

    public  String getemp_name()
    {
        return emp_name;
    }
    public  void setemp_name(String emp_name)
    {
        this.emp_name=emp_name;
    }
    public  String getrm_name()
    {
        return rm_name;
    }
    public  void setrm_name(String rm_name)
    {
        this.rm_name=rm_name;
    }
    public  String getsub_short_name()
    {
        return sub_short_name;
    }
    public  void setsub_short_name(String sub_short_name)
    {
        this.sub_short_name=sub_short_name;
    }
    public  String getsm_name()
    {
        return sm_name;
    }
    public  void setsm_name(String sm_name)
    {
        this.sm_name=sm_name;
    }
    public  String getdvm_name()
    {
        return dvm_name;
    }
    public  void setdvm_namee(String dvm_name)
    {
        this.dvm_name=dvm_name;
    }
}

