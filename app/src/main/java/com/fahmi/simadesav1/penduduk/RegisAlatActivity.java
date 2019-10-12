package com.fahmi.simadesav1.penduduk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.api.ServerApi;
import com.fahmi.simadesav1.session.SessionManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisAlatActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    Toolbar toolbar;
    Button btn_Cek, btn_Regis, btnCekLokasi;
    EditText edtKey, edtAlamat, edtLat, edtLng;
    ProgressBar Loading;
    String id_alat, sNik;
    SessionManager sessionManager;
    CameraPosition cameraPosition;
    Marker marker;
    private double mLatitude = 0;
    private double mLongitude = 0;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_alat);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtKey = (EditText) findViewById(R.id.kd_alat);
        edtAlamat = (EditText) findViewById(R.id.alamat_user);
        edtLat = (EditText) findViewById(R.id.edtlat);
        edtLng = (EditText) findViewById(R.id.edtlng);

        btnCekLokasi = (Button) findViewById(R.id.btnceklokasi);
        btn_Cek = (Button) findViewById(R.id.btnCek);
        btn_Regis = (Button) findViewById(R.id.btnRegisAlat);
        Loading = findViewById(R.id.loading);
        sessionManager = new SessionManager(this);
        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNik = userr.get(SessionManager.NIK);
        sNik = mNik;

        btn_Cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi_alat();
            }
        });

        btn_Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_stat_alat();
                register_alat();
            }
        });


    }

    private void validasi_alat() {
        Loading.setVisibility(View.VISIBLE);
        StringRequest masuk = new StringRequest(Request.Method.POST, ServerApi.URL_VALIDASI_ALAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("validate");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String Idalat = object.getString("id_alat");

                                    id_alat = Idalat;
                                    Loading.setVisibility(View.GONE);


                                    Toast.makeText(RegisAlatActivity.this, "Key ditemukan", Toast.LENGTH_SHORT).show();
                                    edtAlamat.setVisibility(View.VISIBLE);
                                    edtLat.setVisibility(View.VISIBLE);
                                    edtLng.setVisibility(View.VISIBLE);
                                    btnCekLokasi.setVisibility(View.VISIBLE);
                                    btn_Regis.setVisibility(View.VISIBLE);
                                    /* startActivity(new Intent(RegisAlatActivity.this, RegisterActivity.class));
                                     finish();*/
                                }


                            } else {
                                Loading.setVisibility(View.GONE);
                                Toast.makeText(RegisAlatActivity.this, "Key salah !!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(RegisAlatActivity.this, "Key error!", Toast.LENGTH_SHORT).show();
                            Loading.setVisibility(View.GONE);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Loading.setVisibility(View.GONE);
                        Toast.makeText(RegisAlatActivity.this, "gagal", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key_alat", edtKey.getText().toString());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(masuk);

    }

    public void register_alat() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan....");
        progressDialog.show();


        StringRequest simpan = new StringRequest(Request.Method.POST, ServerApi.URL_REGIS_ALAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject send = new JSONObject(response);
                            if (!send.getBoolean("error")) {
                                Toast.makeText(RegisAlatActivity.this, "error json", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        Toast.makeText(RegisAlatActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisAlatActivity.this, HomeActivity.class));
                        finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisAlatActivity.this, "gagal menambakan", Toast.LENGTH_SHORT).show();
                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id_alat", id_alat);
                map.put("nik", sNik);


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpan);
    }

    private void update_stat_alat() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATESTAT_ALAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(RegisAlatActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                               /* startActivity(new Intent(UMKMWargaTambahActivity.this, UMKMWargaActivity.class));
                                finish();*/
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(RegisAlatActivity.this, "error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisAlatActivity.this, "error2 " + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_alat", id_alat);
                params.put("lat", edtLat.getText().toString());
                params.put("lng", edtLng.getText().toString());


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void initMap() {
       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapz);
        mapFragment.getMapAsync(this);*/

//        if (mMap != null) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
        //mMap.setMyLocationEnabled(true);

//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            String provider = locationManager.getBestProvider(criteria, true);
//            Location location = locationManager.getLastKnownLocation(provider);
//
//            if (location != null) {
//                onLocationChanged(location);
//            }
//
//            locationManager.requestLocationUpdates(provider, 20000, 0, this);
    }

    public void geoLocate(View view) throws IOException {

        String location = edtAlamat.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();

        double lng = address.getLongitude();
//        goToLocationZoom(lat, lng, 15);
        String l = String.valueOf(lat);
        String lo = String.valueOf(lng);

        edtLat.setText(l);
        edtLng.setText(lo);


//        setMarker(locality, lat, lng);

    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    private void setMarker(String locality, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }


        MarkerOptions option = new MarkerOptions().title(locality).draggable(true).position(new LatLng(lat, lng)).snippet("here");
        marker = mMap.addMarker(option);


    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
