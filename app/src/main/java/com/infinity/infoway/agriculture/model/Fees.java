package com.infinity.infoway.agriculture.model;
import com.google.gson.annotations.SerializedName;


public class Fees
{

    @SerializedName("Student_ID")
    public String Student_ID;

    public  String getStudent_ID()
    {
        return Student_ID;
    }

    @SerializedName("Fee_Circular")
    public String Fee_Circular;

    public  String getFee_Circular()
    {
        return Fee_Circular;
    }
    @SerializedName("Fee_Date")
    public String Fee_Date;

    public  String getFee_Date()
    {
        return Fee_Date;
    }
    @SerializedName("Fee_Type")
    public String Fee_Type;

    public  String getFee_Type()
    {
        return Fee_Type;
    }

    @SerializedName("Total_Fee")
    public String Total_Fee;

    public  String getTotal_Fee()
    {
        return Total_Fee;
    }
    @SerializedName("Paid_Fee")
    public String Paid_Fee;

    public  String getPaid_Fee()
    {
        return Paid_Fee;
    }
    @SerializedName("Pending_Fee")
    public String Pending_Fee;

    public  String getPending_Fee()
    {
        return Pending_Fee;
    }
    @SerializedName("Pending_Fee_Refund")
    public String Pending_Fee_Refund;

    public  String getPending_Fee_Refund()
    {
        return Pending_Fee_Refund;
    }
    @SerializedName("Fee_Circular_ID")
    public String Fee_Circular_ID;

    public  String getFee_Circular_ID()
    {
        return Fee_Circular_ID;
    }

    @SerializedName("Due_Date")
    public String Due_Date;

    public  String getDue_Date()
    {
        return Due_Date;
    }

    @SerializedName("status")
    public String status;

    public  String getstatus()
    {
        return status;
    }

    @SerializedName("response")
    public String response;

    public  String getresponse()
    {
        return response;
    }

    @SerializedName("Filename")
    public String Filename;

    public  String getFilename()
    {
        return Filename;
    }

    @SerializedName("base64string")
    public String bytearr;

    public  String getbytearr()
    {
        return bytearr;
    }

}
