package com.fahmi.simadesav1.penduduk;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LayananDesaFormActivity extends AppCompatActivity {

    EditText edtKateg, edtNik, edtTgl, edtKet;
    Button btnSimpn;
    String idkategori, namalayanan, ketlayanan;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_desa_form);

        edtKateg = (EditText) findViewById(R.id.edtidkategori);
        edtNik = (EditText) findViewById(R.id.edtnik);
        edtTgl = (EditText) findViewById(R.id.edttgl);
        edtKet = (EditText) findViewById(R.id.edtket);
        btnSimpn = (Button) findViewById(R.id.btnsave);

        Intent got = getIntent();
        idkategori = got.getStringExtra("id_kategori");
        namalayanan = got.getStringExtra("nama_layanan");
        ketlayanan = got.getStringExtra("deskripsi");

        edtKateg.setText(idkategori);
        sessionManager = new SessionManager(this);
        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNik = userr.get(SessionManager.NIK);
        edtNik.setText(mNik);


        btnSimpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_layanan();

            }
        });

        calendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatetgl();
            }
        };
        edtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LayananDesaFormActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    public void updatetgl() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat formatfix = new SimpleDateFormat(format, Locale.US);
        edtTgl.setText(formatfix.format(calendar.getTime()));
    }

    public void tambah_layanan() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan....");
        progressDialog.show();


        StringRequest simpan = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_LAYANAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject send = new JSONObject(response);
                            if (!send.getBoolean("error")) {
                                Toast.makeText(LayananDesaFormActivity.this, "error json", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        Toast.makeText(LayananDesaFormActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LayananDesaFormActivity.this, LayananDesanActivity.class));
                        finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LayananDesaFormActivity.this, "gagal menambakan", Toast.LENGTH_SHORT).show();
                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nik", edtNik.getText().toString());
                map.put("id_kategori", edtKateg.getText().toString());
                map.put("created", edtTgl.getText().toString());
                map.put("ket_layanan", edtKet.getText().toString());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpan);
    }


}
