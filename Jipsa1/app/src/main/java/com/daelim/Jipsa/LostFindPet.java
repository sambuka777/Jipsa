package com.daelim.Jipsa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.HashMap;
import java.util.Map;

public class LostFindPet extends AppCompatActivity implements AutoPermissionsListener, GoogleMap.OnCameraMoveListener {

    String id, value, sex;
    ImageButton close_btn;
    EditText name,chr;
    boolean isdiscovery, ismissing;

    double mlatitude, mlongitude;
    LatLng Clatlng;

    LocationManager locationManager;
    GoogleMap mMap;
    GPSListener gpsListener;
    SupportMapFragment mapFragment;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_write);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsListener = new GPSListener();

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        value = intent.getStringExtra("text");
        id = intent.getStringExtra("id");

        // 신고, 발견에 따른 값 세팅
        TextView title = (TextView)findViewById(R.id.write_title);
        title.setText("펫 " + value +"신고");

        TextView P_gps = (TextView)findViewById(R.id.pet_gps);
        P_gps.setText("펫이 " + value +"된 위치가 현재 위치와 같은가요?");


        //위치 같은지 여부
        RadioGroup R_map = (RadioGroup)findViewById(R.id.radioGroup2);
        R_map.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.gps_no){

                    mMap.setOnCameraMoveListener(LostFindPet.this::onCameraMove);

                }else if(checkedId == R.id.gps_yes){

                    Clatlng = new LatLng(mlatitude , mlongitude);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(Clatlng);
                    mMap.moveCamera(cameraUpdate);
                }
            }
        });


        if(value.equals("실종")){
            isdiscovery = false;
            ismissing = true;
        }else if(value.equals("발견")){
            isdiscovery = true;
            ismissing = false;
        }

        chr = (EditText)findViewById(R.id.wpet_chr);
        name = (EditText)findViewById(R.id.wpet_name);


        RadioGroup P_sex = (RadioGroup)findViewById(R.id.radioGroup);
        P_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.sex_m){
                    sex = "남자";
                }else if(checkedId == R.id.gps_yes){
                    sex = "여자";
                }
            }
        });

        Button BtnUpload = (Button)findViewById(R.id.btn_Upload);
        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chr.getText().toString() != "" && name.getText().toString() != "" && sex != null){

                    Map<String,Object> write = new HashMap<>();

                    write.put("id",id);
                    write.put("isdiscovery",isdiscovery);
                    write.put("ismissing",ismissing);
                    write.put("petchr", chr.getText().toString());
                    write.put("petname", name.getText().toString());
                    write.put("petsex",sex);
                    write.put("gps", new GeoPoint(Clatlng.latitude, Clatlng.longitude));
/*
                    db = FirebaseFirestore.getInstance();
                    db.collection("petofmiss")
                            .add(write)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    System.out.println("DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Error adding document"+ e);
                                }
                            });*/
                    Intent intent = new Intent(LostFindPet.this, MainActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("frag",7);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(LostFindPet.this)
                        .setMessage("모든 값을 입력하세요!")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){

                            }
                        })
                        .show();
                }
            }
        });



        //지도 현재 위치 가져오기
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_location);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                setDefaultLocation();
            }
        });



        //지도 움직임 먹히게
        final ScrollView scroll = (ScrollView) findViewById(R.id.scroll);
        ImageView ivMapTransparent = (ImageView) findViewById(R.id.ivMapTransparent);
        ivMapTransparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        scroll.requestDisallowInterceptTouchEvent(true);
                        return false;

                    case MotionEvent.ACTION_UP:
                        scroll.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });



        //취소버튼 클릭시
        close_btn = findViewById(R.id.btn_close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostFindPet.this, MainActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("frag",7);
                startActivity(intent);
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }


    public void startLocationService() {
        try {
            Location location = null;

            long minTime = 0;        // 0초마다 갱신 - 바로바로갱신
            float minDistance = 0;

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    showCurrentLocation(latitude, longitude);
                }

                //위치 요청하기
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
                //manager.removeUpdates(gpsListener);

            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    showCurrentLocation(latitude,longitude);
                }

                //위치 요청하기
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
                //manager.removeUpdates(gpsListener);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    class GPSListener implements LocationListener {

        // 위치 확인되었을때 자동으로 호출됨 (일정시간 and 일정거리)
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            showCurrentLocation(latitude,longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();

        // GPS provider를 이용전에 퍼미션 체크
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"접근 권한이 없습니다.",Toast.LENGTH_SHORT).show();

            return;
        } else {

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
                //manager.removeUpdates(gpsListener);
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
                //manager.removeUpdates(gpsListener);
            }

            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(gpsListener);

        if (mMap != null) {
            mMap.setMyLocationEnabled(false);
        }
    }

    private void showCurrentLocation(double latitude, double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(curPoint);
        if(mlatitude != latitude && mlatitude != longitude || mlatitude == ' ' && mlongitude == ' '){
            //현재 위치가 변했을때

            mlatitude = latitude;
            mlongitude = longitude;
            mMap.moveCamera(cameraUpdate);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    public void setDefaultLocation() {
        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onCameraMove() {
        CameraPosition position = mMap.getCameraPosition();
        LatLng target = position.target;

        Clatlng = target;
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }
}
