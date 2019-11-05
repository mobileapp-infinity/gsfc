package com.infinity.infoway.gsfc.model;


import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class EmployeeAttendanceResponse
{

    @SerializedName("Att_date")
    private String Att_date;
    @SerializedName("day")
    private String day;
    @SerializedName("status")
    private String status;
    @SerializedName("Total_Minute")
    private String Total_Minute;
    @SerializedName("Total_Hour")
    private String Total_Hour;
    @SerializedName("intime")
    private String intime;
    @SerializedName("outtime")
    private String outtime;
    @SerializedName("late_by")
    private String late_by;
    @SerializedName("early_by")
    private String early_by;
    @SerializedName("inout_array")
    private ArrayList<AttendanceInoutTime> inout_array;

    public String getAtt_date()
    {
        return Att_date;
    }


    public void setAtt_date(String Att_date)
    {
        this.Att_date = Att_date;
    }

    public String getday()
    {
        return day;
    }

    public void setday(String day)
    {
        this.day = day;
    }

    public String getstatus()
    {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getTotal_Minute() {
        return Total_Minute;
    }

    public void setTotal_Minute(String Total_Minute) {
        this.Total_Minute = Total_Minute;
    }

    public String getTotal_Hour() {
        return Total_Hour;
    }

    public void setTotal_Hour(String Total_Hour) {
        this.Total_Hour = Total_Hour;
    }

    public String getintime() {
        return intime;
    }

    public void setintime(String intime)
    {
        this.intime = intime;
    }

    public String getouttime() {
        return outtime;
    }

    public void setouttime(String outtime) {
        this.outtime = outtime;
    }

    public String getlate_by() {
        return late_by;
    }

    public void setlate_by(String late_by) {
        this.late_by = late_by;
    }

    public String getearly_by() {
        return early_by;
    }

    public void setearly_by(String early_by) {
        this.early_by = early_by;
    }

    public ArrayList<AttendanceInoutTime> getinout_array() {
        return inout_array;
    }

    public void setinout_array(ArrayList<AttendanceInoutTime> inout_array) {
        this.inout_array = inout_array;
    }
}
