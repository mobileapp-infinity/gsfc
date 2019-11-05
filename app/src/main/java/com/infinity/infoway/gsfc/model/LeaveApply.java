package com.infinity.infoway.gsfc.model;

import java.util.List;

public class LeaveApply {
    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * insert_status : 14
         */

        private String insert_status;

        public String getInsert_status() {
            return insert_status;
        }

        public void setInsert_status(String insert_status) {
            this.insert_status = insert_status;
        }
    }
}
