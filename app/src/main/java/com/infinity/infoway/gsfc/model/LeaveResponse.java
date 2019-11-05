package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeaveResponse
{
    @SerializedName("Leave_type_name")
    private String leave_type_name;

    //Student Detail
    @SerializedName("Leave_days")
    private String el_leave_days;

    @SerializedName("Leave_taken")
    private String taken_leave;
    @SerializedName("Leave_Balance")
    private String balance;
    @SerializedName("leave_array")
    private ArrayList<AttendanceInoutTime> leave_array;

    @SerializedName("Leave_type_id")
    private  String Leave_type_id;

    public String getLeave_type_id()
    {
        return Leave_type_id ;
    }
    public String getleave_type_name()
    {
        return leave_type_name;
    }

    public void setleave_type_name(String leave_type_name)
    {
        this.leave_type_name = leave_type_name;
    }

    public String getel_leave_days()
    {
        return el_leave_days;
    }

    public void setel_leave_days(String el_leave_days) {
        this.el_leave_days = el_leave_days;
    }

    public String getaken_leave()
    {
        return taken_leave;
    }

    public void settaken_leave(String taken_leave)
    {
        this.taken_leave = taken_leave;
    }

    public String getbalance() {
        return balance;
    }

    public void setbalance(String balance)
    {
        this.balance = balance;
    }

    public ArrayList<AttendanceInoutTime> getleave_array()
    {
        return leave_array;
    }

    public void setleave_array(ArrayList<AttendanceInoutTime> leave_array) {
        this.leave_array = leave_array;
    }

}
