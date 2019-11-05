package com.infinity.infoway.gsfc.model;

import java.util.List;

public class AssignView
{


    private List<AssignmentBean> Assignment;

    public List<AssignmentBean> getAssignment() {
        return Assignment;
    }

    public void setAssignment(List<AssignmentBean> Assignment) {
        this.Assignment = Assignment;
    }

    public static class AssignmentBean {
        /**
         * sr_no : 1
         * ac_short_name : College of Agriculture
         * dm_short_name : Aquaculture
         * course_short_name : B.F.Sc
         * sm_name : Semester - 1
         * dvm_name : A
         * sub_short_name : Principles of Aquaculture
         * am_last_seen_date : 25/04/2019
         * am_id : 374
         * am_name : ni
         * am_file : http://jau.icrp.in/images/Assignment_File/374.jpg
         * created_date : 24/04/2019
         */

        private String sr_no;
        private String ac_short_name;
        private String dm_short_name;
        private String course_short_name;
        private String sm_name;
        private String dvm_name;
        private String sub_short_name;
        private String am_last_seen_date;
        private String am_id;
        private String am_name;
        private String am_file;
        private String created_date;

        public String getSr_no() {
            return sr_no;
        }

        public void setSr_no(String sr_no) {
            this.sr_no = sr_no;
        }

        public String getAc_short_name() {
            return ac_short_name;
        }

        public void setAc_short_name(String ac_short_name) {
            this.ac_short_name = ac_short_name;
        }

        public String getDm_short_name() {
            return dm_short_name;
        }

        public void setDm_short_name(String dm_short_name) {
            this.dm_short_name = dm_short_name;
        }

        public String getCourse_short_name() {
            return course_short_name;
        }

        public void setCourse_short_name(String course_short_name) {
            this.course_short_name = course_short_name;
        }

        public String getSm_name() {
            return sm_name;
        }

        public void setSm_name(String sm_name) {
            this.sm_name = sm_name;
        }

        public String getDvm_name() {
            return dvm_name;
        }

        public void setDvm_name(String dvm_name) {
            this.dvm_name = dvm_name;
        }

        public String getSub_short_name() {
            return sub_short_name;
        }

        public void setSub_short_name(String sub_short_name) {
            this.sub_short_name = sub_short_name;
        }

        public String getAm_last_seen_date() {
            return am_last_seen_date;
        }

        public void setAm_last_seen_date(String am_last_seen_date) {
            this.am_last_seen_date = am_last_seen_date;
        }

        public String getAm_id() {
            return am_id;
        }

        public void setAm_id(String am_id) {
            this.am_id = am_id;
        }

        public String getAm_name() {
            return am_name;
        }

        public void setAm_name(String am_name) {
            this.am_name = am_name;
        }

        public String getAm_file() {
            return am_file;
        }

        public void setAm_file(String am_file) {
            this.am_file = am_file;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}
