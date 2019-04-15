package com.infinity.infoway.agriculture.rest;

import com.infinity.infoway.agriculture.model.ChartDataRemainingAttendance;
import com.infinity.infoway.agriculture.model.EmployeeAttendanceResponse;
import com.infinity.infoway.agriculture.model.ExamDetailPOJO;
import com.infinity.infoway.agriculture.model.ExamTTPojo;
import com.infinity.infoway.agriculture.model.FcmResponse;
import com.infinity.infoway.agriculture.model.Fee_Receipt_List;
import com.infinity.infoway.agriculture.model.Fees;
import com.infinity.infoway.agriculture.model.Holiday_next;
import com.infinity.infoway.agriculture.model.LeaveResponse;
import com.infinity.infoway.agriculture.model.LecturePlanEmployee;
import com.infinity.infoway.agriculture.model.LessionResponse;
import com.infinity.infoway.agriculture.model.LoginResponse;
import com.infinity.infoway.agriculture.model.NewsData;
import com.infinity.infoway.agriculture.model.NotificationResponse;
import com.infinity.infoway.agriculture.model.ProfileResponse;
import com.infinity.infoway.agriculture.model.PunchData;
import com.infinity.infoway.agriculture.model.RemainingAttResponse;
import com.infinity.infoway.agriculture.model.Response_Activity;
import com.infinity.infoway.agriculture.model.StudentAttendance;
import com.infinity.infoway.agriculture.model.SyllabusResponse;
import com.infinity.infoway.agriculture.model.TimeTableResponse;
import com.infinity.infoway.agriculture.model.activity;
import com.infinity.infoway.agriculture.model.assignment_response;
import com.infinity.infoway.agriculture.model.birthdata;
import com.infinity.infoway.agriculture.model.change_psw;
import com.infinity.infoway.agriculture.model.collage_list;
import com.infinity.infoway.agriculture.model.dir_stud_attendance;
import com.infinity.infoway.agriculture.model.hod_list;
import com.infinity.infoway.agriculture.model.homework_response;
import com.infinity.infoway.agriculture.model.leave_data;
import com.infinity.infoway.agriculture.model.new_lect_plan;
import com.infinity.infoway.agriculture.model.pendingfeesmodel;
import com.infinity.infoway.agriculture.model.result_response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface ApiInterface {
    @GET("student_login_check_api_for_jau")
    Call<LoginResponse>student_login_for_sims (@QueryMap Map<String, String> params);



    @GET("student_login_check_api")
    Call<LoginResponse> StudentLogin(@QueryMap Map<String, String> params);

    @GET("login_user_new_api")
    Call<LoginResponse> EmployeeLogin(@QueryMap Map<String, String> params);

    @GET("get_student_profile_detail_atmiya")
    Call<ProfileResponse> getStudentProfile(@QueryMap Map<String, String> params);

    @GET("employee_profile_api")
    Call<List<ProfileResponse>> getEmployeeProfile(@Query("emp_id") String emp_id);

    @GET("get_attendance_information_employee_wise_1")
    Call<ArrayList<EmployeeAttendanceResponse>> getEmployeeAttendance(@QueryMap Map<String, String> params);

    @GET("get_employee_timetable_display")
    Call<ArrayList<TimeTableResponse>> getEmployeeTimetable(@Query("emp_id") String emp_id, @Query("year_id") String year_id);

    @GET("get_student_timetable_api_lopgin")
    Call<ArrayList<TimeTableResponse>> getStudentTimetable(@QueryMap Map<String, String> params);

    @GET("check_version_api")
    Call<ProfileResponse> checkversionupdate(@Query("version") int version);

    @GET("get_student_forget_password_detail")
    Call<LoginResponse> forgotpassword(@Query("email") String version);

    @GET("Get_Employee_Holiday_Details")
    Call<ArrayList<LeaveResponse>> getemployeeleave(@Query("emp_id") String emp_id, @Query("year_id") String year_id);

    @GET("Get_Student_Current_sem_syllabus")
    Call<ArrayList<SyllabusResponse>> getsyllabus(@Query("stud_id") String stud_id);

    @GET("Get_Sem_Wise_Lecture_planning_For_Student")
    Call<ArrayList<LessionResponse>> getlessionresponse(@Query("stud_id") String stud_id);

    @GET("Fees_API")
    Call<Fees> getstudentfee(@Query("stud_id") String stud_id);

    @GET("get_student_attendance_api")
    Call<ArrayList<TimeTableResponse>> getStudentAttendance(@QueryMap Map<String, String> params);

    @GET("Get_Emp_Wise_Approve_Lecture_Planning_Api")
    Call<ArrayList<LecturePlanEmployee>> getEmpLecturePlan(@Query("emp_id") String emp_id);

    @GET("Student_Lecture_Plan_API")
    Call<ArrayList<LecturePlanEmployee>> getStudentLecturePlan(@Query("stud_id") String emp_id);

    @GET("get_student_attendance_api")
    Call<ArrayList<StudentAttendance>> getStudAttendance(@QueryMap Map<String, String> params);

    @GET("Update_Isrp_Student_FCM_Id")
    Call<FcmResponse> getfcmid(@QueryMap Map<String, String> maps);

    @GET("Update_Isrp_employee_FCM_Id")
    Call<FcmResponse> getempfcmid(@QueryMap Map<String, String> maps);

    @GET("Remaining_attendance_API")
    Call<ArrayList<RemainingAttResponse>> getRemainingattendance(@QueryMap Map<String, String> maps);

    @GET("Get_Image_URL")
    Call<LoginResponse> getSliderImages(@Query("url") String url,@Query("institute_id") String institute_id);

    @GET("get_notification")
    Call<ArrayList<NotificationResponse>> getNotification(@Query("notification_for") String notification);

    @GET("get_notification")
    Call<LoginResponse> FeedBack(@Query("name") String name, @Query("email") String email, @Query("mobile") String mobile, @Query("website") String website, @Query("message") String message);

    @GET("paynow")
    Call<Fees> paynow(@Query("school_id") String school_id, @Query("addmission_no") String email, @Query("amount") String amount, @Query("stud_name") String stud_name, @Query("Stud_Order_id") String Stud_Order_id);

    @GET("Print_Fee_Receipt")
    @Streaming
    Call<Fees> Print_Fee_Receipt(@Query("stud_id") String stud_id);

    @GET("Print_Fee_Receipt")
    @Streaming
    Call<Fees> Download_Fee_Receipt(@Query("stud_id") String stud_id, @Query("Receipt_no") String receipt_no);

    @GET("Fee_Receipt_List")
    Call<ArrayList<Fee_Receipt_List>> getreceiptlist(@QueryMap Map<String, String> maps);

    @GET("Get_Campus_News_Master")
    @Streaming
    Call<ArrayList<NewsData>> get_news();

    @POST("Get_Leave_Balance")
    @Streaming
    Call<ArrayList<leave_data>> get_leave(@Query("KEY") String KEY, @Query("Contact_ID") String Contact_ID, @Query("Year") int year);

    @POST("Get_Punch_Data")
    @Streaming
    Call<ArrayList<PunchData>> get_emp_punch(@QueryMap Map<String, String> params);

    @GET("Get_Next_Upcoming_Holiday")
    @Streaming
    Call<ArrayList<Holiday_next>> get_next_holiday(@Query("college_id") String college_id);

    @POST("PunchData/Get_Punch_Data")
    @Streaming
    Call<ArrayList<PunchData>> get_Punch(@QueryMap Map<String, String> params);

    @GET("Get_All_Colleges_List_API")
    @Streaming
    Call<ArrayList<collage_list>> get_collage_list();

    @GET("Get_College_wise_HOD_Principal_Details_API")
    @Streaming
    Call<ArrayList<hod_list>> get_hod_principal_list(@Query("college_id") String college_id);

    @GET("Get_Remaining_att_ChartData")
    @Streaming
    Call<ArrayList<ChartDataRemainingAttendance>> get_chart_data(@Query("txt_date") String txt_date);

    @GET("Get_Employee_Todays_Birthday")
    @Streaming
    Call<ArrayList<birthdata>> get_today_birthday();

    @GET("Get_All_Colleges_and_College_Wise_HOD_Principal_Details")
    @Streaming
    Call<ArrayList<hod_list>> get_collage_hod_list();

    @GET("get_director_student_attendance_ratio")
    @Streaming
    Call<dir_stud_attendance> get_dir_stud_attendance(@Query("date") String date);

    @GET("Get_Emp_Wise_Lecture_Planning_Api")
    @Streaming
    Call<ArrayList<new_lect_plan>> get_new_lect_plan(@Query("emp_id") String emp_id);

    @GET("Student_Lesson_Planning_API")
    @Streaming
    Call<ArrayList<new_lect_plan>> get_student_new_lession_plan(@Query("stud_id") String stud_id);

    @GET("Get_Campus_News_Detail_API")
    @Streaming
    Call<ArrayList<NewsData>> get_group_news(@Query("group_id") String group_id);

    @GET("Get_Campus_News_Detail_API")
    @Streaming
    Call<ArrayList<NewsData>> get_group(@Query("institute_id")String institute_id);

    @GET("Get_Student_Wise_Division_Wise_Student_Activity_API")
    @Streaming
    Call<ArrayList<activity>> get_student_activity_list(@Query("stud_id") String stud_id);

    @GET("Get_Student_Wise_Division_Wise_Student_Activity_API")
    @Streaming
    Call<List<Response_Activity>> get_student_activity_list_temp(@Query("stud_id") String stud_id,@Query("url")String url);


    @GET("get_homework_and_content_delivered_API")
    @Streaming
    Call<ArrayList<homework_response>> get_homework_content_delivered(@QueryMap Map<String, String> params);


    @GET("get_mid_sem_student_wise_exam_mark_API")
    @Streaming
    Call<List<result_response>> get_result_student(@QueryMap Map<String, String> params);

    @GET("Get_Student_Assignment_Detail")
    @Streaming
    Call<ArrayList<assignment_response>> get_student_assignment_detail(@Query("stud_id") String stud_id, @Query("year_id") String year_id);


    @GET("get_notification_new")
    @Streaming
    Call<ArrayList<NotificationResponse>> get_stud_emp_notification(@Query("notification_for") String notification_for, @Query("notif_college_id") String notif_college_id, @Query("notif_dept_id") String notif_course_id, @Query("notif_course_id") String notif_dept_id, @Query("notif_sem_id") String notif_sem_id,@Query("institute_id") String institute_id);


    @GET("Print_Axis_Payslip")
    @Streaming
    Call<pendingfeesmodel> get_pending_fees(@Query("stud_id") String stud_id);


    @GET("application_change_password")
    @Streaming
    Call<ArrayList<change_psw>> get_change_password_applicant(@QueryMap Map<String, String> parameters);

    @GET("Paynow_Axis")
    @Streaming
    Call<Fees> pay_fees_with_axis(@QueryMap Map<String, String> parameters);

    @GET("get_student_mid_exam_display")
    @Streaming
    Call<ArrayList<ExamTTPojo>> get_exam_time_table_display(@Query("stud_id") String stud_id, @Query("year_id") String year_id);

    @GET("Get_Mid_Exam_Timetable_exam_wise")
    @Streaming
    Call<ArrayList<ExamDetailPOJO>> get_exam_time_table_display_detail(@Query("exam_id") String exam_id);



    @GET("student_login_data_from_stud_id")
    @Streaming
    Call<LoginResponse>get_student_data_from_stud_id(@Query("stud_id")String stud_id);


    @GET("login_user_new_api_from_emp_id")
    @Streaming
    Call<LoginResponse>login_user_new_api_from_emp_id(@Query("emp_id")String emp_id);
}
