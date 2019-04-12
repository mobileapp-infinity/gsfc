package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

public class ExamDetailPOJO
{
    @SerializedName("mid_ex_id")
    public String mid_ex_id;

    public  String getMid_ex_id()
    {
        return mid_ex_id;
    }

    @SerializedName("met_id")
    public String met_id;

    public  String getMet_id()
    {
        return met_id;
    }

    @SerializedName("course_fullname")
    public String course_fullname;

    public  String getCourse_fullname()
    {
        return course_fullname;
    }

    @SerializedName("sm_name")
    public String sm_name;

    public  String getSm_name()
    {
        return sm_name;
    }

    @SerializedName("mid_ex_name")
    public String mid_ex_name;

    public  String getMid_ex_name()
    {
        return mid_ex_name;
    }

    @SerializedName("met_total_marks")
    public String met_total_marks;

    public  String getMet_total_marks()
    {
        return met_total_marks;
    }

    @SerializedName("met_pass_marks")
    public String met_pass_marks;

    public  String getMet_pass_marks()
    {
        return met_pass_marks;
    }

    @SerializedName("subject_name")
    public String subject_name;

    public  String getSubject_name()
    {
        return subject_name;
    }


    @SerializedName("exam_date")
    public String exam_date;

    public  String getExam_date()
    {
        return exam_date;
    }

    @SerializedName("exam_start_time")
    public String exam_start_time;

    public  String getExam_start_time()
    {
        return exam_start_time;
    }

    @SerializedName("exam_to_time")
    public String exam_to_time;

    public  String getExam_to_time()
    {
        return exam_to_time;
    }

}
