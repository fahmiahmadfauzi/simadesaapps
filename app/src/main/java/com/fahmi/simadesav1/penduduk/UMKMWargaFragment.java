package com.fahmi.simadesav1.penduduk;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.adapter.AdapterDataUMKM;
import com.fahmi.simadesav1.api.ServerApi;
import com.fahmi.simadesav1.model.ModelDataUMKM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UMKMWargaFragment extends Fragment {

    private static final String TAG = UMKMWargaFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataUMKM> modelDataList;
    RecyclerView.LayoutManager manager;

    public UMKMWargaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View umkm = inflater.inflate(R.layout.fragment_umkmwarga, container, false);
        mRecyclerView = (RecyclerView) umkm.findViewById(R.id.recyclerTempp);
        modelDataList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataUMKM(getActivity(), modelDataList);
        mRecyclerView.setAdapter(mAdapter);
        getUMKM();


        return umkm;
    }

    private void getUMKM() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_GET_UMKMWARGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("umkm");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelDataUMKM modelData = new ModelDataUMKM();

                                String Id = object.getString("id_umkm");
                                String NIK = object.getString("nik");
                                String Namausaha = object.getString("nama_usaha");
                                String Des = object.getString("deskripsi");
                                String Img = object.getString("image");

                                modelData.setId_umkm(Id);
                                modelData.setNik(NIK);
                                modelData.setNamausaha(Namausaha);
                                modelData.setDeskripsi(Des);
                                modelData.setImg(Img);

                                modelDataList.add(modelData);


                            }
                            mAdapter.notifyDataSetChanged();
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


}
