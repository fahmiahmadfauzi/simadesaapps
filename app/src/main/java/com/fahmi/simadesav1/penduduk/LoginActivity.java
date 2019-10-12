package com.fahmi.simadesav1.penduduk;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.fahmi.simadesav1.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String URL_REGISTER_DEVICE = "http://app.desa-babakanasem.id/FcmExample/RegisterDevice.php";
    TextView linkregister;
    EditText edtUsername, edtPassword;
    Button btnLogin;
    SessionManager sessionManager;
    String Nik;

    ProgressBar Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        linkregister = (TextView) findViewById(R.id.register);
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        btnLogin = (Button) findViewById(R.id.btnlogin);


        Loading = findViewById(R.id.loading);


        linkregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
    }


    private void login() {
        Loading.setVisibility(View.VISIBLE);
        StringRequest masuk = new StringRequest(Request.Method.POST, ServerApi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String NIK = object.getString("nik");
                                    String Id = object.getString("id_akun");
                                    sessionManager.createSession(NIK, Id);
                                    Nik = NIK;
                                    //  sessionManager.createSession(Nama, Alamat, Email, Username, Password, JenisKelamin, GrupUser, NIK, Telp, Foto, Id);
                                 /*   Intent ParsingToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                    ParsingToHome.putExtra(key_nama, Nama);
                                    ParsingToHome.putExtra(key_alamat, Alamat);
                                    ParsingToHome.putExtra(key_email, Email);
                                    ParsingToHome.putExtra(key_username, Username);
                                    ParsingToHome.putExtra(key_password, Password);
                                    ParsingToHome.putExtra(key_nik, NIK);
                                    ParsingToHome.putExtra(key_telp, Telp);
                                    ParsingToHome.putExtra(key_jk, JenisKelamin);
                                    ParsingToHome.putExtra(key_foto, Foto);
                                    ParsingToHome.putExtra(key_grup_user, GrupUser);
                                    startActivity(ParsingToHome);*/
                                   /* if (GrupUser.equals("admin")){
                                        startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class));
                                    }else if (GrupUser.equals("customer")){
                                      startActivity(new Intent(LoginActivity.this, HomeUserActivity.class));
                                    }*/
                                    sendTokenToServer();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();

                                    Loading.setVisibility(View.GONE);


                                    // Toast.makeText(MainActivity.this, "Success Login. \n name :"+nam+"\n email :"+emai, Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Loading.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Username dan Password salah !!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "gagal login error!", Toast.LENGTH_SHORT).show();
                            Loading.setVisibility(View.GONE);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Loading.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "gagal login", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", edtUsername.getText().toString());
                map.put("password", edtPassword.getText().toString());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(masuk);

    }

    private void sendTokenToServer() {
       /* progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();
*/
        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String ni = Nik;

        if (token == null) {
            //  progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nik", ni);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
