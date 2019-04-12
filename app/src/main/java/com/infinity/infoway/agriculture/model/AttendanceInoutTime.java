package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

public class AttendanceInoutTime
{
    @SerializedName("InTime")
    private String InTime;
    @SerializedName("OutTime")
    private String OutTime;


//    @SerializedName("leave_date")
//    private String leave_date;
//    @SerializedName("lea
// ve")
//    private String leave;

    public String getintime()
    {
        return InTime;
    }


    public void setInTime(String InTime)
    {
        this.InTime=InTime;
    }

    public  void setOutTime(String OutTime)
    {
        this.OutTime=OutTime;
//        this.OutTime=OutTime;
    }
    public void setintime(String InTime)
    {
        this.InTime = InTime;
    }

    public String getouttime()
    {
        return OutTime;
    }

    public void setouttime(String OutTime)
    {
        this.OutTime = OutTime;
    }
//    public String getleave_date()
//    {
//        return leave_date;
//    }
//
//    public void setleave_date(String leave_date)
//    {
//        this.leave_date = leave_date;
//    }
//   public String getleavee()
//    {
//        return leave;
//    }
//
//    public void setleave(String leave)
//    {
//        this.leave = leave;
//    }

}
