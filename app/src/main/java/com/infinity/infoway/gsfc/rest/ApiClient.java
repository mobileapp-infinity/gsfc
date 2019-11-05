package com.infinity.infoway.gsfc.rest;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{


    //************** SIMS URL **************

    public static final String Base_URl_GSFC = "https://admission.gsfcuniversity.ac.in//API_Student_Panel_JSON_Icampus.asmx/";


    //************** JAU URL **************
//    public  static  final  String Base_URl_JAU = "http://jau.icrp.in/API_Student_Panel_JSON_Icampus.asmx/";

    private static Retrofit retrofit = null;
    private static final String XYZ = null;

    public static Retrofit getClient()
    {
        if (retrofit == null)
        {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URl_GSFC)
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
