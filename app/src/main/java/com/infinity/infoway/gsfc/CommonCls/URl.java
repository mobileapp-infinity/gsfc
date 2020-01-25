package com.infinity.infoway.gsfc.CommonCls;

import android.app.Application;

public class URl extends Application
{


    //************** SIMS URL **************
//    public static String SIMS_JAU_URL = "http://sims.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";

    //************** GSFC URL **************
    public static String GSFC_JAU_URL = "https://admission.gsfcuniversity.ac.in//API_Student_Panel_JSON_Icampus.asmx/";

    //************** JAU URL **************
//    public static String SIMS_JAU_URL = "http://jau.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";


    // **************for authenticate employee of SIMS Nirali 5aug2019 *************
    public static String SiMS_URL_FOR_EMPLOYEE_LOGIN = "http://online.jau.in/api/";

    public static String Authenticate_user_by_validation = SiMS_URL_FOR_EMPLOYEE_LOGIN + "jau_credentials.php?";

    //*********** for announcement student and employee ****************
    public static String get_notification_new = GSFC_JAU_URL + "get_notification_new?";
    public static String Login_user_by_email_Api = GSFC_JAU_URL + "login_user_with_email_api?";
    //  public static String Main_APP_Url = "http://icampus.biz/API_Student_Panel_JSON_Icampus.asmx/";


    //*************for pending and fill attendance employee*************************

    public static String faculty_bind_api = GSFC_JAU_URL + "Get_Pending_Attendance_Detail_Employee_Wise_API?";


    public static String Display_batch_student_wise = GSFC_JAU_URL + "Get_Batch_Detail_For_Attendance?";

    public static String StudentsDisplay_fill_attendance = GSFC_JAU_URL + "Get_Student_Detail_For_Attendance?";


    public static String Subject_wise_unit_Detail = GSFC_JAU_URL + "Get_Subject_Wise_Unit_Detail?";


    public static String Get_Teaching_Aid_Detail_API = GSFC_JAU_URL + "Get_Teaching_Aid_Detail_API?";

    public static String Get_Teaching_Method_Details_API = GSFC_JAU_URL + "Get_Teaching_Method_Details_API?";

    public static String Insert_isrp_class_wise_attendance_API = GSFC_JAU_URL + "Insert_isrp_class_wise_attendance_API?";


    public static String UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API = GSFC_JAU_URL + "UPDATE_DAILY_LECTURE_PLANING_WISE_ATT_STATUS_API?";

    public static String Absent_student_record_save = GSFC_JAU_URL + "Insert_Absent_Student_Attendance_By_Alternate_Method?";

    public static String Present_student_record_save = GSFC_JAU_URL + "Insert_Present_Student_Attendance_By_Alternate_Method?";

    public static String Get_My_Group_Master_Detail_student_Wise_API = GSFC_JAU_URL + "Get_My_Group_Master_Detail_student_Wise_API?";
    public static String Insert_E_Learning_Group_Wise_Student_Master_API = GSFC_JAU_URL + "Insert_E_Learning_Group_Wise_Student_Master_API?";


    //for e-lerningggggggggggg student
    public static String Check_Exist_E_Learning_Group_Wise_Student_Master_API = GSFC_JAU_URL + "Check_Exist_E_Learning_Group_Wise_Student_Master_API?";

    public static String Get_student_wise_group_detail_for_enroll_to_group_API = GSFC_JAU_URL + "Get_student_wise_group_detail_for_enroll_to_group_API?";

    public static String Get_group_wise_e_learning_file_type_wise_file_master_detail_API = GSFC_JAU_URL + "Get_group_wise_e_learning_file_type_wise_file_master_detail_API?";

    public static String Get_Lesson_Planning_Topic_Detail_Subject_and_Faculty_Wise_API = GSFC_JAU_URL + "Get_Lesson_Planning_Topic_Detail_Subject_and_Faculty_Wise_API?";

    public static String get_methods_from_api = GSFC_JAU_URL + "Get_Lesson_Planning_Topic_Detail_Subject_and_Faculty_Wise_Topic_Wise_API?";




    /*for leave Pragna */ //for leavee student

    public static String get_leave_type_file_upload = GSFC_JAU_URL + "get_leave_type_file_upload?";

    public static String get_leave_type_institute_wise_for_student = GSFC_JAU_URL + "get_leave_type_institute_wise_for_student?";

    public static String insert_student_leave_API = GSFC_JAU_URL + "insert_student_leave_API?";

    public static String Get_Date_Wise_Lecture_List_for_student_leave = GSFC_JAU_URL + "Get_Date_Wise_Lecture_List_for_student_leave?";

    public static String Upload_Student_Leave_Document = GSFC_JAU_URL + "Upload_Student_Leave_Document?";

    public static String get_student_leave_application_data_API = GSFC_JAU_URL + "get_student_leave_application_data_API?";

    public static String Get_E_Learning_File_Master_Detail_Group_Wise_API = GSFC_JAU_URL + "Get_E_Learning_File_Master_Detail_Group_Wise_API?";
    //assignment employee

    public static String get_employee_wise_student_assignment_API = GSFC_JAU_URL + "get_employee_wise_student_assignment_API?";


    //************ E_Learning employee **********//for employee


    public static String Get_E_Learning_File_Master_Detail_Group_Wise_Employee_API = GSFC_JAU_URL + "Get_E_Learning_File_Master_Detail_Group_Wise_Employee_API?";

    public static String search_e_learning_group_API = GSFC_JAU_URL + "search_e_learning_group_API?";

    public static String delete_e_learning_group_API = GSFC_JAU_URL + "delete_e_learning_group_API?";

    public static String insert_e_learning_group_API = GSFC_JAU_URL + "insert_e_learning_group_API?";

    public static String update_e_learning_group_API = GSFC_JAU_URL + "update_e_learning_group_API?";

    public static String check_exists_e_learning_group_API = GSFC_JAU_URL + "check_exists_e_learning_group_API?";

    public static String check_exists_e_learning_group_by_id_API = GSFC_JAU_URL + "check_exists_e_learning_group_by_id_API?";

    public static String Delete_E_Learning_File_Master_API = GSFC_JAU_URL + "Delete_E_Learning_File_Master_API?";

    public static String get_e_learning_group_name_API = GSFC_JAU_URL + "get_e_learning_group_name_API?";

    public static String Insert_E_Learning_File_Master_API = GSFC_JAU_URL + "Insert_E_Learning_File_Master_API?";

    public static String Update_E_Learning_File_Master_File_API = GSFC_JAU_URL + "Update_E_Learning_File_Master_File_API?";

    public static String Update_E_Learning_File_Master_API = GSFC_JAU_URL + "Update_E_Learning_File_Master_API?";

    public static String Get_File_Wise_E_Learning_File_Master_Detail_API = GSFC_JAU_URL + "Get_File_Wise_E_Learning_File_Master_Detail_API?";

    public static String get_e_learning_group_API = GSFC_JAU_URL + "get_e_learning_group_API?";


    //*********************** update in-out status of student *********************
    public static String InsertStudent_Attendance_In_Time_API = GSFC_JAU_URL + "InsertStudent_Attendance_In_Time_API?";

    public static String Internship_Attendance_count_api = GSFC_JAU_URL + "Internship_Attendance_count_api?";

    public static String Bind_assign_intenship_to_student_API = GSFC_JAU_URL + "Bind_assign_intenship_to_student_API?";

    public static String Get_intership_student_inout_time_API = GSFC_JAU_URL + "Get_intership_student_inout_time_API?";


    //************************ Internship work report api ****************************

    public static String Insert_Student_Work_Report_API = GSFC_JAU_URL + "Insert_Student_Work_Report_API?";

    public static String Get_grd_work_report_API = GSFC_JAU_URL + "Get_grd_work_report_API?";

    //**** check leave exist in lecture or not ****** nirali 01 nov 2019 ****
    public static String Check_Student_Leave_Exist_API = GSFC_JAU_URL + "Check_Student_Leave_Exist_API?";

    public static String get_student_attendance_api = GSFC_JAU_URL + "get_student_attendance_api?";


    public static String Get_Attendance_Method_Configuration_For_Fill_Attendance = GSFC_JAU_URL + "Get_Attendance_Method_Configuration_For_Fill_Attendance?";

    public static String Check_Attendance_Exists_Before_Fill_Attendance = GSFC_JAU_URL + "Check_Attendance_Exists_Before_Fill_Attendance?";






}
