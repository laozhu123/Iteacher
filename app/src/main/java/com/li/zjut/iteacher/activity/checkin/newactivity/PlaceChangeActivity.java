package com.li.zjut.iteacher.activity.checkin.newactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.li.zjut.iteacher.R;
import com.li.zjut.iteacher.activity.base.BaseActivity;
import com.li.zjut.iteacher.activity.checkin.adapter.PlaceListAdapter;
import com.li.zjut.iteacher.activity.checkin.bean.Place;
import com.li.zjut.iteacher.common.bitmap.View2Bitmap;
import com.li.zjut.iteacher.common.map.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Query;

public class PlaceChangeActivity extends BaseActivity implements AMapLocationListener, View.OnClickListener, PoiSearch.OnPoiSearchListener {

    MapView mMapView = null;
    AMap aMap = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    Marker marker = null;
    Bitmap bitmap;

    PoiSearch.Query query;

    ListView lvPlace;
    List<Place> mDatas = new ArrayList<>();
    PlaceListAdapter adapter;
    int showIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_change);
        super.setContext(findViewById(R.id.toolbar), getString(R.string.place_change), true);

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
        TextView titleRig = (TextView) findViewById(R.id.txTitleRig);
        titleRig.setText(getString(R.string.sure));
        titleRig.setVisibility(View.VISIBLE);
        titleRig.setOnClickListener(this);
        findViewById(R.id.location).setOnClickListener(this);
        lvPlace = (ListView) findViewById(R.id.lv_place);

        adapter = new PlaceListAdapter(mDatas, this);
        adapter.setOnSelectListener(getOnSelectListener());
        lvPlace.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txTitleRig:
                Intent intent = new Intent();
                intent.putExtra("place", mDatas.get(showIndex));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.location:
                locationClient.startLocation();
                break;
        }
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

                    aMap.getUiSettings().setZoomControlsEnabled(false);
                    aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);

                    addMarker(latLng);
                    searchNear(loc);
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

    private void searchNear(AMapLocation loc) {

        query = new PoiSearch.Query("", "科教文化服务|地名地址信息", loc.getCityCode());
        // keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
        //共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
        query.setPageSize(18);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(loc.getLatitude(),
                loc.getLongitude()), 1000));//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            Log.i("---", "查询结果:" + i);
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && !poiItems.isEmpty()) {
                        for (PoiItem p : poiItems) {
                            Place place = new Place();
                            place.setName(p.getTitle());
                            place.setDetail(p.getSnippet());
                            place.setLatitude(p.getLatLonPoint().getLatitude());
                            place.setLongitude(p.getLatLonPoint().getLongitude());
                            mDatas.add(place);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
                Toast.makeText(PlaceChangeActivity.this, "该距离内没有找到结果", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("---", "查询结果:" + i);
            Toast.makeText(PlaceChangeActivity.this, "异常代码---" + i, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private PlaceListAdapter.OnSelectListener getOnSelectListener() {
        return new PlaceListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int index) {
                LatLng latLon = new LatLng(mDatas.get(index).getLatitude(), mDatas.get(index).getLongitude());
                addMarker(latLon);
                showIndex = index;
            }
        };
    }
}
