package com.infinity.infoway.agriculture.model;

import java.io.Serializable;
import java.util.List;

public class FacultyPojo implements Serializable
{


    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean implements Serializable{
        /**
         * dl_date : 05/04/2019
         * college_id : 61
         * college_name : 01 - College of Agriculture, Junagadh
         * dept_id : 3074
         * department : 1 - Aquaculture
         * course_id : 3857
         * course_name : 1 - B.F.Sc
         * sm_id : 4735
         * semester_name : Semester - 1
         * div_id : 3658
         * division_name : A
         * sub_id : 9619
         * sub_name : AQC 101 - Principles of Aquaculture
         * batch_id : 2981
         * batch_name : BX
         * DL_RECOURSE_ID : 1452
         * resourse_name : Class-3
         * lec_no : 2
         * lecture_name : Lecture -2
         * lec_type : 1
         * DL_VERSION_ID : 1
         * lect_name : 05/04/2019<br/>Semester - 1<br/>A - BX<br/>Principles of Aquaculture<br/>Lecture -2<br/>Class-3
         * lect_type : THEORY
         * dl_date1 : 05/04/2019
         * emp_id : 2233
         * dl_is_homework : 0
         */

        private String dl_date;
        private String college_id;
        private String college_name;
        private String dept_id;
        private String department;
        private String course_id;
        private String course_name;
        private String sm_id;
        private String semester_name;
        private String div_id;
        private String division_name;
        private String sub_id;
        private String sub_name;
        private String batch_id;
        private String batch_name;
        private String DL_RECOURSE_ID;
        private String resourse_name;
        private String lec_no;
        private String lecture_name;
        private String lec_type;
        private String DL_VERSION_ID;
        private String lect_name;
        private String lect_type;
        private String dl_date1;
        private String emp_id;
        private String dl_is_homework;

        public String getDl_date() {
            return dl_date;
        }

        public void setDl_date(String dl_date) {
            this.dl_date = dl_date;
        }

        public String getCollege_id() {
            return college_id;
        }

        public void setCollege_id(String college_id) {
            this.college_id = college_id;
        }

        public String getCollege_name() {
            return college_name;
        }

        public void setCollege_name(String college_name) {
            this.college_name = college_name;
        }

        public String getDept_id() {
            return dept_id;
        }

        public void setDept_id(String dept_id) {
            this.dept_id = dept_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getSm_id() {
            return sm_id;
        }

        public void setSm_id(String sm_id) {
            this.sm_id = sm_id;
        }

        public String getSemester_name() {
            return semester_name;
        }

        public void setSemester_name(String semester_name) {
            this.semester_name = semester_name;
        }

        public String getDiv_id() {
            return div_id;
        }

        public void setDiv_id(String div_id) {
            this.div_id = div_id;
        }

        public String getDivision_name() {
            return division_name;
        }

        public void setDivision_name(String division_name) {
            this.division_name = division_name;
        }

        public String getSub_id() {
            return sub_id;
        }

        public void setSub_id(String sub_id) {
            this.sub_id = sub_id;
        }

        public String getSub_name() {
            return sub_name;
        }

        public void setSub_name(String sub_name) {
            this.sub_name = sub_name;
        }

        public String getBatch_id() {
            return batch_id;
        }

        public void setBatch_id(String batch_id) {
            this.batch_id = batch_id;
        }

        public String getBatch_name() {
            return batch_name;
        }

        public void setBatch_name(String batch_name) {
            this.batch_name = batch_name;
        }

        public String getDL_RECOURSE_ID() {
            return DL_RECOURSE_ID;
        }

        public void setDL_RECOURSE_ID(String DL_RECOURSE_ID) {
            this.DL_RECOURSE_ID = DL_RECOURSE_ID;
        }

        public String getResourse_name() {
            return resourse_name;
        }

        public void setResourse_name(String resourse_name) {
            this.resourse_name = resourse_name;
        }

        public String getLec_no() {
            return lec_no;
        }

        public void setLec_no(String lec_no) {
            this.lec_no = lec_no;
        }

        public String getLecture_name() {
            return lecture_name;
        }

        public void setLecture_name(String lecture_name) {
            this.lecture_name = lecture_name;
        }

        public String getLec_type() {
            return lec_type;
        }

        public void setLec_type(String lec_type) {
            this.lec_type = lec_type;
        }

        public String getDL_VERSION_ID() {
            return DL_VERSION_ID;
        }

        public void setDL_VERSION_ID(String DL_VERSION_ID) {
            this.DL_VERSION_ID = DL_VERSION_ID;
        }

        public String getLect_name() {
            return lect_name;
        }

        public void setLect_name(String lect_name) {
            this.lect_name = lect_name;
        }

        public String getLect_type() {
            return lect_type;
        }

        public void setLect_type(String lect_type) {
            this.lect_type = lect_type;
        }

        public String getDl_date1() {
            return dl_date1;
        }

        public void setDl_date1(String dl_date1) {
            this.dl_date1 = dl_date1;
        }

        public String getEmp_id() {
            return emp_id;
        }

        public void setEmp_id(String emp_id) {
            this.emp_id = emp_id;
        }

        public String getDl_is_homework() {
            return dl_is_homework;
        }

        public void setDl_is_homework(String dl_is_homework) {
            this.dl_is_homework = dl_is_homework;
        }
    }
}
