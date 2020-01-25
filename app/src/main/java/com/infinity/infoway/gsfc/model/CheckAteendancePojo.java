package com.infinity.infoway.gsfc.model;

import java.util.List;

public class CheckAteendancePojo
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
         * Att_sem_id : 210
         * Att_division_id : 56
         * Att_period_id : 4
         * Column1 : 2019-12-20T00:00:00
         */

        private String Att_sem_id;
        private String Att_division_id;
        private String Att_period_id;
        private String Column1;

        public String getAtt_sem_id() {
            return Att_sem_id;
        }

        public void setAtt_sem_id(String Att_sem_id) {
            this.Att_sem_id = Att_sem_id;
        }

        public String getAtt_division_id() {
            return Att_division_id;
        }

        public void setAtt_division_id(String Att_division_id) {
            this.Att_division_id = Att_division_id;
        }

        public String getAtt_period_id() {
            return Att_period_id;
        }

        public void setAtt_period_id(String Att_period_id) {
            this.Att_period_id = Att_period_id;
        }

        public String getColumn1() {
            return Column1;
        }

        public void setColumn1(String Column1) {
            this.Column1 = Column1;
        }
    }


}
