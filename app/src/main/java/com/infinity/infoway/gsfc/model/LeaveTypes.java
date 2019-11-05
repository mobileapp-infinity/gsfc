package com.infinity.infoway.gsfc.model;

import java.util.List;

public class LeaveTypes {
    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * lt_id : 1
         * lt_name : Medical Leave
         */

        private String lt_id;
        private String lt_name;

        public String getLt_id() {
            return lt_id;
        }

        public void setLt_id(String lt_id) {
            this.lt_id = lt_id;
        }

        public String getLt_name() {
            return lt_name;
        }

        public void setLt_name(String lt_name) {
            this.lt_name = lt_name;
        }
    }
}
