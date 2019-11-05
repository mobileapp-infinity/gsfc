package com.infinity.infoway.gsfc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user01 on 7/13/2017.
 */

public class LecturePlanEmployee implements Parcelable
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
    @SerializedName("faculty_name")
    private String faculty_name;

    public  String getfaculty_name()
    {
        return faculty_name;
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

   @SerializedName("details")
    private ArrayList<LectureplanEmployeeSubTopics> details;

    public  ArrayList<LectureplanEmployeeSubTopics> getdetails()
    {
        return details;
    }
   @SerializedName("topic")
    private ArrayList<LectureplanEmployeeSubTopics> topic;

    public  ArrayList<LectureplanEmployeeSubTopics> getTopic()
    {
        return topic;
    }


    protected LecturePlanEmployee(Parcel in)
    {

        Course_Name = in.readString();
        Semester = in.readString();
        division = in.readString();
        Subject=in.readString();
        Lecture_Per_Week = in.readString();
        ref_book_name = in.readString();

    }

    public static final Creator<LecturePlanEmployee> CREATOR = new Creator<LecturePlanEmployee>()
    {
        @Override
        public LecturePlanEmployee createFromParcel(Parcel in)
        {
            return new LecturePlanEmployee(in);
        }

        @Override
        public LecturePlanEmployee[] newArray(int size)
        {
            return new LecturePlanEmployee[size];
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

    }
}
