package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

public class ExamTTPojo
{
    @SerializedName("srno")
    public String srno;

    public  String getSrno()
    {
        return srno;
    }

    @SerializedName("exam_id")
    public String exam_id;

    public  String getExam_id()
    {
        return exam_id;
    }

    @SerializedName("exam_name")
    public String exam_name;

    public  String getExam_name()
    {
        return exam_name;
    }

    @SerializedName("exam_start_date")
    public String exam_start_date;

    public  String getExam_start_date()
    {
        return exam_start_date;
    }

    @SerializedName("exam_end_date")
    public String exam_end_date;

    public  String getExam_end_date()
    {
        return exam_end_date;
    }

    @SerializedName("exam_result_date")
    public String exam_result_date;

    public  String getExam_result_date()
    {
        return exam_result_date;
    }

    @SerializedName("is_delete")
    public String is_delete;

    public  String getIs_delete()
    {
        return is_delete;
    }

    @SerializedName("term_name")
    public String term_name;

    public  String getTerm_name()
    {
        return term_name;
    }


    @SerializedName("weightage")
    public String weightage;

    public  String getWeightage()
    {
        return weightage;
    }

    @SerializedName("config_mark")
    public String config_mark;

    public  String getConfig_mark()
    {
        return config_mark;
    }
}
