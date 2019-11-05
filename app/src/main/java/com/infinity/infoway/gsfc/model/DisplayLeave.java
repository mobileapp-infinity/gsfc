package com.infinity.infoway.gsfc.model;

import java.util.List;

public class DisplayLeave {


    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * sl_id : 67
         * sl_stud_id : 37760
         * leave_type : Medical Leave (FullDay Leave)
         * leave_date : 30/04/2019 - 30/04/2019
         * leacture_no : 1 Day
         * app_status : 0
         * app_status_text : Pending
         * document : http://jau.icrp.in/images/student_leave_photo/67.png
         * stud_remarks : testing testing testing
         * is_image : 1
         */

        private String sl_id;
        private String sl_stud_id;
        private String leave_type;
        private String leave_date;
        private String leacture_no;
        private String app_status;
        private String app_status_text;
        private String document;
        private String stud_remarks;
        private String is_image;
        private String leave_apply_date;

        public  String getLeave_apply_date(){return  leave_apply_date;}

        public String getSl_id() {
            return sl_id;
        }

        public void setSl_id(String sl_id) {
            this.sl_id = sl_id;
        }

        public String getSl_stud_id() {
            return sl_stud_id;
        }

        public void setSl_stud_id(String sl_stud_id) {
            this.sl_stud_id = sl_stud_id;
        }

        public String getLeave_type() {
            return leave_type;
        }

        public void setLeave_type(String leave_type) {
            this.leave_type = leave_type;
        }

        public String getLeave_date() {
            return leave_date;
        }

        public void setLeave_date(String leave_date) {
            this.leave_date = leave_date;
        }

        public String getLeacture_no() {
            return leacture_no;
        }

        public void setLeacture_no(String leacture_no) {
            this.leacture_no = leacture_no;
        }

        public String getApp_status() {
            return app_status;
        }

        public void setApp_status(String app_status) {
            this.app_status = app_status;
        }

        public String getApp_status_text() {
            return app_status_text;
        }

        public void setApp_status_text(String app_status_text) {
            this.app_status_text = app_status_text;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }

        public String getStud_remarks() {
            return stud_remarks;
        }

        public void setStud_remarks(String stud_remarks) {
            this.stud_remarks = stud_remarks;
        }

        public String getIs_image() {
            return is_image;
        }

        public void setIs_image(String is_image) {
            this.is_image = is_image;
        }
    }
}
