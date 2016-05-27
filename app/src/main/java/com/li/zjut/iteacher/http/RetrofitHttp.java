package com.li.zjut.iteacher.http;


import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.interFace.GetUrl;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by LaoZhu on 2016/5/11.
 */
public class RetrofitHttp {

    // Create a very simple REST adapter which points the GitHub API.
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(StaticData.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();

    // Create an instance of our GitHub API interface.
    public static GetUrl github = retrofit.create(GetUrl.class);


}
