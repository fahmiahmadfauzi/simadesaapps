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
import com.fahmi.simadesav1.adapter.AdapterDataUsulan;
import com.fahmi.simadesav1.api.ServerApi;
import com.fahmi.simadesav1.model.ModelDataUsulan;
import com.fahmi.simadesav1.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsulanWargaRiwayatFragment extends Fragment {

    private static final String TAG = UsulanWargaRiwayatFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataUsulan> modelDataList;
    RecyclerView.LayoutManager manager;
    SessionManager sessionManager;


    public UsulanWargaRiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View riwayat_usulan = inflater.inflate(R.layout.fragment_usulan_warga_riwayat, container, false);
        mRecyclerView = (RecyclerView) riwayat_usulan.findViewById(R.id.recyclerTempp);
        modelDataList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sessionManager = new SessionManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataUsulan(getActivity(), modelDataList);
        mRecyclerView.setAdapter(mAdapter);
        getUsulanSemua();
        return riwayat_usulan;
    }

    private void getUsulanSemua() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_USULAN_WARGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("usulan");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelDataUsulan modelData = new ModelDataUsulan();

                                String Id = object.getString("id_usulan");
                                String NIK = object.getString("nik");
                                String Usulan = object.getString("usulan");
                                String Status = object.getString("status");

                                modelData.setId_usulan(Id);
                                modelData.setNik(NIK);
                                modelData.setUsulan(Usulan);
                                modelData.setStatus(Status);

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
