package com.infinity.infoway.gsfc.HrAppAPI;

import android.app.Application;

/**
 * Created by ADMIN on 26-04-2018.
 */

public class URLS extends Application
{

//    public static String Base_URl = "http://rku.ierp.co.in/IerpHR.asmx/";


    // ****** testing url for demo *********
//    public static String Base_URl = "http://demo1.iipl.info/ierphr.asmx/";


    //********** live rk url **********
    public static String Base_URl = "http://gsfc.ierp.co.in/ierphr.asmx/";
//    public static String Base_URl = "http://gsfc.ierp.co.in.asmx/";

    public static  int TIME_TILL_DISABLE_BTN = 2000;

    public static String get_multi_compny_permission_user = Base_URl + "get_multi_compny_permission_user?";

    //********pagination data(only 20 rows per page)******
    public static String RowsPerPage = "20";

    public static String get_user_compny_permission_list = Base_URl + "get_user_compny_permission_list?";


    // ************dash-board detail API *********
    public static String Get_Dashboard_detail = Base_URl + "Get_Dashboard_detail?";


    //******* check login of user *****************
    public static String LoginCheck = Base_URl + "LoginCheck?";

    //********* display leave balance of user ***************
    public static String Get_User_LeaveBalance = Base_URl + "Get_User_LeaveBalance?";

    //************* display employee punch detail *********
    public static String Get_employee_punch_detail = Base_URl + "Get_employee_punch_detail?";

    //************* display leave listing *********
    public static String Get_leave_appliation_list = Base_URl + "Get_leave_appliation_list?";


    //*********** Bind type , reason and note *************
    public static String Get_leave_type_and_reason_and_note = Base_URl + "Get_leave_type_and_reason_and_note?";


    // **************** Miss Punch request listing ***********************
    public static String Get_miss_punch_request_list = Base_URl + "Get_miss_punch_request_list?";


    // ****************** Miss Punch Approval listing *******************
    public static String Get_miss_pucn_approve_list = Base_URl + "Get_miss_pucn_approve_list?";


    // ****************** leave approval listing ************************
    public static String Get_leave_approve_list = Base_URl + "Get_leave_approve_list?";

    //************ leave detail of edit and update *******************
    public static String Get_leave_detail = Base_URl + "Get_leave_detail?";

    //************** approve leave application ************
    public static String employee_leave_application_approval = Base_URl + "employee_leave_application_approval?";

    public static String employee_leave_application_approval_mail = Base_URl + "employee_leave_application_approval_mail?";

    public static String employee_leave_application_reject = Base_URl + "employee_leave_application_reject?";

    public static String employee_leave_application_reject_mail = Base_URl + "employee_leave_application_reject_mail?";

    public static String employee_leave_application_mst_delete = Base_URl + "employee_leave_application_mst_delete?";

    public static String Apply_Cancel_Leave_application = Base_URl + "Apply_Cancel_Leave_application?";

    public static String employee_cancel_leave_application_approve_mail = Base_URl + "employee_cancel_leave_application_approve_mail?";

    public static String Get_apply_cancel_leave_appliation_list = Base_URl + "Get_apply_cancel_leave_appliation_list?";

    public static String employee_cancel_leave_application_approve = Base_URl + "employee_cancel_leave_application_approve?";

    public static String Get_cancel_leave_appliation_approve_list = Base_URl + "Get_cancel_leave_appliation_approve_list?";

    public static String Apply_Cancel_Leave_application_mail = Base_URl + "Apply_Cancel_Leave_application_mail?";

    public static String employee_cancel_leave_application_reject = Base_URl + "employee_cancel_leave_application_reject ?";

    public static String employee_cancel_leave_application_reject_mail = Base_URl + "employee_cancel_leave_application_reject_mail?";

    public static String Employee_miss_punch_request_reject = Base_URl + "Employee_miss_punch_request_reject?";

    public static String Employee_miss_punch_request_mst_update = Base_URl + "Employee_miss_punch_request_mst_update?";

    public static String Employee_miss_punch_request_approved = Base_URl + "Employee_miss_punch_request_approved?";

    public static String Employee_miss_punch_request_mst_delete = Base_URl + "Employee_miss_punch_request_mst_delete?";

    public static String Get_miss_punch_detail = Base_URl + "Get_miss_punch_detail?";

    public static String Get_Employee_Leave_Days = Base_URl + "Get_Employee_Leave_Days?";

    public static String Get_Employee_Leave_balance = Base_URl + "Get_Employee_Leave_balance?";

    public static String Employee_leave_application_insert = Base_URl + "Employee_leave_application_insert?";

    public static String Get_employee_inout_time = Base_URl + "Get_employee_inout_time?";

    public static String Get_employee_miss_puch_date_wise_inout_time = Base_URl + "Get_employee_miss_puch_date_wise_inout_time?";

    public static String Employee_miss_punch_request_mst_insert = Base_URl + "Employee_miss_punch_request_mst_insert?";

    //*******************for attendance detail *********************
    public static String Get_employee_attendance_report_detail = Base_URl + "Get_employee_attendance_report_detail?";

    //************************* summery of attendance ***************************
    public static String Get_employee_attendance_report_summary = Base_URl + "Get_employee_attendance_report_summary?";

    public static String Employee_Forgot_password = Base_URl + "Employee_Forgot_password?";

    public static String Employee_Change_password = Base_URl + "Employee_Change_password?";

    public static String Get_employee_salary_slip = Base_URl + "Get_employee_salary_slip?";

    public static String Get_Employee_salary_slip_download = Base_URl + "Get_Employee_salary_slip_download?";

    public static String compensatory_leave_approve_list = Base_URl + "compensatory_leave_approve_list?";

    public static String compensatory_leave_approve_detail = Base_URl + "compensatory_leave_approve_detail?";

    public static String compensatory_leave_approve = Base_URl + "compensatory_leave_approve?";

    public static String Insert_mobile_tocken = Base_URl + "Insert_mobile_tocken?";

    public static String Get_Today_in_out_time = Base_URl + "Get_Today_in_out_time?";

    public static String Get_app_version = Base_URl + "Get_app_version?";

    public static String Get_miss_punch_hours_calculation = Base_URl + "Get_miss_punch_hours_calculation?";

    public static String Get_Other_Statistics = Base_URl + "Get_Other_Statistics?";

}
