package com.li.zjut.iteacher.interFace;


import com.google.gson.JsonObject;
import com.li.zjut.iteacher.bean.Ret_Login;
import com.li.zjut.iteacher.bean.Ret_Register;
import com.li.zjut.iteacher.bean.register.Ret_Allcampus;
import com.li.zjut.iteacher.bean.register.Ret_Allcollege;
import com.li.zjut.iteacher.bean.register.Ret_Allschool;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LaoZhu on 2016/5/10.
 */
public interface GetUrl {

//    @POST("/Iteacher/user/account/register")
//    Call<JsonObject> accountLogin(@Query("phone") String phone, @Query("password") String password, @Query("cid") String cid, @Query("schoolid") int schoolid, @Query("campusid") int campusid, @Query("collegeid") int collegeid, @Query("device") int device); // 登录

    @FormUrlEncoded
    @POST("/Iteacher/user/account/register")
    Call<Ret_Register> accountRegister(@FieldMap Map<String, Object> helo); //

    @FormUrlEncoded
    @POST("/Iteacher/user /account/login")
    Call<Ret_Login> accountLogin(@FieldMap Map<String, Object> helo); //


    @GET("/Iteacher/course/coursetime/list")
    Call<JsonObject> getcurriculms(@Query("sid") String uid); // 登录

    @GET("/Iteacher/school/list")
    Call<Ret_Allschool> getschools(); // 获取所有学校

    @GET("/Iteacher/school/campus/list")
    Call<Ret_Allcampus> getcampus(@Query("schoolid") int schoolid); // 获取所有校区

    @GET("/Iteacher/school/college/list")
    Call<Ret_Allcollege> getcolleges(@Query("campusid") int campusid); // 获取所有学院
}
