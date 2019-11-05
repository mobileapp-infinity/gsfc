package com.infinity.infoway.gsfc.model;

import java.util.List;

public class EnrollGrpPoJO
{


    private List<EnrollGroupBean> Enroll_Group;

    public List<EnrollGroupBean> getEnroll_Group() {
        return Enroll_Group;
    }

    public void setEnroll_Group(List<EnrollGroupBean> Enroll_Group) {
        this.Enroll_Group = Enroll_Group;
    }

    public static class EnrollGroupBean {
        /**
         * group_id : 1
         * group_name : JAVA
         * group_created_date : 10/03/2019
         * group_description_array : [{"grp_desc":"JAVA Tutorial"}]
         */

        private String group_id;
        private String group_name;
        private String group_created_date;
        private List<GroupDescriptionArrayBean> group_description_array;

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getGroup_created_date() {
            return group_created_date;
        }

        public void setGroup_created_date(String group_created_date) {
            this.group_created_date = group_created_date;
        }

        public List<GroupDescriptionArrayBean> getGroup_description_array() {
            return group_description_array;
        }

        public void setGroup_description_array(List<GroupDescriptionArrayBean> group_description_array) {
            this.group_description_array = group_description_array;
        }

        public static class GroupDescriptionArrayBean {
            /**
             * grp_desc : JAVA Tutorial
             */

            private String grp_desc;

            public String getGrp_desc() {
                return grp_desc;
            }

            public void setGrp_desc(String grp_desc) {
                this.grp_desc = grp_desc;
            }
        }
    }
}
