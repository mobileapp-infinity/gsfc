package com.infinity.infoway.gsfc.model;

import java.io.Serializable;
import java.util.List;

public class ELGroupDisplayPojo implements Serializable
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
         * el_grp_id : 1
         * el_grp_name : JAVA
         * el_grp_desc : JAVA Tutorial
         * el_grp_type_id : Optional
         * el_grp_institute_id : 11
         * el_grp_is_status : 1
         */

        private String el_grp_id;
        private String el_grp_name;
        private String el_grp_desc;
        private String el_grp_type_id;
        private String el_grp_institute_id;
        private String el_grp_is_status;

        public String getEl_grp_id() {
            return el_grp_id;
        }

        public void setEl_grp_id(String el_grp_id) {
            this.el_grp_id = el_grp_id;
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

        public String getEl_grp_type_id() {
            return el_grp_type_id;
        }

        public void setEl_grp_type_id(String el_grp_type_id) {
            this.el_grp_type_id = el_grp_type_id;
        }

        public String getEl_grp_institute_id() {
            return el_grp_institute_id;
        }

        public void setEl_grp_institute_id(String el_grp_institute_id) {
            this.el_grp_institute_id = el_grp_institute_id;
        }

        public String getEl_grp_is_status() {
            return el_grp_is_status;
        }

        public void setEl_grp_is_status(String el_grp_is_status) {
            this.el_grp_is_status = el_grp_is_status;
        }
    }
}
