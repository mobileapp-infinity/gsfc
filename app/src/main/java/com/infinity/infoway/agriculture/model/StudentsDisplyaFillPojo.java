package com.infinity.infoway.agriculture.model;

import java.util.List;

public class StudentsDisplyaFillPojo
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
         * sr_no : 1
         * Stud_Name :  Gadhethariya Bansi Ashokbhai
         * Stud_id : 37754
         * swd_batch_id : 2981
         * Stud_Enrollment_no : 123456
         * swd_roll_no : 1
         * Att_status : 0
         * Att_status1 : null
         * pre_att_status : -
         * config_pre_att_status : 1
         * is_elective : 0
         */

        private String sr_no;
        private String Stud_Name;
        private String Stud_id;
        private String swd_batch_id;
        private String Stud_Enrollment_no;
        private String swd_roll_no;
        private String Att_status;
        private Object Att_status1;
        private String pre_att_status;
        private String config_pre_att_status;
        private String is_elective;

        public String getSr_no() {
            return sr_no;
        }

        public void setSr_no(String sr_no) {
            this.sr_no = sr_no;
        }

        public String getStud_Name() {
            return Stud_Name;
        }

        public void setStud_Name(String Stud_Name) {
            this.Stud_Name = Stud_Name;
        }

        public String getStud_id() {
            return Stud_id;
        }

        public void setStud_id(String Stud_id) {
            this.Stud_id = Stud_id;
        }

        public String getSwd_batch_id() {
            return swd_batch_id;
        }

        public void setSwd_batch_id(String swd_batch_id) {
            this.swd_batch_id = swd_batch_id;
        }

        public String getStud_Enrollment_no() {
            return Stud_Enrollment_no;
        }

        public void setStud_Enrollment_no(String Stud_Enrollment_no) {
            this.Stud_Enrollment_no = Stud_Enrollment_no;
        }

        public String getSwd_roll_no() {
            return swd_roll_no;
        }

        public void setSwd_roll_no(String swd_roll_no) {
            this.swd_roll_no = swd_roll_no;
        }

        public String getAtt_status() {
            return Att_status;
        }

        public void setAtt_status(String Att_status) {
            this.Att_status = Att_status;
        }

        public Object getAtt_status1() {
            return Att_status1;
        }

        public void setAtt_status1(Object Att_status1) {
            this.Att_status1 = Att_status1;
        }

        public String getPre_att_status() {
            return pre_att_status;
        }

        public void setPre_att_status(String pre_att_status) {
            this.pre_att_status = pre_att_status;
        }

        public String getConfig_pre_att_status() {
            return config_pre_att_status;
        }

        public void setConfig_pre_att_status(String config_pre_att_status) {
            this.config_pre_att_status = config_pre_att_status;
        }

        public String getIs_elective() {
            return is_elective;
        }

        public void setIs_elective(String is_elective) {
            this.is_elective = is_elective;
        }
    }
}
