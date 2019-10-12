package com.fahmi.simadesav1.penduduk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UsulanWargaTambahActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtNIK, edtUsulan;
    Button btnSimpanUsul;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usulan_warga_tambah);

        edtNIK = (EditText) findViewById(R.id.edtnik);
        edtUsulan = (EditText) findViewById(R.id.edtusulan);
        btnSimpanUsul = (Button) findViewById(R.id.btnsimpanusulan);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        sessionManager = new SessionManager(this);
        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNik = userr.get(SessionManager.NIK);
        String mId = userr.get(SessionManager.ID_AKUN);
        edtNIK.setText(mNik);

        btnSimpanUsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_usulan();
            }
        });


    }

    public void tambah_usulan() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan....");
        progressDialog.show();


        StringRequest simpan = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_USULAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject send = new JSONObject(response);
                            if (!send.getBoolean("error")) {
                                Toast.makeText(UsulanWargaTambahActivity.this, "error json", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        Toast.makeText(UsulanWargaTambahActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UsulanWargaTambahActivity.this, UsulanWargaActivity.class));
                        finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UsulanWargaTambahActivity.this, "gagal menambakan", Toast.LENGTH_SHORT).show();
                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nik", edtNIK.getText().toString());
                map.put("usulan", edtUsulan.getText().toString());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpan);
    }
}
