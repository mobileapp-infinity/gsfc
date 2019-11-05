package com.infinity.infoway.gsfc.model;

import java.util.List;

public class PostElPojo
{

    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * el_grp_file_id : 7
         * el_grp_file_grp_id : 2
         * el_grp_name : ASP .Net
         * el_grp_desc : ASP .Net Tutorial
         * el_grp_file_name : 2_7.zip
         * el_grp_file_desc : File Uploading
         * el_grp_file_date : 21/03/2019
         * el_file_url : ../Images/E_Learning_File/11/2/2_7.zip
         */

        private String el_grp_file_id;
        private String el_grp_file_grp_id;
        private String el_grp_name;
        private String el_grp_desc;
        private String el_grp_file_name;
        private String el_grp_file_desc;
        private String el_grp_file_date;
        private String el_file_url;

        public String getEl_grp_file_id() {
            return el_grp_file_id;
        }

        public void setEl_grp_file_id(String el_grp_file_id) {
            this.el_grp_file_id = el_grp_file_id;
        }

        public String getEl_grp_file_grp_id() {
            return el_grp_file_grp_id;
        }

        public void setEl_grp_file_grp_id(String el_grp_file_grp_id) {
            this.el_grp_file_grp_id = el_grp_file_grp_id;
        }

        public String getEl_grp_name() {
            return el_grp_name;
        }

        public void setEl_grp_name(String el_grp_name) {
            this.el_grp_name = el_grp_name;
        }

        public String getEl_grp_desc() {
            return el_grp_desc;
        }

        public void setEl_grp_desc(String el_grp_desc) {
            this.el_grp_desc = el_grp_desc;
        }

        public String getEl_grp_file_name() {
            return el_grp_file_name;
        }

        public void setEl_grp_file_name(String el_grp_file_name) {
            this.el_grp_file_name = el_grp_file_name;
        }

        public String getEl_grp_file_desc() {
            return el_grp_file_desc;
        }

        public void setEl_grp_file_desc(String el_grp_file_desc) {
            this.el_grp_file_desc = el_grp_file_desc;
        }

        public String getEl_grp_file_date() {
            return el_grp_file_date;
        }

        public void setEl_grp_file_date(String el_grp_file_date) {
            this.el_grp_file_date = el_grp_file_date;
        }

        public String getEl_file_url() {
            return el_file_url;
        }

        public void setEl_file_url(String el_file_url) {
            this.el_file_url = el_file_url;
        }
    }
}
