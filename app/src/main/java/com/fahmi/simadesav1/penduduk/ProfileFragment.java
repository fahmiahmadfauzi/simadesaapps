package com.fahmi.simadesav1.penduduk;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextView tNik, tNama, tAlamat, tTempatlahir, tt;
    SessionManager sessionManager;
    String mNik;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View p = inflater.inflate(R.layout.fragment_profile, container, false);

        tNik = (TextView) p.findViewById(R.id.nik);
        tNama = (TextView) p.findViewById(R.id.nama_penduduk);
        tAlamat = (TextView) p.findViewById(R.id.alamat);


        tTempatlahir = (TextView) p.findViewById(R.id.tempatlahir);
        sessionManager = new SessionManager(getContext());
        HashMap<String, String> userr = sessionManager.getUserDetail();
        mNik = userr.get(SessionManager.NIK);
        //edtNik.setText(mNik);
        load();
//        tt.setText("");

        return p;
    }


    private void load() {
        //Loading.setVisibility(View.VISIBLE);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil...");
        progressDialog.show();
        StringRequest masuk = new StringRequest(Request.Method.POST, ServerApi.URL_GET_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String NIK = object.getString("nik");
                                    String Nama = object.getString("nama");
                                    String Alamat = object.getString("alamat");
                                    String Tempatlahir = object.getString("tempat_lahir");
                                    String Tgllahir = object.getString("tgl_lahir");
                                    String Pekerjaan = object.getString("pekerjaan");


                                    tNik.setText(NIK);
                                    tNama.setText(Nama);
                                    tAlamat.setText(Alamat);
                                    tTempatlahir.setText(Tempatlahir);
                                    //    sessionManager.createSession(NIK,Id);
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

                                    progressDialog.dismiss();
                                    // Toast.makeText(MainActivity.this, "Success Login. \n name :"+nam+"\n email :"+emai, Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                // Loading.setVisibility(View.GONE);
                                //       Toast.makeText(getContext(), "Username dan Password salah !!!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            progressDialog.dismiss();
                            //    Toast.makeText(getContext(), "gagal login error!", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Loading.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nik", mNik);


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(masuk);

    }

}
