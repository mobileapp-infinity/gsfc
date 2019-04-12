package com.infinity.infoway.agriculture.model;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse
{
    //STUDENT PROFILE
    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private int version;

    @SerializedName("ac_full_name")
    private String school_name;

    @SerializedName("dm_full_name")
    private String dm_full_name;

    @SerializedName("course_fullname")
    private String course_fullname;

    @SerializedName("sm_name")
    private String sm_name;

    @SerializedName("stud_enrollment_no")
    private String stud_enrollment_no;

    @SerializedName("stud_admission_no")
    private String stud_admission_no;

    @SerializedName("stud_admission_year")
    private String stud_admission_year;

    @SerializedName("gen_name")
    private String gen_name;

    @SerializedName("stud_bdate")
    private String stud_bdate;

    @SerializedName("blood_group")
    private String blood_group;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("stud_adhar_no")
    private String stud_adhar_no;

    @SerializedName("admission_type")
    private String admission_type;

    @SerializedName("Fee_Type")
    private String Fee_Type;

    @SerializedName("Student_Quata")
    private String Student_Quata;

    @SerializedName("Stud_Shift")
    private String Stud_Shift;

    @SerializedName("stud_birth_place")
    private String stud_birth_place;

    @SerializedName("Stud_Address")
    private String Stud_Address;

    @SerializedName("Stud_Address1")
    private String Stud_Address1;

    @SerializedName("city_name")
    private String city_name;

    @SerializedName("state_name")
    private String state_name;

    @SerializedName("country_name")
    private String country_name;

    @SerializedName("stud_land_line_no")
    private String stud_land_line_no;

    @SerializedName("Stud_mobile_no")
    private String Stud_mobile_no;

    @SerializedName("Stud_email")
    private String Stud_email;

    @SerializedName("Stud_father_mobile_no")
    private String Stud_father_mobile_no;

    @SerializedName("religion_name")
    private String religion_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getSchoolName() {
        return school_name;
    }

    public void setSchoolName(String schoolname)
    {
        this.school_name = schoolname;
    }

    public String getDepartmentName()
    {
        return dm_full_name;
    }

    public void setDepartmentName(String dm_full_name)
    {
        this.dm_full_name = dm_full_name;
    }

    public String getCourseName()
    {
        return course_fullname;
    }

    public void setCouuseName(String course_fullname)
    {
        this.course_fullname = course_fullname;
    }

    public String getSemester()
    {
        return sm_name;
    }

    public void setSemester(String sm_name) {
        this.sm_name = sm_name;
    }

    public String getEnrollno() {
        return stud_enrollment_no;
    }

    public void setEnrollno(String stud_enrollment_no) {
        this.stud_enrollment_no = stud_enrollment_no;
    }

    public String getAdmissionno() {
        return stud_admission_no;
    }

    public void setAdmissionno(String stud_admission_no) {
        this.stud_admission_no = stud_admission_no;
    }

    public String getAdmissionyear() {
        return stud_admission_year;
    }

    public void setAdmissionyear(String stud_admission_year) {
        this.stud_admission_year = stud_admission_year;
    }

    public String getGender() {
        return gen_name;
    }

    public void setAGender(String gen_name) {
        this.gen_name = gen_name;
    }

    public String getBirthdate() {
        return stud_bdate;
    }

    public void setBirthdate(String stud_bdate) {
        this.stud_bdate = stud_bdate;
    }

    public String getBloodgroup() {
        return blood_group;
    }

    public void setBloodgroup(String blood_group)
    {
        this.blood_group = blood_group;
    }

    public String getCategory() {
        return category_name;
    }

    public void setCategory(String category_name) {
        this.category_name = category_name;
    }

    public String getAdharno() {
        return stud_adhar_no;
    }

    public void setAdharno(String stud_adhar_no) {
        this.stud_adhar_no = stud_adhar_no;
    }

    public String getAdmissionType() {
        return admission_type;
    }

    public void setAdmissionType(String admission_type) {
        this.admission_type = admission_type;
    }

    public String getFee_Type() {
        return Fee_Type;
    }

    public void setFee_Type(String Fee_Type) {
        this.Fee_Type = Fee_Type;
    }


    public String getStudent_Quata() {
        return Student_Quata;
    }

    public void setStudent_Quata(String Student_Quata) {
        this.Student_Quata = Student_Quata;
    }

    public String getStud_Shift() {
        return Stud_Shift;
    }

    public void setStud_Shift(String Stud_Shift) {
        this.Stud_Shift = Stud_Shift;
    }

    public String getStud_Address() {
        return Stud_Address;
    }

    public void setStud_Address(String Stud_Address) {
        this.Stud_Address = Stud_Address;
    }

    public String getStud_Address1() {
        return Stud_Address1;
    }

    public void setStud_Address1(String Stud_Address1) {
        this.Stud_Address1 = Stud_Address1;
    }

    public String getcity_name() {
        return city_name;
    }

    public void setcity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getstate_name() {
        return state_name;
    }

    public void setstate_name(String state_name) {
        this.state_name = state_name;
    }

    public String getcountry_name() {
        return country_name;
    }

    public void setcountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getstud_land_line_no() {
        return stud_land_line_no;
    }

    public void setstud_land_line_no(String stud_land_line_no) {
        this.stud_land_line_no = stud_land_line_no;
    }

    public String getStud_mobile_no() {
        return Stud_mobile_no;
    }

    public void setStud_mobile_no(String Stud_mobile_no) {
        this.Stud_mobile_no = Stud_mobile_no;
    }


    public String getStud_email() {
        return Stud_email;
    }

    public void setStud_email(String Stud_email) {
        this.Stud_email = Stud_email;
    }

    public String getStud_father_mobile_no() {
        return Stud_father_mobile_no;
    }

    public void setStud_father_mobile_no(String Stud_father_mobile_no) {
        this.Stud_father_mobile_no = Stud_father_mobile_no;
    }

    public String getreligion_name() {
        return religion_name;
    }

    public void setreligion_name(String religion_name) {
        this.religion_name = religion_name;
    }


    //EMPLOYEE PROFILE

    @SerializedName("emp_number")
    private String emp_number;
    @SerializedName("joining_date")
    private String joining_date;
    @SerializedName("emp_pan_no")
    private String emp_pan_no;
    @SerializedName("emp_passport_no")
    private String emp_passport_no;
    @SerializedName("emp_driving_licen_no")
    private String emp_driving_licen_no;
    @SerializedName("emp_offer_letter_no")
    private String emp_offer_letter_no;
    @SerializedName("gender")
    private String gender;
    @SerializedName("emp_bank_name")
    private String emp_bank_name;
    @SerializedName("emp_account_no")
    private String emp_account_no;
    @SerializedName("emp_branch_name")
    private String emp_branch_name;
    @SerializedName("emp_account_type")
    private String emp_account_type;
    @SerializedName("emp_adhar_no")
    private String emp_adhar_no;
    @SerializedName("emp_card_no")
    private String emp_card_no;
    @SerializedName("emp_job_time_from")
    private String emp_job_time_from;
    @SerializedName("emp_job_time_to")
    private String emp_job_time_to;
    @SerializedName("ed_name")
    private String ed_name;
    @SerializedName("Job_Name")
    private String Job_Name;
    @SerializedName("b_date")
    private String b_date;
    @SerializedName("emp_permanent_address")
    private String emp_permanent_address;
    @SerializedName("emp_present_address")
    private String emp_present_address;
    @SerializedName("emp_home_pin_code")
    private String emp_home_pin_code;
    @SerializedName("emp_mobile_phone")
    private String emp_mobile_phone;
    @SerializedName("emp_home_phone")
    private String emp_home_phone;
    @SerializedName("emp_email")
    private String emp_email;
    @SerializedName("emp_father_name")
    private String emp_father_name;
    @SerializedName("emp_father_occupation")
    private String emp_father_occupation;
    @SerializedName("emp_permanent_address1")
    private String emp_permanent_address1;
    @SerializedName("emp_mother_name")
    private String emp_mother_name;
    @SerializedName("emp_mother_occupation")
    private String emp_mother_occupation;
    @SerializedName("emp_spouse_name")
    private String emp_spouse_name;
    @SerializedName("emp_spouse_occupation")
    private String emp_spouse_occupation;
    @SerializedName("emp_child_name")
    private String emp_child_name;
    @SerializedName("emp_date_of_chield")
    private String emp_date_of_chield;
    @SerializedName("emp_qualification")
    private String emp_qualification;

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String getEmp_father_name() {
        return emp_father_name;
    }

    public void setEmp_father_name(String emp_father_name) {
        this.emp_father_name = emp_father_name;
    }

    public String getEmp_father_occupation() {
        return emp_father_occupation;
    }

    public String getEmp_permanent_address1() {
        return emp_permanent_address1;
    }

    public void setEmp_permanent_address1(String emp_permanent_address1) {
        this.emp_permanent_address1 = emp_permanent_address1;
    }

    public void setEmp_father_occupation() {
        this.emp_father_occupation = emp_father_occupation;
    }

    public String getEmp_mother_name() {
        return emp_mother_name;
    }

    public void setEmp_mother_name() {
        this.emp_mother_name = emp_mother_name;
    }

    public String getEmp_mother_occupation() {
        return emp_mother_occupation;
    }

    public void setEmp_mother_occupation(String emp_mother_occupation) {
        this.emp_mother_occupation = emp_mother_occupation;
    }

    public String getEmp_spouse_name() {
        return emp_spouse_name;
    }

    public void setEmp_spouse_name(String spouse_name) {
        this.emp_spouse_name = spouse_name;
    }

    public String getEmp_spouse_occupation() {
        return emp_spouse_occupation;

    }

    public void setEmp_spouse_occupation(String emp_spouse_occupation) {
        this.emp_spouse_occupation = emp_spouse_occupation;
    }

    public String getEmp_child_name() {
        return emp_child_name;
    }

    public void setEmp_child_name(String emp_child_name) {
        this.emp_child_name = emp_child_name;
    }

    public String getemp_date_of_chield() {
        return emp_date_of_chield;
    }

    public void setEmp_date_of_chield(String date_of_chield) {
        this.emp_date_of_chield = emp_date_of_chield;
    }

    public String getEmp_qualification() {
        return emp_qualification;
    }

    public void setEmp_qualification(String emp_qualification) {
        this.emp_qualification = emp_qualification;
    }


    public String getemp_number() {
        return emp_number;
    }

    public void setemp_number(String emp_number) {
        this.emp_number = emp_number;
    }

    public String getempjoining_date() {
        return joining_date;
    }

    public void setempjoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public String getemp_pan_no() {
        return emp_pan_no;
    }

    public void setemp_pan_no(String emp_pan_no) {
        this.emp_pan_no = emp_pan_no;
    }

    public String getemp_passport_no() {
        return emp_passport_no;
    }

    public void setemp_passport_no(String emp_passport_no) {
        this.emp_passport_no = emp_passport_no;
    }

    public String getemp_driving_licen_no() {
        return emp_driving_licen_no;
    }

    public void setemp_driving_licen_no(String emp_driving_licen_no) {
        this.emp_driving_licen_no = emp_driving_licen_no;
    }

    public String getemp_offer_letter_no() {
        return emp_offer_letter_no;
    }

    public void setemp_offer_letter_no(String emp_offer_letter_no) {
        this.emp_offer_letter_no = emp_offer_letter_no;
    }

    public String getemp_bank_name() {
        return emp_bank_name;
    }

    public void setemp_bank_name(String emp_bank_name) {
        this.emp_bank_name = emp_bank_name;
    }

    public String getemp_account_no() {
        return emp_account_no;
    }

    public void setemp_account_no(String emp_account_no) {
        this.emp_account_no = emp_account_no;
    }

    public String getemp_branch_name() {
        return emp_branch_name;
    }

    public void setemp_branch_name(String emp_branch_name) {
        this.emp_branch_name = emp_branch_name;
    }

    public String getemp_account_type() {
        return emp_account_type;
    }

    public void setemp_account_type(String emp_account_type) {
        this.emp_account_type = emp_account_type;
    }

    public String getemp_adhar_no() {
        return emp_adhar_no;
    }

    public void setemp_adhar_no(String emp_adhar_no) {
        this.emp_adhar_no = emp_adhar_no;
    }

    public String getemp_card_no() {
        return emp_card_no;
    }

    public void setemp_card_no(String emp_card_no) {
        this.emp_card_no = emp_card_no;
    }

    public String getemp_job_time_from()

    {
        return emp_job_time_from;
    }

    public void setemp_job_time_from(String emp_job_time_from) {
        this.emp_job_time_from = emp_job_time_from;
    }

    public String getemp_job_time_to() {
        return emp_job_time_to;
    }

    public void setemp_job_time_to(String emp_job_time_to) {
        this.emp_job_time_to = emp_job_time_to;
    }

    public String geted_name() {
        return ed_name;
    }

    public void seted_name(String ed_name) {
        this.ed_name = ed_name;
    }

    public String getJob_Name() {
        return Job_Name;
    }

    public void setJob_Name(String Job_Name) {
        this.Job_Name = Job_Name;
    }

    public String getempb_date() {
        return b_date;
    }

    public void setempb_date(String b_date) {
        this.b_date = b_date;
    }

    public String getemp_home_address_line1() {
        return emp_permanent_address;
    }

    public void setemp_home_address_line1(String emp_permanent_address) {
        this.emp_permanent_address = emp_permanent_address;
    }

    public String getemp_home_address_line2() {
        return emp_present_address;
    }

    public void setemp_home_address_line2(String emp_present_address) {
        this.emp_present_address = emp_present_address;
    }

    public String getemp_home_pin_code() {
        return emp_home_pin_code;
    }

    public void setemp_home_pin_code(String emp_home_pin_code) {
        this.emp_home_pin_code = emp_home_pin_code;
    }

    public String getemp_mobile_phone() {
        return emp_mobile_phone;
    }

    public void setemp_mobile_phone(String emp_mobile_phone) {
        this.emp_mobile_phone = emp_mobile_phone;
    }

    public String getemp_home_phone() {
        return emp_home_phone;
    }

    public void setemp_home_phone(String emp_home_phone) {
        this.emp_home_phone = emp_home_phone;
    }

    public String getemp_email() {
        return emp_email;
    }

    public void setemp_email(String emp_email) {
        this.emp_email = emp_email;
    }

}