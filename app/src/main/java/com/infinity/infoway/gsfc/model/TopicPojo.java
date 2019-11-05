package com.infinity.infoway.gsfc.model;

import java.util.List;

public class TopicPojo {
    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * topic_id : 199196
         * topic_name : Unit-1 - Topic-1
         */

        private String topic_id;
        private String topic_name;

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
    }
}
