package com.infinity.infoway.gsfc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 12/16/2017.
 */

public class InsertData {
    @SerializedName("student_id")
    private String student_id;

    //Student Detail
    @SerializedName("stud_admission_no")
    private String stud_admission_no;

    @SerializedName("stud_main_school_id")
    private String stud_main_school_id;
    @SerializedName("year_id")
    private String year_id;
    @SerializedName("key")
    private String key;

    @SerializedName("status")
    private String status;

    public InsertData(String student_id,String status, String stud_admission_no, String stud_main_school_id, String year_id, String key) {
    this.student_id=student_id;
        this.stud_admission_no=stud_admission_no;
        this.stud_main_school_id=stud_main_school_id;
        this.year_id=year_id;
        this.key=key;
        this.status=status;

    }
    public  String getStatus(){return  status;}
    public  void setStatus(String status){this.status=status;}

    public  String getStudent_id(){return  student_id;}
    public void setStudent_id(String student_id){this.student_id=student_id;}

    public String getStud_admission_no(){return  stud_admission_no;}
    public  void setStud_admission_no(String stud_admission_no){this.stud_admission_no=stud_admission_no;}

    public String getStud_main_school_id(){return stud_main_school_id;}
    public  void setStud_main_school_id(String stud_main_school_id){this.stud_main_school_id=stud_main_school_id;}

    public String getYear_id(){return year_id;}
    public  void setYear_id(String year_id){this.year_id=year_id;}

    public String getKey(){return key;}
    public  void setKey(String key){this.key=key;}


}
