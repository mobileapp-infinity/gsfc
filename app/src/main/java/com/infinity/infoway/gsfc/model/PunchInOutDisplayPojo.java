package com.infinity.infoway.gsfc.model;

import java.util.List;

public class PunchInOutDisplayPojo
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
         * date : 2019-07-04T00:00:00
         * intime : 17:45:18
         * outtime : 17:45:18
         */

        private String date;
        private String intime;
        private String outtime;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getIntime() {
            return intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        public String getOuttime() {
            return outtime;
        }

        public void setOuttime(String outtime) {
            this.outtime = outtime;
        }
    }
}
