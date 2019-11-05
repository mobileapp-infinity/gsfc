package com.infinity.infoway.gsfc.model;

import java.util.List;

public class InternshipNamePojo
{


    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * ints_id : 2
         * ints_name : Internship-1_IBM
         */

        private String ints_id;
        private String ints_name;

        public String getInts_id() {
            return ints_id;
        }

        public void setInts_id(String ints_id) {
            this.ints_id = ints_id;
        }

        public String getInts_name() {
            return ints_name;
        }

        public void setInts_name(String ints_name) {
            this.ints_name = ints_name;
        }
    }
}
