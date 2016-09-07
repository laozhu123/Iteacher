package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.common.DensityUtil;
import com.li.zjut.iteacher.common.bitmap.View2Bitmap;

public class MapShowEveryOneActivity extends BaseActivity {

    MapView mMapView = null;
    AMap aMap = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    int widthHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_every_one);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.all_check_place), true);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        mMapView.setFocusableInTouchMode(false);

        initData();
        initMap();
    }

    private void initMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
    }

    private void initData() {
        widthHeight = DensityUtil.dip2px(this, 30);

        int id = getIntent().getIntExtra("id", -1);

        LatLng teaLatLng = new LatLng(30.26, 120.19);
        addCircle(teaLatLng);

        for (int i = 0; i < 5; i++) {
            addMarker(new LatLng(30.26 + i * 0.0001, 120.19 + i * 0.0002), "王河" + i);
        }
    }

    private void addCircle(LatLng teaLatLng) {
        // 绘制一个圆形
        aMap.addCircle(new CircleOptions().center(teaLatLng)
                .radius(300).strokeColor(getResources().getColor(R.color.white)).fillColor(getResources().getColor(R.color.blue_shadow))
                .strokeWidth(1));

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(teaLatLng, 15));
    }


    private void addMarker(LatLng latLng, String name) {
        //弄一个arrow
        Marker marker = null;
        Bitmap bitmap;

        View point = View.inflate(this, R.layout.point, null);
        ImageView img = (ImageView) point.findViewById(R.id.img);
        img.setImageDrawable(com.li.zjut.iteacher.common.Utils.getCircleBg(name.substring(0, 2), this));
        bitmap = View2Bitmap.convertViewToBitmap(img, widthHeight, widthHeight);

        marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1f)
                .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .perspective(true).draggable(true));
        marker.showInfoWindow();
    }
}
