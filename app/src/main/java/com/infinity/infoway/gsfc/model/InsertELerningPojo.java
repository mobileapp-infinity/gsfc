package com.infinity.infoway.gsfc.model;

import java.util.List;

public class InsertELerningPojo
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
         * Error_code : 1
         * Error_msg : Success
         */

        private String Error_code;
        private String Error_msg;

        public String getError_code() {
            return Error_code;
        }

        public void setError_code(String Error_code) {
            this.Error_code = Error_code;
        }

        public String getError_msg() {
            return Error_msg;
        }

        public void setError_msg(String Error_msg) {
            this.Error_msg = Error_msg;
        }
    }
}
