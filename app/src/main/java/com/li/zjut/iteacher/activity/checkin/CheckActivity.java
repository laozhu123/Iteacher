package com.li.zjut.iteacher.activity.checkin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.li.zjut.iteacher.adapter.schedule.ScheduleListAdapter;
import com.li.zjut.iteacher.bean.checkin.Lesson;
import com.li.zjut.iteacher.bean.test.Com;
import com.li.zjut.iteacher.common.bitmap.View2Bitmap;
import com.li.zjut.iteacher.common.map.Utils;
import com.li.zjut.iteacher.common.mylesson.SizeUtil;
import com.li.zjut.iteacher.widget.recycleview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class CheckActivity extends BaseActivity implements
        AMapLocationListener, View.OnClickListener {

    MapView mMapView = null;
    AMap aMap = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    Marker marker = null;
    Bitmap bitmap;
    private TextView  txPlace, txCheck, txReset;
    private List<Com> mData = new ArrayList<>();
    private PopupWindow pop = null;
    private PopupWindow pop1 = null;
    private View mHidden;
    private TextView popTxtTitle;
    private RecyclerView reCycleView;
    private TextView noContext;
    private ScheduleListAdapter mAdapter;

    private TextView place,lessonname;
    private EditText normal,late;

    public static final int REQUEST = 1;
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pop();
                pop1();
                getTodayLesson();
            }
        }, 1000);
        initMap();
    }

    private void getTodayLesson() {

        for (int i = 1; i < 10; i++) {
            Com com = new Com();
            com.setText("helo");
            mData.add(com);
        }
        mAdapter.notifyDataSetChanged();
        reCycleView.setVisibility(View.VISIBLE);
        noContext.setVisibility(View.GONE);
    }

    android.os.Handler handler = new android.os.Handler();

    private void pop() {
        View cont = LayoutInflater.from(this)
                .inflate(R.layout.popup_lesson_list, null);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int w = rect.right - rect.left - 2 * SizeUtil.dp2px(32);
        pop = new PopupWindow(cont, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(
                getResources().getDrawable(R.drawable.popupwindow));

        reCycleView = (RecyclerView) cont.findViewById(R.id.reCycleView);
        reCycleView.setLayoutManager(new LinearLayoutManager(this));
        reCycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        reCycleView.setItemAnimator(new DefaultItemAnimator());

        noContext = (TextView) cont.findViewById(R.id.noContext);
        noContext.setText(getString(R.string.nodata));
        mAdapter = new ScheduleListAdapter(mData);
        mAdapter.setOnItemClickListener(new ScheduleListAdapter.OnItemClickListener() {

            @Override
            public void onClick(View v, int position) {
                pop.dismiss();
                showPop1();
            }
        });
        reCycleView.setAdapter(mAdapter);

        popTxtTitle = (TextView) cont.findViewById(R.id.title);
        popTxtTitle.setText(getString(R.string.todaytask));

        cont.findViewById(R.id.allLesson).setOnClickListener(this);
        cont.findViewById(R.id.close).setOnClickListener(this);

    }

    private void pop1() {
        View cont = LayoutInflater.from(this)
                .inflate(R.layout.popup_check, null);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int w = rect.right - rect.left - 2 * SizeUtil.dp2px(32);
        pop1 = new PopupWindow(cont, w, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop1.setOutsideTouchable(true);
        pop1.setBackgroundDrawable(
                getResources().getDrawable(R.drawable.popupwindow));

        lessonname = (TextView) cont.findViewById(R.id.lessonname);
        place = (TextView) cont.findViewById(R.id.place);
        normal = (EditText) cont.findViewById(R.id.normal);
        late = (EditText) cont.findViewById(R.id.late);

        cont.findViewById(R.id.sure).setOnClickListener(this);
        cont.findViewById(R.id.cancel).setOnClickListener(this);

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
        mHidden = findViewById(R.id.hidden);
        findViewById(R.id.transparent).setOnClickListener(this);
        findViewById(R.id.location).setOnClickListener(listener);
        txCheck = (TextView) findViewById(R.id.txCheck);
        txCheck.setOnClickListener(this);
        txReset = (TextView) findViewById(R.id.txReset);
        txReset.setText(getString(R.string.reset));
        txReset.setOnClickListener(this);
        txPlace = (TextView) findViewById(R.id.txPlace);
        TextView txTitleRig = (TextView) findViewById(R.id.txTitleRig);
        txTitleRig.setText(getString(R.string.todaycheck));
        txTitleRig.setVisibility(View.VISIBLE);
        txTitleRig.setOnClickListener(this);

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

    private void showPop() {

        pop.showAtLocation((View) mHidden.getParent(), Gravity.CENTER, 0, 0);
        findViewById(R.id.transparent).setVisibility(View.VISIBLE);
    }

    private void showPop1() {

        pop1.showAtLocation((View) mHidden.getParent(), Gravity.CENTER, 0, 0);
        findViewById(R.id.transparent).setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {

        if (pop.isShowing()||pop1.isShowing()) {
            pop.dismiss();
            pop1.dismiss();
            findViewById(R.id.transparent).setVisibility(View.GONE);
        } else
            super.onBackPressed();

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
                showPop();
                break;
            case R.id.txTitleRig:
                startActivity(new Intent().setClass(getApplication(), TodayCheckInfoActivity.class));
                break;
            case R.id.txReset:
                txCheck.setSelected(false);
                txReset.setVisibility(View.GONE);
                break;
            case R.id.close:
            case R.id.transparent:
                pop.dismiss();
                pop1.dismiss();
                findViewById(R.id.transparent).setVisibility(View.GONE);
                break;
            case R.id.allLesson:
                pop.dismiss();
                findViewById(R.id.transparent).setVisibility(View.GONE);
                startActivityForResult(new Intent(CheckActivity.this,AllLessonsActivity.class),REQUEST);
                break;
            case R.id.sure:
                pop1.dismiss();
                findViewById(R.id.transparent).setVisibility(View.GONE);

                txCheck.setSelected(true);
                txReset.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel:
                pop1.dismiss();
                findViewById(R.id.transparent).setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:
//                txCheck.setSelected(true);
//                txReset.setVisibility(View.VISIBLE);
                showPop1();
                break;
        }
    }
}
