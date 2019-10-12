package com.fahmi.simadesav1.penduduk;


import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.api.ServerApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latlng;
    double lat, lng;
    String dess;
    private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mp = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        getmitigasi();

        return mp;
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

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        center = new LatLng(-6.886427, 107.613604);
        cameraPosition = new CameraPosition.Builder().target(center).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // latlng = new LatLng(-6.8952, 107.62668);
        //  addMarker(latlng, "gagak");


        if (mMap != null) {


            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                //kondisi saat marker selesai di drag
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc = new Geocoder(getContext());
                    LatLng ll = marker.getPosition();
                    double lat = ll.latitude;
                    double lng = ll.longitude;
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();
                }
            });
            //menampilkan informasi
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView tvlocality = v.findViewById(R.id.tv_locality);
                    TextView tvlat = v.findViewById(R.id.tv_lat);
                    TextView tvlng = v.findViewById(R.id.tv_lng);
                    TextView tvsnippet = v.findViewById(R.id.tv_snippet);
                    TextView tvket = v.findViewById(R.id.tv_ket);

                    LatLng ll = marker.getPosition();
                    tvlocality.setText(marker.getTitle());
                    tvlat.setText("Latitude :" + ll.latitude);
                    tvlng.setText("Longitude :" + ll.longitude);
                    tvsnippet.setText(marker.getSnippet());
                    return v;

                }
            });
        }

        //    getMarkers();
        // Add a marker in Sydney and move the camera
//           LatLng sydney = new LatLng(-34, 151);
//           mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//           mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }


    private void getmitigasi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_GET_MITIGASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("mitigasi");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                /*ModelData modelData = new ModelData();*/

                                String IdToko = object.getString("id_mitigasi");
                                String Nama = object.getString("nama_lokasi");
                                String la = object.getString("lat");
                                String ln = object.getString("lon");
                                String Keterangan = object.getString("keterangan");

                                lat = Double.parseDouble(object.getString("lat"));
                                lng = Double.parseDouble(object.getString("lon"));

                                latlng = new LatLng(Double.parseDouble(object.getString("lat")), Double.parseDouble(object.getString("lon")));
                                addMarker(latlng, Nama);


                              /*  modelData.setId_toko(IdToko);
                                modelData.setNama(Nama);
                                modelData.setAlamat(Alamat);
                                modelData.setFoto(Foto);


                                modelDataList.add(modelData);*/


                            }
                            /*mAdapter.notifyDataSetChanged();*/
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "error reading detail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void addMarker(LatLng latlng, final String title) {


        markerOptions.position(latlng);
        markerOptions.title(title);
        mMap.addMarker(markerOptions);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

