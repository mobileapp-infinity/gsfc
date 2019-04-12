package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class LoginResponse
{

    @SerializedName("status")
    private String status;


    //Student Detail
    @SerializedName("stud_id")
    private String stud_id;
    @SerializedName("dm_id")
    private String dm_id;
    @SerializedName("dm_full_name")
    private String dm_full_name;
    @SerializedName("course_id")
    private String course_id;
    @SerializedName("course_fullname")
    private String course_fullname;
    @SerializedName("sm_id")
    private String sm_id;
    @SerializedName("sm_name")
    private String sm_name;
    @SerializedName("ac_id")
    private String ac_id;
    @SerializedName("ac_full_name")
    private String ac_full_name;
    @SerializedName("swd_year_id")
    private String swd_year_id;
    @SerializedName("name")
    private String name;
    @SerializedName("stud_admission_no")
    private String stud_admission_no;
    @SerializedName("stud_photo")
    private String stud_photo;
    @SerializedName("swd_batch_id")
    private String swd_batch_id;
    @SerializedName("fc_file")
    private String fc_file;
    @SerializedName("intitute_id")
    private String institute_id;
    @SerializedName("im_domain_name")
    private String im_domain_name;

    //Employee Detail
    @SerializedName("is_director")
    private String is_director;

    @SerializedName("emp_id")
    private String emp_id;

    @SerializedName("ac_code")
    private String ac_code;

    @SerializedName("emp_number")
    private String emp_number;

    @SerializedName("emp_main_school_id")
    private String emp_main_school_id;

    @SerializedName("emp_username")
    private String emp_username;

    @SerializedName("emp_password")
    private String emp_password;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("ac_logo")
    private String ac_logo;

    @SerializedName("swd_division_id")
    private String swd_division_id;

    @SerializedName("shift_id")
    private String shift_id;

    @SerializedName("url")
    private ArrayList<String> url;

    @SerializedName("Success")
    private String Success;


    @SerializedName("emp_year_id")
    private String emp_year_id;

    @SerializedName("institute_id")
    private  String institute_id_;



    public ArrayList<String> geturl()
    {
        return url;
    }


    public LoginResponse(String status, String is_director, String ac_code, String emp_number, String stud_id, String dm_id, String dm_full_name, String course_id, String course_fullname, String name, String stud_photo, String emp_id, String stud_admission_no, String emp_main_school_id, String emp_username, String emp_password, String ac_logo,String emp_year_id,String fc_file,String institute_id,String institute_id_,String im_domain_name)
    {

        //Student detail
        this.stud_id = stud_id;
        this.course_id = course_id;
        this.dm_id = dm_id;
        this.dm_full_name = dm_full_name;
        this.course_fullname = course_fullname;
        this.name = name;
        this.stud_photo = stud_photo;
        this.stud_admission_no = stud_admission_no;
        this.status = status;
        this.fc_file=fc_file;
        this.institute_id=institute_id;
        this.im_domain_name=im_domain_name;
        //employee detail
        this.emp_id = emp_id;
        this.emp_main_school_id = emp_main_school_id;
        this.emp_username = emp_username;
        this.emp_password = emp_password;
        this.ac_logo = ac_logo;
        this.emp_number = emp_number;
        this.ac_code = ac_code;
        this.is_director = is_director;
        this.emp_year_id = emp_year_id;
        this.institute_id_=institute_id_;
        this.im_domain_name=im_domain_name;
    }
    public String getinstitute_id(){
        return  institute_id;
    }
    public String getfc_file(){return  fc_file;}

    public String getemp_year_id()
    {
        return emp_year_id;
    }
    public  String getim_domain_name(){return  im_domain_name;}


    public String getAc_code()
    {
        return ac_code;
    }

    public void setAc_id(String ac_code)
    {
        this.ac_code = ac_code;
    }

    public String getEmp_number() {
        return emp_number;
    }

    public void setEmp_number(String emp_number) {
        this.emp_number = emp_number;
    }

    public String getstud_admission_no() {
        return stud_admission_no;
    }

    public void setstud_admission_no(String stud_admission_no) {
        this.stud_admission_no = stud_admission_no;
    }

    public String getstatus()
    {
        return status;
    }


    public void setstatus(String status) {
        this.status = status;
    }

    public String getshift_id() {
        return shift_id;
    }

    public void setshift_ids(String shift_id) {
        this.shift_id = shift_id;
    }

    //Student Detail
    public String getstud_id() {
        return stud_id;
    }

    public void setstud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getdm_id() {
        return dm_id;
    }

    public void setdm_id(String dm_id) {
        this.dm_id = dm_id;
    }

    public String getdm_full_name() {
        return dm_full_name;
    }

    public void setdm_full_name(String dm_full_name) {
        this.dm_full_name = dm_full_name;
    }

    public String getcourse_id() {
        return course_id;
    }

    public void setcourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getcourse_fullname() {
        return course_fullname;
    }

    public void setcourse_fullname(String course_fullname) {
        this.course_fullname = course_fullname;
    }

    public String getsm_id() {
        return sm_id;
    }

    public String getSuccess() {
        return Success;
    }

    public void setsm_id(String sm_id) {
        this.sm_id = sm_id;
    }

    public String getsm_name() {
        return sm_name;
    }

    public void setsm_name(String sm_name) {
        this.sm_name = sm_name;
    }


    public String getac_id() {
        return ac_id;
    }

    public void setac_id(String ac_id) {
        this.ac_id = ac_id;
    }

    public String getac_full_name() {
        return ac_full_name;
    }

    public void setac_full_name(String ac_full_name) {
        this.ac_full_name = ac_full_name;
    }

    public String getswd_year_id() {
        return swd_year_id;
    }

    public void setswd_year_id(String swd_year_id) {
        this.swd_year_id = swd_year_id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getstud_photo() {
        return stud_photo;
    }

    public void setstud_photo(String stud_photo) {
        this.stud_photo = stud_photo;
    }

    public String getswd_batch_id() {
        return swd_batch_id;
    }

    public void setswd_batch_id(String swd_batch_id) {
        this.swd_batch_id = swd_batch_id;
    }

    public String getswd_division_id() {
        return swd_division_id;
    }

    public void setswd_division_id(String swd_division_id) {
        this.swd_division_id = swd_division_id;
    }


    //Employee Detail
    public String getemp_id()
    {
        return emp_id;
    }

    public void setemp_id(String emp_id)
    {
        this.emp_id = emp_id;
    }

    public String getemp_main_school_id()
    {
        return emp_main_school_id;
    }

    public void setemp_main_school_id(String emp_main_school_id) {
        this.emp_main_school_id = emp_main_school_id;
    }
    public String getinstitute_id_()
    {
        return institute_id_;
    }
    public String getIs_director()
    {
        return is_director;
    }

    public void setIs_director(String is_director) {
        this.is_director = is_director;
    }

    public String getemp_username() {
        return emp_username;
    }

    public void setemp_username(String emp_username) {
        this.emp_username = emp_username;
    }

    public String getemp_password() {
        return emp_password;
    }

    public void setemp_password(String emp_password) {
        this.emp_password = emp_password;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getac_logo() {
        return ac_logo;
    }

    public void setac_logo(String ac_logo) {
        this.ac_logo = ac_logo;
    }
}
