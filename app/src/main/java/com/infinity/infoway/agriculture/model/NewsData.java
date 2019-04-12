package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user01 on 12/7/2017.
 */

public class NewsData
{

    @SerializedName("group_id")
    private String group_id;
    @SerializedName("group_name")
    private String  group_name;

    @SerializedName("Igm_id")
    private  String Igm_id;
    @SerializedName("Igm_name")
    private  String Igm_name;
    @SerializedName("cn_id")
    private String cn_id;
    @SerializedName("cn_date")
    private String cn_date;
    @SerializedName("cn_subject")
    private String cn_subject;
    @SerializedName("cn_content")
    private String cn_content;
    @SerializedName("cn_photo_1")
    private String cn_photo_1;
    @SerializedName("cn_photo_2")
    private String cn_photo_2;
    @SerializedName("cn_file")
    private String cn_file;
    @SerializedName("cn_is_status")
    private String cn_is_status;
    @SerializedName("ph_1")
    private String ph_1;
    @SerializedName("cn_is_delete")
    private String cn_is_delete;
    @SerializedName("ph_2")
    private String ph_2;
    @SerializedName("cn_group")
    private  String cn_group;

    @SerializedName("group_news_detail")
    private ArrayList<group_news_detail> group_news_details;


    public ArrayList<group_news_detail> getGroup_news_details()
    {
        return group_news_details;
    }


    public  String getGroup_id(){return group_id;}
    public  String getGroup_name(){return  group_name;}

    public  String getIgm_id(){return  Igm_id;}
    public  String getIgm_name(){return  Igm_name;}
    public  String getCn_group()
    {return cn_group;}
    public String getPh_2()
    {
        return ph_2;
    }

    public void setPh_2(String ph_2) {
        this.ph_2 = ph_2;
    }

    public String getCn_is_delete() {
        return cn_is_delete;
    }

    public String getPh_1() {
        return ph_1;
    }

    public void setPh_1(String ph_1) {
        this.ph_1 = ph_1;
    }

    public void setCn_is_delete(String cn_is_delete) {
        this.cn_is_delete = cn_is_delete;
    }

    public String getCn_is_status() {
        return cn_is_status;
    }

    public void setCn_is_status(String cn_is_status) {
        this.cn_is_status = cn_is_status;
    }

    public String getCn_id() {
        return cn_id;
    }

    public void setCn_id(String lect_name) {
        this.cn_id = cn_id;
    }

    public String getCn_date() {
        return cn_date;
    }

    public void setCn_date(String cn_date) {
        this.cn_date = cn_date;
    }

    public String getCn_subject() {
        return cn_subject;
    }

    public void setCn_subject(String cn_subject) {
        this.cn_subject = cn_subject;
    }

    public String getCn_content() {
        return cn_content;
    }

    public void setCn_content(String cn_content) {
        this.cn_content = cn_content;
    }

    public String getCn_photo_1() {
        return cn_photo_1;
    }

    public void setCn_photo_1(String cn_photo_1) {
        this.cn_photo_1 = cn_photo_1;
    }

    public String getCn_photo_2() {
        return cn_photo_2;
    }

    public void setCn_photo_2(String cn_photo_2) {
        this.cn_photo_2 = cn_photo_2;
    }

    public String getCn_file() {
        return cn_file;
    }

    public void setCn_file(String cn_file) {
        this.cn_file = cn_file;
    }

}
