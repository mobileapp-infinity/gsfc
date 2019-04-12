package com.infinity.infoway.agriculture.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class LectureplanEmployeeSubTopics implements Parcelable
{
    @SerializedName("topic_Name")
    public String topic_Name;

    @SerializedName("topic_name")
    public String topic_name;

    public static final Creator<LectureplanEmployeeSubTopics> CREATOR = new Creator<LectureplanEmployeeSubTopics>()
    {
        @Override
        public LectureplanEmployeeSubTopics createFromParcel(Parcel in)
        {
            return new LectureplanEmployeeSubTopics(in);
        }

        @Override
        public LectureplanEmployeeSubTopics[] newArray(int size)
        {
            return new LectureplanEmployeeSubTopics[size];
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

    @SerializedName("methods")
    public ArrayList<Methods> methods;

    public ArrayList<Methods> getmethods() {
        return methods;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(srno);
        dest.writeString(aid);
        dest.writeString(topic_Name);


    }


    protected LectureplanEmployeeSubTopics(Parcel in) {

        srno = in.readString();
        aid = in.readString();
        topic_Name = in.readString();
    }
}
