package com.dormitorylife.sduse1708;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class BFragment extends Fragment implements View.OnClickListener{
    private View view;
    public TextView map_location,get_location;
    public String student_id;
    //地图变量
    MapView mapView;
    TencentMap tencentMap;
    TencentLocationRequest request;
    TencentLocationListener locationListener;
    TencentLocationManager locationManager;
    TencentMap.OnMarkerClickListener markerClickListener;
    TencentLocation myLocation;
    Marker meMarker;
    boolean isRegisteredLocationManager;
    boolean isFirstLocate=true;
    boolean Flag=true;
    private ArrayList<String[]> data;
    private double myLatitude,myLongitude;//我的经纬度
    private double matesLatitude,matesLongitude;//舍友的经纬度
    private String distance;//二者的距离


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_b, container, false);
        initView(view);
        Toast.makeText(getActivity(), "地图启动中", Toast.LENGTH_SHORT).show();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        student_id=MainActivity.student_id;
        initMap();
        useMapAndLocation();
    }

    //初始化View
    private void initView(View view){
        mapView=(MapView)view.findViewById(R.id.map);
        map_location = (TextView) view.findViewById(R.id.map_location);
        get_location = (TextView) view.findViewById(R.id.get_location);
    }

    //初始化地图设置
    private void initMap(){
        tencentMap=mapView.getMap();
        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        tencentMap.setTrafficEnabled(true);
        UiSettings mapUiSettings =tencentMap.getUiSettings();
        mapUiSettings.setAllGesturesEnabled(true);
        mapUiSettings.setCompassEnabled(true);
        mapUiSettings.setZoomControlsEnabled(true);
        initMarkerClickListener();
        tencentMap.setOnMarkerClickListener(markerClickListener);
        tencentMap.setOnTapMapViewInfoWindowHidden(true);
    }

    //创建位置监听器
    private void makeLocationListener(){
        locationListener=new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
                myLocation=tencentLocation;
                myLatitude=tencentLocation.getLatitude();
                myLongitude=tencentLocation.getLongitude();
                if(TencentLocation.ERROR_OK==error) {
                    //定位成功
                    if(isFirstLocate){
                        navigateToMe(tencentLocation);
                        isFirstLocate=false;
                    }
                    getLocation();
                }else{
                    //定位失败
                    switch (error){
                        case TencentLocation.ERROR_NETWORK:
                            Toast.makeText(getActivity(),"网络问题引起的定位失败", Toast.LENGTH_SHORT).show();
                            break;
                        case TencentLocation.ERROR_BAD_JSON:
                            Toast.makeText(getActivity(),"GPS, Wi-Fi 或基站错误引起的定位失败：\n" +
                                    "1、用户的手机确实采集不到定位凭据，比如偏远地区比如地下车库电梯内等;\n" +
                                    "2、开关跟权限问题，比如用户关闭了位置信息，关闭了Wi-Fi，未授予app定位权限等。", Toast.LENGTH_SHORT).show();
                            break;
                        case TencentLocation.ERROR_WGS84:
                            Toast.makeText(getActivity(),"无法将WGS84坐标转换成GCJ-02坐标时的定位失败", Toast.LENGTH_SHORT).show();
                            break;
                        case TencentLocation.ERROR_UNKNOWN:
                            Toast.makeText(getActivity(),"未知原因引起的定位失败", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
            @Override
            public void onStatusUpdate(String name, int status, String desc) {
                String s="";
                switch (status){
                    case STATUS_DENIED:
                        s="权限被禁止";
                        break;
                    case STATUS_DISABLED:
                        s = "模块关闭";
                        break;
                    case STATUS_ENABLED:
                        s = "模块开启";
                        break;
                    case STATUS_GPS_AVAILABLE:
                        s = "GPS可用，代表GPS开关打开，且搜星定位成功";
                        break;
                    case STATUS_GPS_UNAVAILABLE:
                        s = "GPS不可用，可能 gps 权限被禁止或无法成功搜星";
                        break;
                    case STATUS_LOCATION_SWITCH_OFF:
                        s = "位置信息开关关闭，在android M系统中，此时禁止进行wifi扫描";
                        break;
                    case STATUS_UNKNOWN:
                        break;
                }
                Log.d("location", "location status:" + name + ", " + desc + " " + s);
            }
        };
    }

    //创建定位请求
    private void makeRequest(){
        request=TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
        request.setAllowCache(true);
    }

    //注册位置监听器
    private boolean registerLocationManager(Context context,TencentLocationListener listener,TencentLocationRequest request){
        locationManager=TencentLocationManager.getInstance(context);
        int error=locationManager.requestLocationUpdates(request,listener);
        switch (error){
            case 0:
                return true;
            case 1:
                Log.d("MainActivity","设备缺少使用腾讯定位SDK需要的基本条件");
                return false;
            case 2:
                Log.d("MainActivity","配置的 Key 不正确");
                return false;
            case 3:
                Log.d("MainActivity","自动加载libtencentloc.so失败，可能由以下原因造成：\n" +
                        "1、这往往是由工程中的so与设备不兼容造成的，应该添加相应版本so文件;\n" +
                        "2、如果您使用AndroidStudio,可能是gradle没有正确指向so文件加载位置，可以按照这里配置您的gradle");
                return false;
            default:
                return false;
        }
    }

    //创建Marker点击监听器
    private void initMarkerClickListener(){
        markerClickListener=new TencentMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                TencentMap.InfoWindowAdapter infoWindowAdapter=new TencentMap.InfoWindowAdapter() {
                    TextView tv_name,tv_distance;
                    Button button_wash;
                    Button button_getIn;
                    Button button_back;
                    String student_name;
                    String distance;
                    @Override
                    public View getInfoWindow(Marker marker) {
                        LinearLayout infoWindow=(LinearLayout) View.inflate(getActivity(),R.layout.map_infowindow,null);
                        tv_name=(TextView)infoWindow.findViewById(R.id.mapInfo_title);
                        tv_distance=(TextView)infoWindow.findViewById(R.id.mapInfo_distance2);
                        student_name=marker.getTitle();
                        distance=marker.getSnippet();
                        button_wash=(Button)view.findViewById(R.id.mapInfo_wash);
                        button_getIn=(Button)view.findViewById(R.id.mapInfo_getIn);
                        button_back=(Button)view.findViewById(R.id.mapInfo_back);
                        button_wash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendNotice(student_name,0);
                            }
                        });
                        button_getIn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendNotice(student_name,1);
                            }
                        });
                        button_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendNotice(student_name,2);
                            }
                        });
                        tv_name.setText(student_name);
                        tv_distance.setText(distance+"米");
                        return infoWindow;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        return null;
                    }
                };
                tencentMap.setInfoWindowAdapter(infoWindowAdapter);
                return true;
            }
        };
    }

    //使用地图和定位
    private void useMapAndLocation(){
        makeLocationListener();
        makeRequest();
        isRegisteredLocationManager=registerLocationManager(getActivity(),locationListener,request);
        if(isRegisteredLocationManager==false){
            locationManager.removeUpdates(locationListener);
            //finish();
        }

    }

    //移动到我
    private void navigateToMe(TencentLocation tencentLocation){
        LatLng latLng=new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude());
        CameraUpdate cameraSigma= CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()),
                17,
                0,
                0
        ));
        tencentMap.animateCamera(cameraSigma);
        if(isFirstLocate){
            meMarker=tencentMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .anchor(0.5f,0.5f));
        }
        meMarker.setPosition(latLng);

    }

    //获取当前自己的经纬度，并同步到数据库
    public void getLocation() {
        new Thread(runnable).start();
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
                Looper.prepare();
                LinkServer linkServer=new LinkServer();
                linkServer.updateLocation(student_id,""+myLatitude,""+myLongitude);
                Toast.makeText(getActivity(), "位置数据更新成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    //在地图上标记舍友的位置
    public void markRoommates() {
        new Thread(changeUI).start();
    }
    Runnable changeUI=new Runnable() {
        @Override
        public void run() {
            try {
                LinkServer linkServer = new LinkServer();
                String[] me = linkServer.student(student_id);
                String dorm_building=me[7].substring(0,me[7].length()-1)+"%23";
                String dorm_room=me[8];
                data = linkServer.student_all(dorm_building,dorm_room);
                Marker[] makers=new Marker[data.size()];
                for(int i=0;i<data.size();i++){
                    if(Flag){
                        String[] mate=data.get(i);
                        if (mate[0].equals(student_id)) {
                            meMarker.setTitle("我："+mate[1]);
                            meMarker.setSnippet("0");
                            continue;
                        }
                        makers[i]=tencentMap.addMarker(new MarkerOptions()
                                .title("舍友："+mate[1])
                                .icon(BitmapDescriptorFactory.defaultMarker())
                                );
                    }
                }
                Flag=false;
                for (int i = 0; i < data.size(); i++) {
                    String[] mate = data.get(i);
                    if (mate[0].equals(student_id)) {
                        continue;
                    }
                        matesLatitude=Double.parseDouble(mate[9]);
                        matesLongitude=Double.parseDouble(mate[10]);
                        LatLng start=new LatLng(myLatitude,myLongitude);
                        LatLng end=new LatLng(matesLatitude,matesLongitude);
                        distance=getDistance(start,end);
                        makers[i].setSnippet(distance);
                        LatLng latLng = new LatLng(matesLatitude,matesLongitude);
                        makers[i].setPosition(latLng);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    //获得两点间距离
    private String getDistance(LatLng start,LatLng end){
        //地球半径
        final double R=6371;
        //角度制转弧度制
        double lat1=(Math.PI/180)*start.latitude;
        double lat2=(Math.PI/180)*end.latitude;
        double lon1=(Math.PI/180)*start.longitude;
        double lon2=(Math.PI/180)*end.longitude;
        //两点间距离km
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
        return String.format("%.2f",(d*1000));
    }

    //发送提醒
    public void sendNotice(final String student_name, int toDo){
        DNotice dNotice;
        switch (toDo){
            case 0://洗衣服
                dNotice=new DNotice(MainActivity.name,"有一位舍友提醒了"+student_name+"该洗衣服了！");
                dNotice.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Toast.makeText(getActivity(), "您提醒了"+student_name+"去洗衣服", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1://收衣服
                dNotice=new DNotice(MainActivity.name,"有一位舍友提醒了"+student_name+"该收衣服了！");
                dNotice.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Toast.makeText(getActivity(), "您提醒了"+student_name+"去收衣服", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2://回宿舍
                dNotice=new DNotice(MainActivity.name,"有一位舍友提醒了"+student_name+"该回宿舍了！");
                dNotice.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Toast.makeText(getActivity(), "您提醒了"+student_name+"回宿舍", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    //点击按钮
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.get_location:
                markRoommates();
                break;
            case R.id.map_location:
                navigateToMe(myLocation);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onDetach(){
        super.onDetach();
    }


}
