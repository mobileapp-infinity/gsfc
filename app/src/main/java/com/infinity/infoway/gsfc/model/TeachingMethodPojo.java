package com.infinity.infoway.gsfc.model;

import java.util.List;

public class TeachingMethodPojo
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
         * tm_id : 1
         * tm_short_name : L
         * method_name : L -  Lecture (L)
         */

        private String tm_id;
        private String tm_short_name;
        private String method_name;

        public String getTm_id() {
            return tm_id;
        }

        public void setTm_id(String tm_id) {
            this.tm_id = tm_id;
        }

        public String getTm_short_name() {
            return tm_short_name;
        }

        public void setTm_short_name(String tm_short_name) {
            this.tm_short_name = tm_short_name;
        }

        public String getMethod_name() {
            return method_name;
        }

        public void setMethod_name(String method_name) {
            this.method_name = method_name;
        }
    }
}
