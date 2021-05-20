package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

public class Innerdata {

    @SerializedName("tot_lect")
    private String tot_lect;
    @SerializedName("remaining_lect")
    private String remaining_lect;
    @SerializedName("present_lect")
    private String present_lect;
    @SerializedName("TotalPresent")
    private String TotalPresent;
    @SerializedName("persentage_lect")
    private String persentage_lect;
    @SerializedName("Aggr")
    private String Aggr;


    public String getTot_lect() {
        return tot_lect;
    }

    public String getRemaining_lect() {
        return remaining_lect;
    }

    public String getPresent_lect() {
        return present_lect;
    }

    public String getTotalPresent() {
        return TotalPresent;
    }

    public String getPersentage_lect() {
        return persentage_lect;
    }

    public String getAggr() {
        return Aggr;
    }
}
