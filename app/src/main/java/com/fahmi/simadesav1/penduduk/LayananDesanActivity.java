package com.fahmi.simadesav1.penduduk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.adapter.AdapterDataInfoDesa;
import com.fahmi.simadesav1.adapter.AdapterDataLayanan;
import com.fahmi.simadesav1.api.ServerApi;
import com.fahmi.simadesav1.model.ModelDataInfoDesa;
import com.fahmi.simadesav1.model.ModelDataLayanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LayananDesanActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String TAG = LayananDesanActivity.class.getSimpleName();



    TableRow tSuratKTP, tSuratLaporan;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataLayanan> modelDataList;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_desan);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mRecyclerView = findViewById(R.id.recyclerTempp);
        modelDataList = new ArrayList<>();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataLayanan(LayananDesanActivity.this, modelDataList);
        mRecyclerView.setAdapter(mAdapter);
        getLayanan();




       /* tSuratKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "1";
                Intent go = new Intent(LayananDesanActivity.this, LayananDesaFormActivity.class);
                go.putExtra("kategori", id);

                startActivity(go);

            }
        });


        tSuratLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "2";
                Intent go = new Intent(LayananDesanActivity.this, LayananDesaFormActivity.class);
                go.putExtra("kategori", id);

                startActivity(go);

            }
        });*/
    }

    private void getLayanan() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_GET_LAYANAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("layanan");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelDataLayanan modelData = new ModelDataLayanan();

                                String id = object.getString("id_kategori");
                                String nama = object.getString("nama_layanan");
                                String ket = object.getString("keterangan");

                                modelData.setId_kategori(id);
                                modelData.setNama_layanan(nama);
                                modelData.setKeterangan(ket);
                                modelDataList.add(modelData);


                            }
                            mAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(LayananDesanActivity.this, "error reading detail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LayananDesanActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
