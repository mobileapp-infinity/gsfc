package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user01 on 1/22/2018.
 */

public class Emp_detail {

    @SerializedName("emp_id")
    private String emp_id;
    @SerializedName("emp_name")
    private String emp_name;
    @SerializedName("designation")
    private String designation;

    @SerializedName("department_name")
    private String department_name;

    @SerializedName("emp_mobile_no")
    private String emp_mobile_no;

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getEmp_mobile_no() {
        return emp_mobile_no;
    }

    public void setEmp_mobile_no(String emp_mobile_no) {
        this.emp_mobile_no = emp_mobile_no;
    }
}
