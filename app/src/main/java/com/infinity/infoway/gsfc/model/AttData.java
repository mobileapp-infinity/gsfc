package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttData {

    @SerializedName("sub_id")
    private String sub_id;
    @SerializedName("sub_fullname")
    private String sub_fullname;
    @SerializedName("Day")
    private String day;

    @SerializedName("inout_array1")
    private ArrayList<Innerdata> inout_array1;

    public String getSub_id() {
        return sub_id;
    }


    public String getSub_fullname() {
        return sub_fullname;
    }


    public ArrayList<Innerdata> getInout_array1() {
        return inout_array1;
    }

    public void setInout_Array(ArrayList<Innerdata> Innerdata) {
        this.inout_array1 = inout_array1;


    }
}
