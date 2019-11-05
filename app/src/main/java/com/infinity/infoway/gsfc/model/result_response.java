package com.infinity.infoway.gsfc.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class result_response
{


    @SerializedName("main_ex_id")
    @Expose
    private String main_ex_id;

    public  String getMain_ex_id()
    {
        return main_ex_id;
    }


    @SerializedName("main_ex_name")
    @Expose
    private String main_ex_name;

    public  String getMain_ex_name()
    {
        return main_ex_name;
    }

    @SerializedName("main_re_date")
    @Expose
    private String main_re_date;

    public  String getMain_re_date()
    {
        return main_re_date;
    }

    @SerializedName("Total")
    @Expose
    private String Total;

    public  String getTotal()
    {
        return Total;
    }

    @SerializedName("Subjects_array")
    @Expose
    private List<Subjects_array> subjectsarrays = null;

    public List<Subjects_array> getSubjectsarrays()
    {
        return subjectsarrays;
    }


    public class Subjects_array
    {



        @SerializedName("sub_name")
        @Expose
        private String sub_name;
        public String getSub_name()
        {
            return sub_name;
        }
        public void setSub_name(String activityFile)
        {
            this.sub_name = sub_name;
        }


        @SerializedName("sub_tot_mark")
        @Expose
        private String sub_tot_mark;

        public String getSub_tot_mark()
        {
            return sub_tot_mark;
        }

        @SerializedName("sub_obt_mark")
        @Expose
        private String sub_obt_mark;

        public String getSub_obt_mark()
        {
            return sub_obt_mark;
        }


        @SerializedName("sub_weight")
        @Expose
        private String sub_weight;

        public String getSub_weight()
        {
            return sub_weight;
        }
    }





}
