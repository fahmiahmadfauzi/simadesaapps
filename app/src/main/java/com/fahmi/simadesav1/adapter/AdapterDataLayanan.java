package com.fahmi.simadesav1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fahmi.simadesav1.DetailInfoDesaActivity;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.model.ModelDataInfoDesa;
import com.fahmi.simadesav1.model.ModelDataLayanan;
import com.fahmi.simadesav1.penduduk.InfoDesaActivity;
import com.fahmi.simadesav1.penduduk.InfoDesaDetailActivity;
import com.fahmi.simadesav1.penduduk.LayananDesaFormActivity;
import com.fahmi.simadesav1.umum.HomeUmumActivity;

import java.util.List;

public class AdapterDataLayanan extends RecyclerView.Adapter<AdapterDataLayanan.HolderData> {
    List<ModelDataLayanan> mListItems;
    Bitmap bitmap;
    Context context;

    public AdapterDataLayanan(Context context, List<ModelDataLayanan> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterDataLayanan.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_layanan, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterDataLayanan.HolderData holder, int position) {
        ModelDataLayanan mlist = mListItems.get(position);



        holder.tv_title.setText(mlist.getNama_layanan());
         holder.tv_keterangan.setText(mlist.getNama_layanan());
        //loading image
        //Glide.with(context).load(mlist.getFoto()).thumbnail(0.5f).transition(new DrawableTransitionOptions().crossFade()).into(holder.thubnail);
        // new GetImageFromURL(holder.thubnail).execute(mlist.getFoto());
        holder.md = mlist;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {


        TextView tv_title, tv_keterangan;
        ModelDataLayanan md;

        public HolderData(View v) {
            super(v);
            tv_title = v.findViewById(R.id.nama_layanan);
            tv_keterangan = v.findViewById(R.id.keterangan);




            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        Intent detail = new Intent(context, LayananDesaFormActivity.class);


                        detail.putExtra("id_kategori", md.getId_kategori());
                        detail.putExtra("nama_layanan", md.getNama_layanan());
                        detail.putExtra("keterangan", md.getKeterangan());
                        context.startActivity(detail);



                }
            });


        }
    }


}
