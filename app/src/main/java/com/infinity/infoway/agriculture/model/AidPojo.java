package com.infinity.infoway.agriculture.model;

import java.util.List;

public class AidPojo
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
         * ta_id : 13
         * ta_name : Power point presentation
         */

        private String ta_id;
        private String ta_name;

        public String getTa_id() {
            return ta_id;
        }

        public void setTa_id(String ta_id) {
            this.ta_id = ta_id;
        }

        public String getTa_name() {
            return ta_name;
        }

        public void setTa_name(String ta_name) {
            this.ta_name = ta_name;
        }
    }
}
