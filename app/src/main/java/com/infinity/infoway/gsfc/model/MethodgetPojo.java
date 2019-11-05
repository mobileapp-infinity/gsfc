package com.infinity.infoway.gsfc.model;

import java.util.List;

public class MethodgetPojo {


    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * unit_id : 138912
         * unit_name : Unit-1
         * topic_id : 199196
         * topic_name : Topic-1
         * topic_name1 : Unit-1 - Topic-1
         * topic_method : 22,
         * topic_aid : 13
         */

        private String unit_id;
        private String unit_name;
        private String topic_id;
        private String topic_name;
        private String topic_name1;
        private String topic_method;
        private String topic_aid;

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getTopic_name1() {
            return topic_name1;
        }

        public void setTopic_name1(String topic_name1) {
            this.topic_name1 = topic_name1;
        }

        public String getTopic_method() {
            return topic_method;
        }

        public void setTopic_method(String topic_method) {
            this.topic_method = topic_method;
        }

        public String getTopic_aid() {
            return topic_aid;
        }

        public void setTopic_aid(String topic_aid) {
            this.topic_aid = topic_aid;
        }
    }
}
