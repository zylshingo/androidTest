package com.jikexueyuan.locationshare;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private LocationClient mClient = null;
    private BDLocationListener locationListener = new MyLocationListener();
    private MapView mapView;
    private BaiduMap baiduMap;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Marker marker = null;
    private Button btn_share;
    private Button btn_UnShare;
    private locationService locationService = null;
    //是否第一次定位
    private boolean atFirst = true;
    private locationService.MyBinder binder = null;
    private double move = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.bmapView);
        mClient = new LocationClient(getApplicationContext());
        mClient.registerLocationListener(locationListener);

        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initLocation();

        btn_share = (Button) findViewById(R.id.btn_share);
        btn_share.setOnClickListener(this);
        btn_UnShare = (Button) findViewById(R.id.btn_UnShare);
        btn_UnShare.setOnClickListener(this);
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binder != null) {
            this.unbindService(this);
        }
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
        mClient.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
        mClient.stop();
    }

    /**
     * 按钮点击事件设定绑定服务和解绑服务
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                Intent i = new Intent(this, locationService.class);
                bindService(i, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_UnShare:
                if (binder != null) {
                    unbindService(this);
                    binder = null;
                    locationService = null;
                }
                marker = null;
                break;
        }
    }

    /**
     * 绑定服务时候设定接收消息的回调函数
     *
     * @param name
     * @param service
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (locationService.MyBinder) service;
        locationService = binder.getService();
        locationService.setDataListener(new DataListener() {
            @Override
            public void dataChanged(String pointData) {
                String[] split = pointData.split(",");
                LatLng point = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                if (marker == null) {
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
                    OverlayOptions options = new MarkerOptions().position(point).icon(bitmapDescriptor);
                    marker = (Marker) baiduMap.addOverlay(options);
                }
                marker.setPosition(point);
                System.out.println(split[0]);
                System.out.println(split[1]);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        binder = null;
        locationService = null;
    }

    /**
     * 百度地图定位回调
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mapView == null) {
                return;
            }
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
            //自定义定位图标
//            mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
            baiduMap.setMyLocationEnabled(true);
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(bdLocation.getDerect()).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locationData);
            baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));

            if (locationService != null) {
                locationService.sendMsg(bdLocation.getLongitude() + "," + (bdLocation.getLatitude() + (move += 0.001)));
            }
            if (atFirst) {
                atFirst = false;
                LatLng lng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(lng).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

}
