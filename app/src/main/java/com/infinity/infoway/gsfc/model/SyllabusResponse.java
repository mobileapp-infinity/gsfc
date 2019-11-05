package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;



public class  SyllabusResponse
{

    @SerializedName("Subject_Name")
    private String subname;

    public void setSubname(String subname)
    {
        this.subname = subname;
    }

    @SerializedName("sub_code")
    private String sub_code;

    public void setsub_code(String sub_code)
    {
        this.sub_code = sub_code;
    }


    public String getSubname()
    {
        return subname;
    }

    @SerializedName("course_fullname")
    private String course_fullname;

    public  void setCourse_fullname(String course_fullname)
    {
        this.course_fullname=course_fullname;
    }
    public  String getCourse_fullname()
    {
        return course_fullname;
    }

    public String getSub_code()
    {
        return sub_code;
    }

    @SerializedName("PDF_URL")
    private String pdf;

    public void setPdf(String pdf)
    {
            this.pdf = pdf;
    }

    public String getPdf()
    {
        return pdf;
    }

}
