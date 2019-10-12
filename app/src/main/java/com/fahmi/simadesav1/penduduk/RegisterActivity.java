package com.fahmi.simadesav1.penduduk;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Toolbar rToolbar;
    TextView tt;
    EditText edtNIK, edtNama, edtAlamat, edtTempatLahir, edtTglLahir, edtJK, edtAgama, edtStatus, edtPekerjaan, edtKontak, edtUsername, edtPass1, edtPass2;
    Button btnSimpan;
    RadioGroup rJK, rStatus;
    RadioButton rbJK, rbStatus;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;
    int selectedId, selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(rToolbar);
        getSupportActionBar().setTitle("");
        tt = (TextView) findViewById(R.id.titlee);
        edtNIK = (EditText) findViewById(R.id.edtnik);
        edtNama = (EditText) findViewById(R.id.edtnama);
        edtAlamat = (EditText) findViewById(R.id.edtalamat);
        edtTempatLahir = (EditText) findViewById(R.id.edttmptlahir);
        edtTglLahir = (EditText) findViewById(R.id.edttgllahir);
        // edtJK=(EditText)findViewById(R.id.edtjk);
        edtAgama = (EditText) findViewById(R.id.edtagama);
        // edtStatus=(EditText)findViewById(R.id.edtstatus);
        edtPekerjaan = (EditText) findViewById(R.id.edtpekerjaan);
        edtKontak = (EditText) findViewById(R.id.edtkontak);
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPass1 = (EditText) findViewById(R.id.edtpass1);
        edtPass2 = (EditText) findViewById(R.id.edtpass2);
        btnSimpan = (Button) findViewById(R.id.btnsimipan);
        rJK = (RadioGroup) findViewById(R.id.rgJK);
        rStatus = (RadioGroup) findViewById(R.id.rgStatus);

        tt.setText("Register");


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
        edtTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedId = rJK.getCheckedRadioButtonId();
                selectedID = rStatus.getCheckedRadioButtonId();

                rbStatus = (RadioButton) findViewById(selectedID);
                rbJK = (RadioButton) findViewById(selectedId);
                if ((edtNIK.getText().toString().isEmpty()) ||
                        (edtNama.getText().toString().isEmpty()) ||
                        (edtAlamat.getText().toString().isEmpty()) ||
                        (edtTempatLahir.getText().toString().isEmpty()) ||
                        (edtTglLahir.getText().toString().isEmpty()) ||
                        (edtAgama.getText().toString().isEmpty()) ||
                        (edtPekerjaan.getText().toString().isEmpty()) ||
                        (edtKontak.getText().toString().isEmpty()) ||
                        (edtUsername.getText().toString().isEmpty()) ||
                        (edtPass1.getText().toString().isEmpty()) ||
                        (edtPass2.getText().toString().isEmpty())
                ) {
                    Toast.makeText(RegisterActivity.this, "Data tidak boleh kosong..", Toast.LENGTH_SHORT).show();

                } else if (!(edtPass1.getText().toString().equals(edtPass2.getText().toString()))) {
                    Toast.makeText(RegisterActivity.this, "Password tidak sama!!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(RegisterActivity.this, "Password sama..", Toast.LENGTH_SHORT).show();
                    register();
                }


            }
        });


    }

    public void updatetgl() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat formatfix = new SimpleDateFormat(format, Locale.US);
        edtTglLahir.setText(formatfix.format(calendar.getTime()));
    }

    public void register() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan....");
        progressDialog.show();


        StringRequest simpan = new StringRequest(Request.Method.POST, ServerApi.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject send = new JSONObject(response);
                            if (!send.getBoolean("error")) {
                                Toast.makeText(RegisterActivity.this, "error json", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        Toast.makeText(RegisterActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "gagal menambakan", Toast.LENGTH_SHORT).show();
                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nik", edtNIK.getText().toString());
                map.put("nama", edtNama.getText().toString());
                map.put("alamat", edtAlamat.getText().toString());
                map.put("tempat_lahir", edtTempatLahir.getText().toString());
                map.put("tgl_lahir", edtTglLahir.getText().toString());
                map.put("jk", rbJK.getText().toString());
                map.put("agama", edtAgama.getText().toString());
                map.put("status", rbStatus.getText().toString());
                map.put("pekerjaan", edtPekerjaan.getText().toString());
                map.put("kontak", edtKontak.getText().toString());
                map.put("username", edtUsername.getText().toString());
                map.put("password", edtPass2.getText().toString());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpan);
    }
}
