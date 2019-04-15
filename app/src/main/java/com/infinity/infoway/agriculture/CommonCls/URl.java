package com.infinity.infoway.agriculture.CommonCls;

import android.app.Application;

public class URl extends Application
{



    public static String SIMS_JAU_URL = "http://sims.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";
//    public static String SIMS_JAU_URL = "http://jau.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";

    public static  String SiMS_URL_FOR_EMPLOYEE_LOGIN = "http://online.jau.in/api/";

    public static  String Authenticate_user_by_validation = SiMS_URL_FOR_EMPLOYEE_LOGIN +"jau_credentials.php?";


  //  public static String Main_APP_Url = "http://icampus.biz/API_Student_Panel_JSON_Icampus.asmx/";

    public static String faculty_bind_api = SIMS_JAU_URL + "Get_Pending_Attendance_Detail_Employee_Wise_API?";


    public static String Display_batch_student_wise = SIMS_JAU_URL + "Get_Batch_Detail_For_Attendance?";

    public static String StudentsDisplay_fill_attendance = SIMS_JAU_URL + "Get_Student_Detail_For_Attendance?";


    public static String Subject_wise_unit_Detail = SIMS_JAU_URL + "Get_Subject_Wise_Unit_Detail?";


    public static String Get_Teaching_Aid_Detail_API = SIMS_JAU_URL + "Get_Teaching_Aid_Detail_API?";

    public static String Get_Teaching_Method_Details_API = SIMS_JAU_URL + "Get_Teaching_Method_Details_API?";

    public static String Insert_isrp_class_wise_attendance_API = SIMS_JAU_URL + "Insert_isrp_class_wise_attendance_API?";


    public static String UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API = SIMS_JAU_URL + "UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API?";

    public static String Absent_student_record_save = SIMS_JAU_URL + "Insert_Absent_Student_Attendance_By_Alternate_Method?";

    public static String Present_student_record_save = SIMS_JAU_URL + "Insert_Present_Student_Attendance_By_Alternate_Method?";



    public static String Login_user_by_email_Api = SIMS_JAU_URL + "login_user_with_email_api?";
}
