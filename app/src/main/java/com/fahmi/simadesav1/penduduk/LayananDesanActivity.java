package com.fahmi.simadesav1.penduduk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableRow;

import com.fahmi.simadesav1.R;

public class LayananDesanActivity extends AppCompatActivity {
    Toolbar toolbar;

    TableRow tSuratKTP, tSuratLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_desan);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        tSuratKTP = (TableRow) findViewById(R.id.tsuratktp);
        tSuratLaporan = (TableRow) findViewById(R.id.tsuratlaporan);


        tSuratKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "1";
                Intent go = new Intent(LayananDesanActivity.this, LayananDesaFormActivity.class);
                go.putExtra("kategori", id);

                startActivity(go);

            }
        });


        tSuratLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "2";
                Intent go = new Intent(LayananDesanActivity.this, LayananDesaFormActivity.class);
                go.putExtra("kategori", id);

                startActivity(go);

            }
        });
    }
}
