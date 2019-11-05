package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user01 on 1/3/2018.
 */
//  Atmiya employee attendance api response
public class PunchData {

    @SerializedName("Contact_ID")
    private String Contact_ID;
    @SerializedName("Att_date")
    private String Att_date;
    @SerializedName("Day")
    private String day;
    @SerializedName("Status")
    private String status;
    @SerializedName("Total_Minute")
    private String Total_Minute;
    @SerializedName("Total_hour")
    private String Total_Hour;
    @SerializedName("InTime")
    private String intime;
    @SerializedName("OutTime")
    private String outtime;
    @SerializedName("Late_By")
    private String late_by;
    @SerializedName("Early_By")
    private String early_by;
    @SerializedName("Late_By_Hour")
    private String Late_By_Hour;
    @SerializedName("Early_By_Hour")
    private String Early_By_Hour;
    @SerializedName("Extra_Hours")
    private String Extra_Hours;

    @SerializedName("Remark")
    private String Remark;
    @SerializedName("Attendance_Shift_Name")
    private String Attendance_Shift_Name;

    @SerializedName("Inout_Array")
    private ArrayList<AttendanceInoutTime> Inout_Array;

    public String getExtra_Hours() {
        return Extra_Hours;
    }

    public void setExtra_Hours(String Extra_Hours) {
        this.Extra_Hours = Extra_Hours;
    }

    public String getEarly_By_Hour() {
        return Early_By_Hour;
    }

    public void setEarly_By_Hour(String Early_By_Hour) {
        this.Early_By_Hour = Early_By_Hour;
    }

    public String getLate_By_Hour() {
        return Late_By_Hour;
    }

    public void setLate_By_Hour(String Late_By_Hour) {
        this.Late_By_Hour = Late_By_Hour;
    }

    public String getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(String Contact_ID) {
        this.Contact_ID = Contact_ID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getAttendance_Shift_Name() {
        return Attendance_Shift_Name;
    }

    public void setAttendance_Shift_Name(String Attendance_Shift_Name) {
        this.Attendance_Shift_Name = Attendance_Shift_Name;
    }

    public String getAtt_date()
    {
        return Att_date;
    }

    public void setAtt_date(String Att_date) {
        this.Att_date = Att_date;
    }

    public String getday() {
        return day;
    }

    public void setday(String day) {
        this.day = day;
    }

    public String getstatus() {
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

    public void setintime(String intime) {
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

    public ArrayList<AttendanceInoutTime> getInout_Array() {
        return Inout_Array;
    }

    public void setInout_Array(ArrayList<AttendanceInoutTime> Inout_Array) {
        this.Inout_Array = Inout_Array;


    }
}