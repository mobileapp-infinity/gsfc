package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user01 on 1/17/2018.
 */

public class hod_list {

    @SerializedName("college_id")
    public String college_id;

    public  String getCollege_id()
    {
        return college_id;
    }

    @SerializedName("college_name")
    public String college_name;

    public String getCollege_name()
    {
        return college_name;
    }

    @SerializedName("emp_detail_array")
    private ArrayList<Emp_detail> Emp_detail;


    @SerializedName("emp_id")
    public String emp_id;

    public  String getEmp_id()
    {
        return emp_id;
    }

    @SerializedName("emp_name")
    public String emp_name;

    public String getEmp_name()
    {
        return emp_name;
    }

    @SerializedName("designation")
    public  String designation;

    public String getDesignation()
    {
        return  designation;
    }
    @SerializedName("department_name")
    public  String department_name;

    public  String getDepartment_name()
    {
        return  department_name;
    }

    @SerializedName("emp_mobile_no")
    public  String emp_mobile_no;

    public  String getEmp_mobile_no()
    {
        return  emp_mobile_no;
    }

    public ArrayList<Emp_detail> getEmp_detail()
    {
        return Emp_detail;
    }

    public void setEmp_detail(ArrayList<Emp_detail> Emp_detail)
    {
        this.Emp_detail = Emp_detail;


    }
}
