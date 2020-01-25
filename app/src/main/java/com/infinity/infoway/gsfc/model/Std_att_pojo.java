package com.infinity.infoway.gsfc.model;

import java.util.List;

public class Std_att_pojo
{


    private List<DataBean> Data;

    public List<DataBean> getData()
    {
        return Data;
    }

    public void setData(List<DataBean> Data)
    {
        this.Data = Data;
    }  public void addData(List<DataBean> Data)
    {
        //for(int i=0;i<Data)
        this.Data.addAll(Data);

    }

    public static class DataBean {
        /**
         * date : 01/11/2019_H-DAY
         * day_id : 5
         * all_lecture : [{"att_status":"","lec_status":""},{"att_status":"","lec_status":""},{"att_status":"","lec_status":""},{"att_status":"","lec_status":""},{"att_status":"","lec_status":""},{"att_status":"","lec_status":""}]
         */

        private String date;
        private String day_id;
        private List<AllLectureBean> all_lecture;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay_id() {
            return day_id;
        }

        public void setDay_id(String day_id) {
            this.day_id = day_id;
        }

        public List<AllLectureBean> getAll_lecture() {
            return all_lecture;
        }

        public void setAll_lecture(List<AllLectureBean> all_lecture) {
            this.all_lecture = all_lecture;
        }

        public static class AllLectureBean {
            /**
             * att_status :
             * lec_status :
             */

            private String att_status;
            private String lec_status;

            public String getAtt_status() {
                return att_status;
            }

            public void setAtt_status(String att_status) {
                this.att_status = att_status;
            }

            public String getLec_status() {
                return lec_status;
            }

            public void setLec_status(String lec_status) {
                this.lec_status = lec_status;
            }
        }
    }
}
