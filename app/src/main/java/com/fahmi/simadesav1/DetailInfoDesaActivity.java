package com.fahmi.simadesav1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DetailInfoDesaActivity extends AppCompatActivity {

    TextView tJudul, tDes, tCreat;
    ImageView img;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info_desa);
        tJudul = (TextView) findViewById(R.id.judul);
        tDes = (TextView) findViewById(R.id.deskripsi);
        img = (ImageView) findViewById(R.id.cover);
        tCreat = (TextView) findViewById(R.id.creat);

        Intent data = getIntent();
        String intent_id_info = data.getStringExtra("id_info");
        String intent_judul = data.getStringExtra("judul");
        String intent_deskripsi = data.getStringExtra("deskripsi");
        String intent_created = data.getStringExtra("createdat");
        String intent_foto = data.getStringExtra("foto");

        tJudul.setText(intent_judul);
        tDes.setText(intent_deskripsi);
        tCreat.setText(intent_created);

        new GetImageFromURL(img).execute(intent_foto);

    }

    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imgV;

        public GetImageFromURL(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            img = null;
            try {
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(srt);

            } catch (EOFException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgV.setImageBitmap(bitmap);
        }
    }
}
