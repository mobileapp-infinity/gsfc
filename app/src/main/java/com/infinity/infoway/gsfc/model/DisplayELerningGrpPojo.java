package com.infinity.infoway.gsfc.model;

import java.util.List;

public class DisplayELerningGrpPojo

{


    private List<MyGroupBean> My_Group;

    public List<MyGroupBean> getMy_Group() {
        return My_Group;
    }

    public void setMy_Group(List<MyGroupBean> My_Group) {
        this.My_Group = My_Group;
    }

    public static class MyGroupBean {
        /**
         * group_id : 1
         * group_name : JAVA
         * group_created_date : 10/03/2019
         * grp_desc : JAVA Tutorial
         * group_detail_array : [{"file_desc":"OOPS and its application in Java","file_upload_date":"21/03/2019","file_url":"http://jau.icrp.in/Images/E_Learning_File/11/1/1_4.mp4"},{"file_desc":"Control Flow statements","file_upload_date":"20/03/2019","file_url":"http://jau.icrp.in/Images/E_Learning_File/11/1/1_3.zip"},{"file_desc":"Data types and Operators","file_upload_date":"19/03/2019","file_url":"http://jau.icrp.in/Images/E_Learning_File/11/1/1_2.png"}]
         */

        private String group_id;
        private String group_name;
        private String group_created_date;
        private String grp_desc;
        private List<GroupDetailArrayBean> group_detail_array;

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

        public String getGrp_desc() {
            return grp_desc;
        }

        public void setGrp_desc(String grp_desc) {
            this.grp_desc = grp_desc;
        }

        public List<GroupDetailArrayBean> getGroup_detail_array() {
            return group_detail_array;
        }

        public void setGroup_detail_array(List<GroupDetailArrayBean> group_detail_array) {
            this.group_detail_array = group_detail_array;
        }

        public static class GroupDetailArrayBean {
            /**
             * file_desc : OOPS and its application in Java
             * file_upload_date : 21/03/2019
             * file_url : http://jau.icrp.in/Images/E_Learning_File/11/1/1_4.mp4
             */

            private String file_desc;
            private String file_upload_date;
            private String file_url;

            public String getFile_desc() {
                return file_desc;
            }

            public void setFile_desc(String file_desc) {
                this.file_desc = file_desc;
            }

            public String getFile_upload_date() {
                return file_upload_date;
            }

            public void setFile_upload_date(String file_upload_date) {
                this.file_upload_date = file_upload_date;
            }

            public String getFile_url() {
                return file_url;
            }

            public void setFile_url(String file_url) {
                this.file_url = file_url;
            }
        }
    }
}
