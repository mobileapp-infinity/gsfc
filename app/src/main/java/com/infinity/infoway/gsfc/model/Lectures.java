package com.infinity.infoway.gsfc.model;

import java.util.List;

public class Lectures {
    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * srno : 1
         * DL_ID : 10624
         * dm_id : 3074
         * dm_full_name : Aquaculture
         * course_id : 3857
         * course_fullname : B.F.Sc
         * sm_id : 4735
         * sm_name : Semester - 1
         * dvm_id : 3658
         * dvm_name : A
         * emp_id : 2231
         * emp_name :  Patel Hiteshkumar Nathabhai
         * dl_date : 22/04/2019
         * DL_LEC_NO : 1
         * DL_LEC_STATUS : 1
         */

        private String srno;
        private String DL_ID;
        private String dm_id;
        private String dm_full_name;
        private String course_id;
        private String course_fullname;
        private String sm_id;
        private String sm_name;
        private String dvm_id;
        private String dvm_name;
        private int emp_id;
        private String emp_name;
        private String dl_date;
        private String DL_LEC_NO;
        private String DL_LEC_STATUS;

        public String getSrno() {
            return srno;
        }

        public void setSrno(String srno) {
            this.srno = srno;
        }

        public String getDL_ID() {
            return DL_ID+"";
        }

        public void setDL_ID(String DL_ID) {
            this.DL_ID = DL_ID+"";
        }

        public String getDm_id() {
            return dm_id;
        }

        public void setDm_id(String dm_id) {
            this.dm_id = dm_id;
        }

        public String getDm_full_name() {
            return dm_full_name;
        }

        public void setDm_full_name(String dm_full_name) {
            this.dm_full_name = dm_full_name;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_fullname() {
            return course_fullname;
        }

        public void setCourse_fullname(String course_fullname) {
            this.course_fullname = course_fullname;
        }

        public String getSm_id() {
            return sm_id;
        }

        public void setSm_id(String sm_id) {
            this.sm_id = sm_id;
        }

        public String getSm_name() {
            return sm_name;
        }

        public void setSm_name(String sm_name) {
            this.sm_name = sm_name;
        }

        public String getDvm_id() {
            return dvm_id;
        }

        public void setDvm_id(String dvm_id) {
            this.dvm_id = dvm_id;
        }

        public String getDvm_name() {
            return dvm_name;
        }

        public void setDvm_name(String dvm_name) {
            this.dvm_name = dvm_name;
        }

        public int getEmp_id() {
            return emp_id;
        }

        public void setEmp_id(int emp_id) {
            this.emp_id = emp_id;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }

        public String getDl_date() {
            return dl_date;
        }

        public void setDl_date(String dl_date) {
            this.dl_date = dl_date;
        }

        public String getDL_LEC_NO() {
            return DL_LEC_NO;
        }

        public void setDL_LEC_NO(String DL_LEC_NO) {
            this.DL_LEC_NO = DL_LEC_NO;
        }

        public String getDL_LEC_STATUS() {
            return DL_LEC_STATUS;
        }

        public void setDL_LEC_STATUS(String DL_LEC_STATUS) {
            this.DL_LEC_STATUS = DL_LEC_STATUS;
        }
    }
}
