package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.li.zjut.iteacher.activity.checkin.bean.Place;
import com.li.zjut.iteacher.bean.checkin.CheckInClass;
import com.li.zjut.iteacher.bean.checkin.CheckInLesson;
import com.li.zjut.iteacher.common.bitmap.View2Bitmap;
import com.li.zjut.iteacher.common.map.Utils;

import java.io.Serializable;

public class CheckInActivity extends BaseActivity implements AMapLocationListener, View.OnClickListener {

    MapView mMapView = null;
    AMap aMap = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    Marker marker = null;
    Bitmap bitmap;

    TextView txPlace, txPlaceDetail;
    EditText etPlaceCross, etLateTime;
    TextView tvCurname, tvCurTime, tvCheck;

    private final int REQUEST_CHANGE_PLACE = 1, REQUEST_SELECT_CUR = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in2);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.checkIn), true);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        mMapView.setFocusableInTouchMode(false);

        //弄一个arrow
        View point = View.inflate(this, R.layout.point, null);
        ImageView img = (ImageView) point.findViewById(R.id.img);
        img.setImageResource(R.drawable.point);
        bitmap = View2Bitmap.convertViewToBitmap(img, 70, 70);

        initView();
        initMap();
    }

    private void initView() {
        TextView titleRig = (TextView) findViewById(R.id.txTitleRig);
        titleRig.setText(getString(R.string.history_check));
        titleRig.setVisibility(View.VISIBLE);
        titleRig.setOnClickListener(this);
        tvCheck = (TextView) findViewById(R.id.tx_check);
        txPlace = (TextView) findViewById(R.id.place);
        txPlaceDetail = (TextView) findViewById(R.id.place_detail);
        etPlaceCross = (EditText) findViewById(R.id.et_local_cross);
        etLateTime = (EditText) findViewById(R.id.et_late_time);
        tvCurname = (TextView) findViewById(R.id.tv_cur_name);
        tvCurTime = (TextView) findViewById(R.id.tv_cur_time);
        findViewById(R.id.tv_check_again).setOnClickListener(this);
        findViewById(R.id.tv_place_change).setOnClickListener(this);
        findViewById(R.id.ll_cur).setOnClickListener(this);
        tvCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tx_check:
                if (!tvCheck.isSelected()) {
                    tvCheck.setSelected(true);
                    tvCheck.setTextColor(getResources().getColor(R.color.gray_character));
                    tvCheck.setText("已点");
                    findViewById(R.id.tv_check_again).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_check_again:
                findViewById(R.id.tv_check_again).setVisibility(View.GONE);
                tvCheck.setSelected(false);
                tvCheck.setText(getString(R.string.checkIn));
                tvCheck.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.ll_cur:
                intent.setClass(CheckInActivity.this, SelectCurActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_CUR);
                break;
            case R.id.tv_place_change:
                intent.setClass(CheckInActivity.this, PlaceChangeActivity.class);
                startActivityForResult(intent, REQUEST_CHANGE_PLACE);
                break;
            case R.id.txTitleRig:
                intent.setClass(CheckInActivity.this, CheckHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHANGE_PLACE:
                    Place place = (Place) data.getSerializableExtra("place");
                    txPlace.setText(place.getName());
                    txPlaceDetail.setText(place.getDetail());
                    addMarker(new LatLng(place.getLatitude(), place.getLongitude()));
                    break;
                case REQUEST_SELECT_CUR:
                    CheckInClass c = (CheckInClass) data.getSerializableExtra("class");
                    CheckInLesson l = (CheckInLesson) data.getSerializableExtra("lesson");
                    tvCurname.setText(l.getLessonName() + c.getClassName());
                    tvCurTime.setText(c.getClassDetail());
                    break;
            }
        }
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
//        locationClient.startLocation();
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
                        String place = loc.getPoiName();
                        String place_detail = loc.getAddress();
                        txPlace.setText(place);
                        txPlaceDetail.setText(place_detail);
                    } else {
                        txPlace.setText(getString(R.string.nowplace));
                    }

                    //添加每一帧图片。
                    addMarker(latLng);

                    aMap.setOnMapTouchListener(null);
                    aMap.getUiSettings().setCompassEnabled(false);
                    aMap.getUiSettings().setScaleControlsEnabled(false);
                    aMap.getUiSettings().setZoomGesturesEnabled(false);
                    aMap.getUiSettings().setZoomControlsEnabled(false);
                    aMap.getUiSettings().setAllGesturesEnabled(false);
                    aMap.getUiSettings().setIndoorSwitchEnabled(false);
                    aMap.getUiSettings().setLogoPosition(-1);
                    aMap.getUiSettings().setMyLocationButtonEnabled(false);
                    aMap.getUiSettings().setTiltGesturesEnabled(false);


                    break;
                default:
                    break;
            }
        }
    };

    private void addMarker(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1f)
                .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .perspective(true).draggable(true));

        marker.showInfoWindow();
    }

}
