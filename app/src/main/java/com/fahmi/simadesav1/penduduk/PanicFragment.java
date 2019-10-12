package com.fahmi.simadesav1.penduduk;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.api.ServerApi;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PanicFragment extends Fragment {
    Button btnPanic;


    ProgressDialog progressDialog;


    public PanicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View panic = inflater.inflate(R.layout.fragment_panic, container, false);

        btnPanic = (Button) panic.findViewById(R.id.btnsendaall);

        btnPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMultiplePush();
            }
        });


        return panic;
    }

    public void sendMultiplePush() {

        final String title = "Pemberitahuan Darurat";
        final String message = "Butuh Pertolongan";
        final String image = "";
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_SEND_MULTIPLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);
                return params;
            }
        };

        MyVolley.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

}
