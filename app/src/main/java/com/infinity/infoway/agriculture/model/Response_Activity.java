package com.infinity.infoway.agriculture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_Activity
{
    @SerializedName("activity_date")
    @Expose
    private String activityDate;
    @SerializedName("da_description")
    @Expose
    private String daDescription;
    @SerializedName("Activity_File")
    @Expose
    private List<ActivityFile> activityFile = null;

    public String getActivityDate()
    {
        return activityDate;
    }

    public void setActivityDate(String activityDate)
    {
        this.activityDate = activityDate;
    }

    public String getDaDescription()
    {
        return daDescription;
    }

    public void setDaDescription(String daDescription)
    {
        this.daDescription = daDescription;
    }

    public List<ActivityFile> getActivityFile()
    {
        return activityFile;
    }

    public void setActivityFile(List<ActivityFile> activityFile)
    {
        this.activityFile = activityFile;
    }

    public class ActivityFile
    {

        @SerializedName("Activity_file")
        @Expose
        private String activityFile;

        public String getActivityFile()
        {
            return activityFile;
        }

        public void setActivityFile(String activityFile)
        {
            this.activityFile = activityFile;
        }

    }


}
