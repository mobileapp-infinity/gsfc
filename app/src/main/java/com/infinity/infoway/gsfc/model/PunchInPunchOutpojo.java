package com.infinity.infoway.gsfc.model;

import java.util.List;

public class PunchInPunchOutpojo
{
    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * msg :
         * in_out_count : 2
         * ints_name : GATL
         * cm_name : IBM
         * ints_description : NA
         * ints_from_date : 29-10-2019
         * ints_to_date : 05-11-2019
         * shift_time : null
         * ints_total_marks : 50
         * ints_passing_marks : 25
         * ism_mark : 85
         */

        private String msg;
        private String in_out_count;
        private String ints_name;
        private String cm_name;
        private String ints_description;
        private String ints_from_date;
        private String ints_to_date;
        private String shift_time;
        private String ints_total_marks;
        private String ints_passing_marks;
        private String ism_mark;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getIn_out_count() {
            return in_out_count;
        }

        public void setIn_out_count(String in_out_count) {
            this.in_out_count = in_out_count;
        }

        public String getInts_name() {
            return ints_name;
        }

        public void setInts_name(String ints_name) {
            this.ints_name = ints_name;
        }

        public String getCm_name() {
            return cm_name;
        }

        public void setCm_name(String cm_name) {
            this.cm_name = cm_name;
        }

        public String getInts_description() {
            return ints_description;
        }

        public void setInts_description(String ints_description) {
            this.ints_description = ints_description;
        }

        public String getInts_from_date() {
            return ints_from_date;
        }

        public void setInts_from_date(String ints_from_date) {
            this.ints_from_date = ints_from_date;
        }

        public String getInts_to_date() {
            return ints_to_date;
        }

        public void setInts_to_date(String ints_to_date) {
            this.ints_to_date = ints_to_date;
        }

        public Object getShift_time() {
            return shift_time;
        }

        public void setShift_time(String shift_time) {
            this.shift_time = shift_time;
        }

        public String getInts_total_marks() {
            return ints_total_marks;
        }

        public void setInts_total_marks(String ints_total_marks) {
            this.ints_total_marks = ints_total_marks;
        }

        public String getInts_passing_marks() {
            return ints_passing_marks;
        }

        public void setInts_passing_marks(String ints_passing_marks) {
            this.ints_passing_marks = ints_passing_marks;
        }

        public String getIsm_mark() {
            return ism_mark;
        }

        public void setIsm_mark(String ism_mark) {
            this.ism_mark = ism_mark;
        }
    }

/*    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        *//**
         * Count_login : 0
         *//*

        private String Count_login;

        public String getCount_login() {
            return Count_login;
        }

        public void setCount_login(String Count_login) {
            this.Count_login = Count_login;
        }
    }*/





}
