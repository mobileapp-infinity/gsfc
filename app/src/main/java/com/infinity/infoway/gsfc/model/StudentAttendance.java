package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user01 on 7/13/2017.
 */

public class StudentAttendance {
    @SerializedName("date")
    private String date;

    public String getdate() {
        return date;
    }

    @SerializedName("all_lecture")
    private ArrayList<Methods> all_lecture;

    public ArrayList<Methods> getall_lecture() {
        return all_lecture;
    }

}
