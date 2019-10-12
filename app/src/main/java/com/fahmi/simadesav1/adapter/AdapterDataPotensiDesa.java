package com.fahmi.simadesav1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.model.ModelDataPotensiDesa;
import com.fahmi.simadesav1.penduduk.InfoDesaActivity;
import com.fahmi.simadesav1.penduduk.InfoDesaDetailActivity;

import java.util.List;

public class AdapterDataPotensiDesa extends RecyclerView.Adapter<AdapterDataPotensiDesa.HolderData> {
    List<ModelDataPotensiDesa> mListItems;
    Bitmap bitmap;
    Context context;

    public AdapterDataPotensiDesa(Context context, List<ModelDataPotensiDesa> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterDataPotensiDesa.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_potensidesa, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterDataPotensiDesa.HolderData holder, int position) {
        ModelDataPotensiDesa mlist = mListItems.get(position);

        holder.tv_title.setText(mlist.getNama_potensi());
        holder.tv_keterangan.setText(mlist.getDeskripsi());
        //loading image
        Glide.with(context).load(mlist.getImg()).thumbnail(0.5f).transition(new DrawableTransitionOptions().crossFade()).into(holder.thubnail);
        // new GetImageFromURL(holder.thubnail).execute(mlist.getFoto());
        holder.md = mlist;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {


        ImageView thubnail;
        TextView tv_title, tv_keterangan;
        ModelDataPotensiDesa md;

        public HolderData(View v) {
            super(v);
            thubnail = v.findViewById(R.id.img_info);
            tv_title = v.findViewById(R.id.tv_title);
            tv_keterangan = v.findViewById(R.id.tv_description);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (context.getClass().equals(InfoDesaActivity.class)) {
                        Intent detail = new Intent(context, InfoDesaDetailActivity.class);

                        // detail.putExtra("klik",1);
                        detail.putExtra("id_info", md.getId_potensi());
                        detail.putExtra("jenis_potensi", md.getJenis_potensi());
                        detail.putExtra("nama_potensi", md.getNama_potensi());
                        detail.putExtra("deskripsi", md.getDeskripsi());
                        detail.putExtra("img", md.getImg());
                        // sessionManager.createSessionIdToko(md.getId_info());
                        context.startActivity(detail);
                    }


                }
            });


        }
    }


}