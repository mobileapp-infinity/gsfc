package com.infinity.infoway.gsfc.model;

import java.util.List;

public class ViewInternShipPojo
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
         * swr_id : 1
         * swr_report_title : 02
         * swr_decription : 02
         * swr_report_file : 20190704143752571_Housekeeping Services Tender Documents - GSFCU-19-20-171957.pdf
         * ints_name : Chemistry Intern
         * sra_approve : 1
         */

        private String swr_id;
        private String swr_report_title;
        private String swr_decription;
        private String swr_report_file;
        private String ints_name;
        private String sra_approve;

        public String getSwr_id() {
            return swr_id;
        }

        public void setSwr_id(String swr_id) {
            this.swr_id = swr_id;
        }

        public String getSwr_report_title() {
            return swr_report_title;
        }

        public void setSwr_report_title(String swr_report_title) {
            this.swr_report_title = swr_report_title;
        }

        public String getSwr_decription() {
            return swr_decription;
        }

        public void setSwr_decription(String swr_decription) {
            this.swr_decription = swr_decription;
        }

        public String getSwr_report_file() {
            return swr_report_file;
        }

        public void setSwr_report_file(String swr_report_file) {
            this.swr_report_file = swr_report_file;
        }

        public String getInts_name() {
            return ints_name;
        }

        public void setInts_name(String ints_name) {
            this.ints_name = ints_name;
        }

        public String getSra_approve() {
            return sra_approve;
        }

        public void setSra_approve(String sra_approve) {
            this.sra_approve = sra_approve;
        }
    }
}
