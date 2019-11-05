package com.infinity.infoway.gsfc.model;

import java.util.List;

public class GrpNamePojo
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
         * el_grp_id : 2
         * el_grp_name : ASP .Net
         */

        private String el_grp_id;
        private String el_grp_name;

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
    }
}
