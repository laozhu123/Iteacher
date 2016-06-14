package com.li.zjut.iteacher.activity.checkin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.bean.checkin.Lesson;
import com.li.zjut.iteacher.common.bitmap.View2Bitmap;
import com.li.zjut.iteacher.common.map.Utils;


public class CheckActivity extends BaseActivity implements
        AMapLocationListener, View.OnClickListener {

    MapView mMapView = null;
    AMap aMap = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    Marker marker = null;
    Bitmap bitmap;
    private TextView txClassNameTime, txPlace, txCheck,txReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.checkIn), true);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();

        //弄一个arrow
        View point = View.inflate(this, R.layout.point, null);
        ImageView img = (ImageView) point.findViewById(R.id.img);
        img.setImageResource(R.drawable.point);
        bitmap = View2Bitmap.convertViewToBitmap(img, 70, 70);

        initView();
        initMap();
    }

    private void initMap() {

        locationClient = new AMapLocationClient(this.getApplicationContext());
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


    private void initView() {

        findViewById(R.id.location).setOnClickListener(listener);
        txCheck = (TextView) findViewById(R.id.txCheck);
        txCheck.setOnClickListener(this);
        txReset = (TextView) findViewById(R.id.txReset);
        txReset.setText(getString(R.string.reset));
        txReset.setOnClickListener(this);
        txClassNameTime = (TextView) findViewById(R.id.txClassNameTime);
        txPlace = (TextView) findViewById(R.id.txPlace);
        TextView txTitleRig = (TextView) findViewById(R.id.txTitleRig);
        txTitleRig.setText(getString(R.string.todaycheck));
        txTitleRig.setVisibility(View.VISIBLE);
        txTitleRig.setOnClickListener(this);

        Lesson lesson = (Lesson) getIntent().getSerializableExtra("lesson");
        String lessonName = getString(R.string.nowclass) + ":" + lesson.getName();
        txClassNameTime.setText(lessonName);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.location:
                    locationClient.startLocation();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
        locationClient.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
        locationClient.stopLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case Utils.MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation) msg.obj;
                    String result = Utils.getLocationStr(loc);
                    Log.d("helo", result);
                    LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                    if (loc.getAddress() != null) {
                        String place = getString(R.string.nowplace) + ":" + loc.getAddress();
                        txPlace.setText(place);
                    } else {
                        txPlace.setText(getString(R.string.nowplace));
                    }

                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                    if (marker != null) {
                        marker.remove();
                    }

                    //添加每一帧图片。
                    marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1f)
                            .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                            .perspective(true).draggable(true));

                    marker.showInfoWindow();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txCheck:
                txCheck.setSelected(true);
                txReset.setVisibility(View.VISIBLE);
                break;
            case R.id.txTitleRig:
                startActivity(new Intent().setClass(getApplication(),TodayCheckInfoActivity.class));
                break;
            case R.id.txReset:
                txCheck.setSelected(false);
                txReset.setVisibility(View.GONE);
                break;
        }
    }
}
