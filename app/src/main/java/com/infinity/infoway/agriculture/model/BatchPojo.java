package com.infinity.infoway.agriculture.model;

import java.util.List;

public class BatchPojo
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
         * bch_id : 2981
         * bch_name : BX
         */

        private String bch_id;
        private String bch_name;

        public String getBch_id() {
            return bch_id;
        }

        public void setBch_id(String bch_id) {
            this.bch_id = bch_id;
        }

        public String getBch_name() {
            return bch_name;
        }

        public void setBch_name(String bch_name) {
            this.bch_name = bch_name;
        }
    }
}

