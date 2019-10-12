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

import com.android.volley.AuthFailureError;
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
import com.fahmi.simadesav1.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UMKMAndaFragment extends Fragment {

    private static final String TAG = UMKMAndaFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataUMKM> modelDataList;
    RecyclerView.LayoutManager manager;
    SessionManager sessionManager;
    String sNik;

    public UMKMAndaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View umkm = inflater.inflate(R.layout.fragment_umkmanda, container, false);
        mRecyclerView = (RecyclerView) umkm.findViewById(R.id.recyclerTempp);
        modelDataList = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataUMKM(getActivity(), modelDataList);
        mRecyclerView.setAdapter(mAdapter);
        sessionManager = new SessionManager(getContext());
        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNik = userr.get(SessionManager.NIK);
        sNik = mNik;
        getUMKM();
        return umkm;
    }

    private void getUMKM() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_GET_UMKM_ANDA,
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
                            //   Toast.makeText(getContext(), "kosong" + e.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Anda tidak punya umkm", Toast.LENGTH_SHORT).show();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("nik", sNik);
                return param;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
