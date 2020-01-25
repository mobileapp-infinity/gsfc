package com.infinity.infoway.gsfc.model;

import java.util.List;

public class ConfigurationAttPojo
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
         * ac_id : 1
         * ac_attendance_method : 1
         */

        private String ac_id;
        private String ac_attendance_method;

        public String getAc_id() {
            return ac_id;
        }

        public void setAc_id(String ac_id) {
            this.ac_id = ac_id;
        }

        public String getAc_attendance_method() {
            return ac_attendance_method;
        }

        public void setAc_attendance_method(String ac_attendance_method) {
            this.ac_attendance_method = ac_attendance_method;
        }
    }
}
