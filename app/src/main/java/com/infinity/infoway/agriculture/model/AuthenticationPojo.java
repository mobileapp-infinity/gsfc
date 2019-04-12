package com.infinity.infoway.agriculture.model;

public class AuthenticationPojo
{


    /**
     * user_id : AGRI1560
     * user_name : Deven J Patel
     * user_email : djpatel@jau.in
     * user_mobile : 9099094295
     * error_no : 0
     * error_desc : User is Valid.
     */

    private String user_id;
    private String user_name;
    private String user_email;
    private String user_mobile;
    private String error_no;
    private String error_desc;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }
}
