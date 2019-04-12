package com.infinity.infoway.agriculture.rest;

import android.util.Log;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{

    private static final String BASE_URL = "http://cms.atmiya.edu.in/API_Student_panel_json.asmx/";
    private static final String BASE_URL_txt = "http://cms.atmiya.edu.in/api.txt";
   // private static final String BASE_URL_s= "http://icampus.biz/API_Student_Panel_JSON_Icampus.asmx/";


 //   public  static  final  String Base_URl_JAU = "http://sims.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";
    public  static  final  String Base_URl_JAU = "http://jau.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";
   // private static final String BASE_URL_s= "https://cms.atmiya.edu.in/API_Student_panel_json.asmx/";
    private static Retrofit retrofit = null;
    private static final String XYZ=null;
    public static Retrofit getClient()
    {
        if (retrofit == null)
        {

            retrofit = new Retrofit.Builder()
                        .baseUrl(Base_URl_JAU)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


            }


        return retrofit;
    }

    Request request = new Request.Builder()
            .url("http://cms.atmiya.edu.in/api.txt")
            .build();

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();


}
