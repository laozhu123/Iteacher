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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.checkin.newactivity.CheckInActivity;
import com.li.zjut.iteacher.activity.imteacher.ImTeacherFirstNewActivity;
import com.li.zjut.iteacher.activity.main.MainActivity;
import com.li.zjut.iteacher.activity.myLesson.MyLessonActivity;
import com.li.zjut.iteacher.activity.schedule.MyScheduleNewActivity;
import com.li.zjut.iteacher.adapter.main.MyGridAdapter;
import com.li.zjut.iteacher.bean.main.Ret_Weather;
import com.li.zjut.iteacher.common.CommonTestUtil;
import com.li.zjut.iteacher.common.StaticData;
import com.li.zjut.iteacher.common.map.Utils;
import com.li.zjut.iteacher.http.Weather;
import com.li.zjut.iteacher.widget.banner.BannerView;
import com.li.zjut.iteacher.widget.main.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by LaoZhu on 2016/4/26.
 */
public class HomeFragment extends Fragment implements AMapLocationListener, GridView.OnItemClickListener, View.OnClickListener {

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private MyGridAdapter adapter;
    boolean haveWeather = false;
    TextView weather;

    View curView;
    ListView lvTodayCur;
    View moreCur;


    BannerView bannerView;
    List<Integer> mImgDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);

        MainActivity.content.setVisibility(View.VISIBLE);
        initView(view);
        getDataFromNetwork();
        initMap();
        return view;
    }

    private void getDataFromNetwork() {

        mImgDatas = new ArrayList<>();
        mImgDatas.add(R.mipmap.banner1);
        mImgDatas.add(R.mipmap.banner1);
        mImgDatas.add(R.mipmap.banner1);
        bannerView.setList(mImgDatas);
        bannerView.setOnBannerItemClickListener(new BannerView.OnBannerItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeather(String provider, String city) {

        Call<Ret_Weather> call = Weather.github.getweather(StaticData.KEYWEATHER, city, provider);

        call.enqueue(new Callback<Ret_Weather>() {

            @Override
            public void onResponse(Call<Ret_Weather> call, Response<Ret_Weather> response) {

                if (response.body().getMsg().equals("success")) {
                    haveWeather = true;
                    if (!weather.isShown())
                        weather.setVisibility(View.VISIBLE);
                    if (response.body().getResult().get(0).getFuture().get(0).getDayTime() != null) {
                        weather.setText(response.body().getResult().get(0).getFuture().get(0).getDayTime()
                                + " " + response.body().getResult().get(0).getFuture().get(0).getTemperature());
                    } else {
                        weather.setText("温度:" + response.body().getResult().get(0).getFuture().get(0).getTemperature());
                    }


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
        title.setText(getResources().getString(R.string.firstpage));


        weather = (TextView) v.findViewById(R.id.weather);
        TextView activity = (TextView) v.findViewById(R.id.txTitleRig);
        activity.setText("更多动态");
        activity.setVisibility(View.VISIBLE);
        activity.setOnClickListener(this);

        MyGridView gridView = (MyGridView) v.findViewById(R.id.gridview);
        adapter = new MyGridAdapter(getActivity());
        adapter.setTodayLessonNum(10);   //设置小红点
        adapter.setTodaySchedule(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        v.findViewById(R.id.ll_add_app).setOnClickListener(this);
        bannerView = (BannerView) v.findViewById(R.id.banner_view);
    }


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
                        getWeather(loc.getProvider(), loc.getCity().replace("市", ""));
                    } else {
//                        getWeather("浙江", "杭州");
                        locationClient.startLocation();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                getActivity().startActivity(new Intent(getActivity(), CheckInActivity.class));
                break;
            case 1:
                getActivity().startActivity(new Intent(getActivity(), MyLessonActivity.class));
                break;
            case 2:
                getActivity().startActivity(new Intent(getActivity(), MyScheduleNewActivity.class));
                break;
            case 3:
                getActivity().startActivity(new Intent(getActivity(), ImTeacherFirstNewActivity.class));
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        HashMap<String, String> h = new HashMap<String, String>();
        switch (v.getId()) {
            case R.id.ll_add_app:
                Toast.makeText(getActivity(), "add app", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txTitleRig:
                Toast.makeText(getActivity(), "show all activity", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
