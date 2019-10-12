package com.fahmi.simadesav1.umum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.fahmi.simadesav1.R;

public class CariActivity extends AppCompatActivity {

    Toolbar toolbarCari;
    EditText edtCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);
        toolbarCari = findViewById(R.id.toolbarcari);
        setSupportActionBar(toolbarCari);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtCari = (EditText) findViewById(R.id.edtcari);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cari, menu);
        return true;
    }

    public void cari(MenuItem mi) {
        Intent go = new Intent(CariActivity.this, CariHasilActivity.class);
        go.putExtra("cari", edtCari.getText().toString());
        startActivity(go);

    }
}
