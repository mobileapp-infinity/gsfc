package com.infinity.infoway.gsfc.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;





public class newlectplandetail implements Parcelable
{
    @SerializedName("topic_Name")
    public String topic_Name;

    @SerializedName("topic_name")
    public String topic_name;

    public static final Creator<newlectplandetail> CREATOR = new Creator<newlectplandetail>()
    {
        @Override
        public newlectplandetail createFromParcel(Parcel in)
        {
            return new newlectplandetail(in);
        }

        @Override
        public newlectplandetail[] newArray(int size)
        {
            return new newlectplandetail[size];
        }
    };


    public String gettopic_Name()
    {
        return topic_Name;

    }

    public String gettopic_name()
    {
        return topic_name;

    }


    @SerializedName("sr_no")
    private String srno;


    public String getSrno() {
        return srno;
    }


    @SerializedName("aid")
    public String aid;

    public String getaid() {
        return aid;
    }

    @SerializedName("lp_sub_topic")
    public ArrayList<lp_sub_topic> lp_sub_topic;

    public ArrayList<lp_sub_topic> getLp_sub_topic() {
        return lp_sub_topic;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeString(srno);
        dest.writeString(aid);
        dest.writeString(topic_Name);


    }


    protected newlectplandetail(Parcel in)
    {

        srno = in.readString();
        aid = in.readString();
        topic_Name = in.readString();
    }
}

