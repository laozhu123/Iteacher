package com.li.zjut.iteacher.activity.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.personinfo.PersonInfoActivity;
import com.li.zjut.iteacher.activity.wel_login.LoginActivity;
import com.li.zjut.iteacher.bean.main.Ret_Weather;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.common.SharePerfrence;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.map.Utils;
import com.li.zjut.iteacher.common.weather.WeatherImg;
import com.li.zjut.iteacher.http.Weather;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class HomeFragment extends Fragment implements AMapLocationListener {

    String Tag = "HomeFragment";

    private ImageView mImg_left;
    private CircleImageView mImg_profile;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    LinearLayout ll_yes, ll_no;
    TextView tv_date0,tv_date1, tv_date2,tv_wd0, tv_wd1, tv_wd2;
    ImageView ima0, ima1, ima2;

    boolean haveWeather = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        initView(view);
        initMap();
        return view;
    }

    private void getWeather(String provider, String city) {

        Call<Ret_Weather> call = Weather.github.getweather(StaticData.KEYWEATHER, city, provider);

        call.enqueue(new Callback<Ret_Weather>() {

            @Override
            public void onResponse(Call<Ret_Weather> call, Response<Ret_Weather> response) {

                if (response.body().getMsg().equals("success")) {
                    Log.d("out", response.body().getResult().get(0).getFuture().get(0).getTemperature());
                    ll_yes.setVisibility(View.VISIBLE);
                    haveWeather = true;
                    tv_date0.setText("今天    " + response.body().getResult().get(0).getFuture().get(0).getDayTime());
                    tv_wd0.setText(response.body().getResult().get(0).getFuture().get(0).getTemperature());
                    ima0.setImageResource(WeatherImg.imageResoId(response.body().getResult().get(0).getFuture().get(0).getDayTime()));

                    tv_date1.setText("明天    " + response.body().getResult().get(0).getFuture().get(1).getDayTime());
                    tv_wd1.setText(response.body().getResult().get(0).getFuture().get(1).getTemperature());
                    ima1.setImageResource(WeatherImg.imageResoId(response.body().getResult().get(0).getFuture().get(1).getDayTime()));

                    tv_date2.setText("后天    " + response.body().getResult().get(0).getFuture().get(2).getDayTime());
                    tv_wd2.setText(response.body().getResult().get(0).getFuture().get(2).getTemperature());
                    ima2.setImageResource(WeatherImg.imageResoId(response.body().getResult().get(0).getFuture().get(2).getDayTime()));
                } else {
                }
            }

            @Override
            public void onFailure(Call<Ret_Weather> call, Throwable t) {
                CommonTestUtil.toast(getActivity(), "bad");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!haveWeather)
            locationClient.startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        locationClient.stopLocation();
    }

    private void initMap() {

        locationClient = new AMapLocationClient(getActivity());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
//        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 设置定位监听
        locationClient.setLocationListener(this);

        locationOption.setOnceLocation(true);
        locationOption.setNeedAddress(true);
        locationOption.setGpsFirst(true);
//        locationOption.setInterval(5000);

        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    private void initView(View v) {
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.myday));


        mImg_profile = (CircleImageView) v.findViewById(R.id.profile_image);
        mImg_profile.setImageResource(R.mipmap.palette);
        mImg_profile.setVisibility(View.VISIBLE);
        mImg_profile.setOnClickListener(listener);


        ll_yes = (LinearLayout) v.findViewById(R.id.ws2_ll_yes);


        tv_date0 = (TextView) v.findViewById(R.id.ws2_tv_0_date);
        tv_date1 = (TextView) v.findViewById(R.id.ws2_tv_1_date);
        tv_date2 = (TextView) v.findViewById(R.id.ws2_tv_2_date);
        tv_wd0 = (TextView) v.findViewById(R.id.ws2_tv_0_wd);
        tv_wd1 = (TextView) v.findViewById(R.id.ws2_tv_1_wd);
        tv_wd2 = (TextView) v.findViewById(R.id.ws2_tv_2_wd);

        ima0 = (ImageView) v.findViewById(R.id.ws2_iv_0_image);
        ima1 = (ImageView) v.findViewById(R.id.ws2_iv_1_image);
        ima2 = (ImageView) v.findViewById(R.id.ws2_iv_2_image);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profile_image:
                    startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                    break;
            }
        }
    };



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case Utils.MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation) msg.obj;
                    String result = Utils.getLocationStr(loc);
                    if (loc.getAddress() != null) {
                        Log.d("out", result);
//                        getWeather(loc.getProvider(), loc.getCity().replace("市", ""));
                        getWeather("浙江", "杭州");
                    } else {
                        locationClient.startLocation();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
