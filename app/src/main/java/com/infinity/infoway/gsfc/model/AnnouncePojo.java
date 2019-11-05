package com.infinity.infoway.gsfc.model;

import java.util.List;

public class AnnouncePojo
{


    private List<NotyBean> Noty;

    public List<NotyBean> getNoty() {
        return Noty;
    }

    public void setNoty(List<NotyBean> Noty) {
        this.Noty = Noty;
    }

    public static class NotyBean {
        /**
         * notif_no : 126
         * notif_head : Important
         * notif_msg : hello
         * notif_date : 03/04/2019
         * notif_file_path : ../Images/Student_Notification_Images/2138.pdf
         * notif_file : Show Image
         * app_file_path : http://jau.icrp.in/Images/Student_Notification_Images/2138.pdf
         * announced_by :  Admin JAU JAU
         */

        private String notif_no;
        private String notif_head;
        private String notif_msg;
        private String notif_date;
        private String notif_file_path;
        private String notif_file;
        private String app_file_path;
        private String announced_by;



        public String getNotif_no() {
            return notif_no;
        }

        public void setNotif_no(String notif_no) {
            this.notif_no = notif_no;
        }

        public String getNotif_head() {
            return notif_head;
        }

        public void setNotif_head(String notif_head) {
            this.notif_head = notif_head;
        }

        public String getNotif_msg() {
            return notif_msg;
        }

        public void setNotif_msg(String notif_msg) {
            this.notif_msg = notif_msg;
        }

        public String getNotif_date() {
            return notif_date;
        }

        public void setNotif_date(String notif_date) {
            this.notif_date = notif_date;
        }

        public String getNotif_file_path() {
            return notif_file_path;
        }

        public void setNotif_file_path(String notif_file_path) {
            this.notif_file_path = notif_file_path;
        }

        public String getNotif_file() {
            return notif_file;
        }

        public void setNotif_file(String notif_file) {
            this.notif_file = notif_file;
        }

        public String getApp_file_path() {
            return app_file_path;
        }

        public void setApp_file_path(String app_file_path) {
            this.app_file_path = app_file_path;
        }

        public String getAnnounced_by() {
            return announced_by+"";
        }

        public void setAnnounced_by(String announced_by) {
            this.announced_by = announced_by+"";
        }
    }
}
