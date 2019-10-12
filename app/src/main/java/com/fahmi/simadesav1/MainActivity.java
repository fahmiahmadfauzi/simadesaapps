package com.fahmi.simadesav1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fahmi.simadesav1.penduduk.LoginActivity;
import com.fahmi.simadesav1.session.SessionManager;
import com.fahmi.simadesav1.umum.HomeUmumActivity;

public class MainActivity extends AppCompatActivity {
    Button btnPenduduk, btnUmum;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        btnPenduduk = (Button) findViewById(R.id.penduduk);
        btnUmum = (Button) findViewById(R.id.umum);


        btnPenduduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });


        btnUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeUmumActivity.class));

            }
        });
    }
}
