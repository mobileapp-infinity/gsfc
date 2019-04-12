package com.infinity.infoway.agriculture.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class new_lect_plan implements  Parcelable
{
        @SerializedName("Course_Name")
        private String Course_Name;

        public  String getCourse_Name()
        {
            return Course_Name;
        }

        @SerializedName("Subject_name")
        private String Subject_name;

        public  String getSubject_name()
        {
            return Subject_name;
        }


        @SerializedName("Semester")
        private String Semester;

        public  String getSemester()
        {
            return Semester;
        }

        @SerializedName("division")
        private String division;

        public  String getdivision()
        {
            return division;
        }

        @SerializedName("Subject")
        private String Subject;

        public  String getSubject()
        {
            return Subject;
        }

        @SerializedName("Lecture_Per_Week")
        private String Lecture_Per_Week;

        public  String getLecture_Per_Week()
        {
            return Lecture_Per_Week;
        }

        @SerializedName("ref_book_name")
        private String ref_book_name;

        public  String getref_book_name()
        {
            return ref_book_name;
        }


    @SerializedName("faculty_name")
    private  String faculty_name;

    public String getFaculty_name()
    {
        return faculty_name;
    }
        @SerializedName("lp_details")
        private ArrayList<newlectplandetail> lp_details;

        public  ArrayList<newlectplandetail> getdetails()
        {
            return lp_details;
        }
//        @SerializedName("lp_sub_topic")
//        private ArrayList<LectureplanEmployeeSubTopics> lp_sub_topic;
//
//        public  ArrayList<LectureplanEmployeeSubTopics> getTopic()
//        {
//            return lp_sub_topic;
//        }


        protected new_lect_plan (Parcel in)
        {

            Course_Name = in.readString();
            Semester = in.readString();
            division = in.readString();
            Subject=in.readString();
            Lecture_Per_Week = in.readString();
            ref_book_name = in.readString();
            faculty_name=in.readString();
        }

        public static final Creator<com.infinity.infoway.agriculture.model.new_lect_plan> CREATOR = new Creator<com.infinity.infoway.agriculture.model.new_lect_plan>()
        {
            @Override
            public com.infinity.infoway.agriculture.model.new_lect_plan createFromParcel(Parcel in)
            {
                return new com.infinity.infoway.agriculture.model.new_lect_plan(in);
            }

            @Override
            public com.infinity.infoway.agriculture.model.new_lect_plan[] newArray(int size)
            {
                return new com.infinity.infoway.agriculture.model.new_lect_plan[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeString(Course_Name);
            dest.writeString(Semester);
            dest.writeString(division);
            dest.writeString(Subject);
            dest.writeString(Lecture_Per_Week);
            dest.writeString(ref_book_name);
            dest.writeString(faculty_name);

        }


}
