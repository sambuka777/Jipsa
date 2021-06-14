package com.daelim.Jipsa;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class FragmentLost extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback{

        String id;
        private GoogleMap mMap;

        private static final String TAG = "googlemap";
        private static final int GPS_ENABLE_REQUEST_CODE = 2001;
        private static final int UPDATE_INTERVAL_MS = 5000;  // 5초
        private static final int FASTEST_UPDATE_INTERVAL_MS = 1000; // 1초


        // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
        private static final int PERMISSIONS_REQUEST_CODE = 100;
        boolean needRequest = false;


        // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
        String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소

        ArrayList<String> img = new ArrayList<>();
        ArrayList<String> dismiss = new ArrayList<>();

        Location mCurrentLocatiion;
        LatLng currentPosition;
        boolean flag_findOrlost;

        private FusedLocationProviderClient mFusedLocationClient;
        private LocationRequest locationRequest;
        private Location location;


        View mLayout;
        MapView mapView;
        ImageButton lostpet_btn, findpet_btn;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(UPDATE_INTERVAL_MS)
                    .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


            LocationSettingsRequest.Builder builder =
                    new LocationSettingsRequest.Builder();

            builder.addLocationRequest(locationRequest);


            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

            mLayout = inflater.inflate(R.layout.activity_lost, container, false);

            mapView = (MapView) mLayout.findViewById(R.id.map);
            mapView.onCreate(savedInstanceState);

            mapView.getMapAsync(this);


            lostpet_btn = mLayout.findViewById(R.id.btn_lost);
            lostpet_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LostFindPet.class);
                    intent.putExtra("id",id);
                    intent.putExtra("text","실종");

                    startActivity(intent);
                }
            });

            findpet_btn = mLayout.findViewById(R.id.btn_find);
            findpet_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LostFindPet.class);
                    intent.putExtra("id",id);
                    intent.putExtra("text","발견");

                    startActivity(intent);
                }
            });

            //마커 클릭 전 정보창 숨기기
            LinearLayout petinfobox = (LinearLayout) mLayout.findViewById(R.id.petinfobox);
            petinfobox.setVisibility(View.INVISIBLE);

            return mLayout;
        }

        @Override
        public void onResume() {
            mapView.onResume();
            super.onResume();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mapView.onLowMemory();
        }



    @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.d(TAG, "onMapReady :");
            MapsInitializer.initialize(this.getActivity());


            mMap = googleMap;

            //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
            //지도의 초기위치를 서울로 이동
            setDefaultLocation();



            //런타임 퍼미션 처리
            // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                    hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {

                // 2. 이미 퍼미션을 가지고 있다면
                // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


                startLocationUpdates(); // 3. 위치 업데이트 시작


            }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

                // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                    // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                    Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                            requestPermissions( REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                        }
                    }).show();


                } else {
                    // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                    // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                    requestPermissions( REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                }

            }



            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            // 현재 오동작을 해서 주석처리

            //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng latLng) {

                    Log.d( TAG, "onMapClick :");
                }
            });
        }

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                String markerSnippet = "위도:" + String.valueOf(location.getLatitude()) + " 경도:" + String.valueOf(location.getLongitude());

               /* Log.d(TAG, "onLocationResult : " + markerSnippet);*/

                //현재 위치로 이동
                setCurrentLocation(location);
            }
            }

        };



        private void startLocationUpdates() {

            if (!checkLocationServicesStatus()) {

                Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
                showDialogForLocationServiceSetting();
            }else {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.ACCESS_COARSE_LOCATION);



                if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                        hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                    Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                    return;
                }


                Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                if (checkPermission())
                    mMap.setMyLocationEnabled(true);

            }

        }


        @Override
        public void onStart() {
            super.onStart();

            Log.d(TAG, "onStart");

            if (checkPermission()) {

                Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

                if (mMap!=null)
                    mMap.setMyLocationEnabled(true);

            }


        }


        @Override
        public void onStop() {

            super.onStop();

            if (mFusedLocationClient != null) {

                Log.d(TAG, "onStop : call stopLocationUpdates");
                mFusedLocationClient.removeLocationUpdates(locationCallback);
            }
        }




        public String getCurrentAddress(LatLng latlng) {

            //지오코더... GPS를 주소로 변환
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

            List<Address> addresses;

            try {

                addresses = geocoder.getFromLocation(
                        latlng.latitude,
                        latlng.longitude,
                        1);
            } catch (IOException ioException) {
                //네트워크 문제
                Toast.makeText(getActivity(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
                return "지오코더 서비스 사용불가";
            } catch (IllegalArgumentException illegalArgumentException) {
                Toast.makeText(getActivity(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
                return "잘못된 GPS 좌표";

            }


            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getActivity(), "주소 미발견", Toast.LENGTH_LONG).show();
                return "주소 미발견";

            } else {
                Address address = addresses.get(0);
                return address.getAddressLine(0).toString();
            }

        }


        public boolean checkLocationServicesStatus() {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }


        public void setCurrentLocation(Location location) {

            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());



            //마커찍기
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            CollectionReference docRef = db.collection("petofmiss");
            docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String lost_petname = (String) document.get("petname");
                            String lost_petchr = (String) document.get("petchr");
                            String lost_petsex = (String) document.get("petsex");
                            flag_findOrlost = document.getBoolean("isdiscovery");
                            GeoPoint geoPoint = document.getGeoPoint("gps");

                            double lostlat = geoPoint.getLatitude();
                            double lostlng = geoPoint.getLongitude();

                            LatLng markersLatLng = new LatLng(lostlat , lostlng);

                            img.add((String) document.get("image"));
                            dismiss.add(String.valueOf(document.get("isdiscovery")));
                            int height = 130;
                            int width = 100;
                            BitmapDrawable bitmapdraw;
                            if(flag_findOrlost){
                                bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.findpet_marker1);
                            }else{
                                bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.lostpet_marker);
                            }

                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(markersLatLng);
                            markerOptions.title(lost_petname);
                            markerOptions.snippet(lost_petchr);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.findpet_marker1)); // 커스텀 이미지

                            Marker lostMarkers = mMap.addMarker(markerOptions);

                            lostMarkers.hideInfoWindow();
                            lostMarkers.setTag(lost_petsex);

                            mMap.setOnMarkerClickListener(markerClickListener); // 마커 클릭 이벤트


                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }


                //마커 클릭 이벤트
                GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        // 정보세팅
                        TextView petname = (TextView)getActivity().findViewById(R.id.info_petname); //펫 이름
                        TextView petsex = (TextView)getActivity().findViewById(R.id.info_petsex); // 펫 성별
                        TextView petichr = (TextView)getActivity().findViewById(R.id.info_petchr); // 펫 정보
                        ImageView imgview = (ImageView)getActivity().findViewById(R.id.imageView4);//이미지
                        Button btn_chat =  (Button) getActivity().findViewById(R.id.btn_chat);
                        btn_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), Chatroom2.class);
                                intent.putExtra("id", "관리자");
                                startActivity(intent);
                            }
                        });
                        petname.setText(marker.getTitle());
                        petsex.setText((String)marker.getTag());
                        petichr.setText(Html.fromHtml(marker.getSnippet().replaceAll("InE", "<br/>")));

                        //박스 이름
                        TextView boxname = (TextView)getActivity().findViewById(R.id.boxname);

                        String box = dismiss.get(Integer.parseInt(marker.getId().split("m")[1]));

                        if(box.equals("false")){
                            boxname.setText("실종신고 펫 정보");
                        }else if(box.equals("true")){
                            boxname.setText("발견신고 펫 정보");
                        }

                        String l_img = img.get(Integer.parseInt(marker.getId().split("m")[1]));

                        if(l_img.equals("null")) {
                            imgview.setImageResource(R.drawable.dogicon);
                        }else{
                            FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
                            StorageReference storageRef = firebaseStorage.getReference();
                            storageRef.child("lostpet/"+l_img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(FragmentLost.this).load(uri).into(imgview);
                                }
                            });
                        }




                        //펫 정보창 보여주기
                        LinearLayout petinfobox = (LinearLayout) mLayout.findViewById(R.id.petinfobox);
                        petinfobox.setVisibility(View.VISIBLE);

                        return true;
                    }
                };
            });


            //현재 위치 표시
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);

            if(mCurrentLocatiion ==  null || location.getLatitude() != mCurrentLocatiion.getLatitude() && location.getLongitude() != mCurrentLocatiion.getLongitude()){
                //현재 위치가 변했을때
                mMap.moveCamera(cameraUpdate);
                mCurrentLocatiion = location;
            }

        }


        public void setDefaultLocation() {


            //디폴트 위치, Seoul
            LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
            mMap.moveCamera(cameraUpdate);

        }


        //여기부터는 런타임 퍼미션 처리을 위한 메소드들
        private boolean checkPermission() {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity() ,Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                    hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
                return true;
            }

            return false;

        }



        /*
         * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
         */
        @Override
        public void onRequestPermissionsResult(int permsRequestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grandResults) {

            if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

                // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

                boolean check_result = true;


                // 모든 퍼미션을 허용했는지 체크합니다.

                for (int result : grandResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        check_result = false;
                        break;
                    }
                }


                if ( check_result ) {

                    // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                    startLocationUpdates();
                }
                else {
                    // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                            || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {


                        // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                        Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                                Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                getActivity().finish();
                            }
                        }).show();

                    }else {


                        // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                        Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                                Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                getActivity().finish();
                            }
                        }).show();
                    }
                }

            }
        }


        //여기부터는 GPS 활성화를 위한 메소드들
        private void showDialogForLocationServiceSetting() {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("위치 서비스 비활성화");
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                    + "위치를 서비스를 허용하시겠습니까?");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent callGPSSettingIntent
                            = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {

                case GPS_ENABLE_REQUEST_CODE:

                    //사용자가 GPS 활성 시켰는지 검사
                    if (checkLocationServicesStatus()) {
                        if (checkLocationServicesStatus()) {

                            Log.d(TAG, "onActivityResult : GPS 활성화 되있음");


                            needRequest = true;

                            return;
                        }
                    }

                    break;
            }
        }

    public void set_id(String id){
        this.id = id;
    }
}
