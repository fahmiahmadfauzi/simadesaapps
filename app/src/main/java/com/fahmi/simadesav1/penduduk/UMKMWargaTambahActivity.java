package com.fahmi.simadesav1.penduduk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UMKMWargaTambahActivity extends AppCompatActivity {
    private static final String TAG = UMKMWargaTambahActivity.class.getSimpleName();
    Toolbar toolbar;
    EditText edtNik, edtNamausaha, edtDes;
    Button btnSave, btnF, saveEdt, btnHps;
    SessionManager sessionManager;
    ImageView FotoF;
    String idumkm;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umkmwarga_tambah);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        edtNik = (EditText) findViewById(R.id.edtnik);
        edtNamausaha = (EditText) findViewById(R.id.edtnamausaha);
        edtDes = (EditText) findViewById(R.id.edtdes);
        btnSave = (Button) findViewById(R.id.btnsave);
        btnF = (Button) findViewById(R.id.btnf);
        FotoF = (ImageView) findViewById(R.id.fotof);
        saveEdt = (Button) findViewById(R.id.btnsaveedit);
        btnHps = (Button) findViewById(R.id.btnhapus);


        sessionManager = new SessionManager(this);
        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNik = userr.get(SessionManager.NIK);
        String mId = userr.get(SessionManager.ID_AKUN);
        edtNik.setText(mNik);

        Intent data = getIntent();
        final int intent_update = data.getIntExtra("update", 0);

        String intent_id_umkm = data.getStringExtra("id_umkm");
        String intent_nik = data.getStringExtra("nik");
        String intent_namausaha = data.getStringExtra("namausaha");
        String intent_des = data.getStringExtra("deskripsi");
        idumkm = intent_id_umkm;

        if (intent_update == 1) {

            edtNik.setText(intent_nik);
            edtNamausaha.setText(intent_namausaha);
            edtDes.setText(intent_des);
            btnHps.setVisibility(View.VISIBLE);
            saveEdt.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
            btnF.setVisibility(View.VISIBLE);


        }


        saveEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();


            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(UMKMWargaTambahActivity.this, "terupload", Toast.LENGTH_SHORT).show();
                chooseFile();
            }
        });
        btnHps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus(idumkm);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_umkm();
                //chooseFile();

            }
        });


    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "Gambar berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                //   foto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            UploadPicture(idumkm, getStringImage(bitmap));

        }
    }

    private void UploadPicture(final String id, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();

        StringRequest upload = new StringRequest(Request.Method.POST, ServerApi.URL_FOTO_UMKM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(EditAkunActivity.this, "masuk", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(UMKMWargaTambahActivity.this, "gambar berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                               /* startActivity(new Intent(EditAkunActivity.this, PengaturanActivity.class));
                                finish();*/
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UMKMWargaTambahActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                            // Toast.makeText(EditAkunActivity.this, "gagal error"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //  Toast.makeText(EditAkunActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(UMKMWargaTambahActivity.this, "jaringan buruk", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_umkm", id);
                params.put("photo", photo);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(upload);

    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }


    public void tambah_umkm() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan....");
        progressDialog.show();


        StringRequest simpan = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_UMKM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject send = new JSONObject(response);
                            if (!send.getBoolean("error")) {
                                Toast.makeText(UMKMWargaTambahActivity.this, "error json", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        Toast.makeText(UMKMWargaTambahActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UMKMWargaTambahActivity.this, UMKMWargaActivity.class));
                        finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UMKMWargaTambahActivity.this, "gagal menambakan", Toast.LENGTH_SHORT).show();
                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nik", edtNik.getText().toString());
                map.put("nama_usaha", edtNamausaha.getText().toString());
                map.put("deskripsi", edtNamausaha.getText().toString());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpan);
    }


    private void saveEdit() {

        final String nama = edtNamausaha.getText().toString();
        final String des = edtDes.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_EDIT_UMKM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(UMKMWargaTambahActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UMKMWargaTambahActivity.this, UMKMWargaActivity.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UMKMWargaTambahActivity.this, "error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UMKMWargaTambahActivity.this, "error2 " + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_usaha", nama);
                params.put("deskripsi", des);
                params.put("id_umkm", idumkm);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void hapus(final String id_alat) {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_UMKM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(UMKMWargaTambahActivity.this, "berhasil menghapus", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UMKMWargaTambahActivity.this, UMKMWargaActivity.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UMKMWargaTambahActivity.this, "error" + e.toString(), Toast.LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UMKMWargaTambahActivity.this, "error2 " + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_umkm", id_alat);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
