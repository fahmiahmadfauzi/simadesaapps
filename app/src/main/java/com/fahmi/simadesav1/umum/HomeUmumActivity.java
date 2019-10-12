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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.adapter.AdapterDataInfoDesa;
import com.fahmi.simadesav1.api.ServerApi;
import com.fahmi.simadesav1.model.ModelDataInfoDesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeUmumActivity extends AppCompatActivity {
    private static final String TAG = HomeUmumActivity.class.getSimpleName();
    Toolbar toolbarCari;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<ModelDataInfoDesa> modelDataList;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_umum);

        toolbarCari = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarCari);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerTemp);
        modelDataList = new ArrayList<>();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AdapterDataInfoDesa(HomeUmumActivity.this, modelDataList);
        mRecyclerView.setAdapter(mAdapter);
        getInfoDesa();
    }


   /* public void cari(MenuItem mi) {
        String sCari = edPencarian.getText().toString();

        Intent go = new Intent(CariActivity.this, CariHasilActivity.class);
        go.putExtra("pencarian", sCari);
        startActivity(go);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cari, menu);
        return true;
    }

    public void cari(MenuItem mi) {
        startActivity(new Intent(HomeUmumActivity.this, CariActivity.class));

    }


    private void getInfoDesa() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_GET_INFODESA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getObject = jsonObject.getString("info");
                            JSONArray jsonArray = new JSONArray(getObject);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelDataInfoDesa modelData = new ModelDataInfoDesa();

                                String Judul = object.getString("judul");
                                String Deskripsi = object.getString("deskripsi");
                                String Foto = object.getString("image");
                                String Id_Info = object.getString("id_info");
                                String Created_At = object.getString("created_at");
                                String linkfoto = "http://simadesa.desa-babakanasem.id/src/info_desa/" + Foto;

                                modelData.setId_info(Id_Info);
                                modelData.setJudul(Judul);
                                modelData.setDeskripsi(Deskripsi);
                                modelData.setFoto(linkfoto);
                                modelData.setCreatedat(Created_At);

                                modelDataList.add(modelData);


                            }
                            mAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HomeUmumActivity.this, "error reading detail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeUmumActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
