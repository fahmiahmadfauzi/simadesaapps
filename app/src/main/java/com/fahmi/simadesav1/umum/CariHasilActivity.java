package com.fahmi.simadesav1.umum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CariHasilActivity extends AppCompatActivity {
    private static final String TAG = CariHasilActivity.class.getSimpleName();
    Toolbar toolbar;
    String scari;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataUMKM> modelDataList;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_hasil);

        toolbar = (Toolbar) findViewById(R.id.toolbarcari);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent data = getIntent();
        scari = data.getStringExtra("cari");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerTempp);
        modelDataList = new ArrayList<>();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataUMKM(CariHasilActivity.this, modelDataList);
        mRecyclerView.setAdapter(mAdapter);
        getCari();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cari, menu);
        return true;
    }

    public void cari(MenuItem mi) {
        Intent go = new Intent(CariHasilActivity.this, CariActivity.class);
        startActivity(go);
        finish();

    }

    private void getCari() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_CARI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("cari");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelDataUMKM modelData = new ModelDataUMKM();

                                String Id = object.getString("id_umkm");
                                String Nik = object.getString("nik");
                                String Namausaha = object.getString("nama_usaha");
                                String Dess = object.getString("deskripsi");

                                String Img = object.getString("image");


                                modelData.setId_umkm(Id);
                                modelData.setNik(Nik);
                                modelData.setNamausaha(Namausaha);
                                modelData.setDeskripsi(Dess);
                                modelData.setImg(Img);

                                modelDataList.add(modelData);


                            }
                            mAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(CariHasilActivity.this, "error reading detail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CariHasilActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("cari", scari);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
