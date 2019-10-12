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
import com.fahmi.simadesav1.penduduk.InfoDesaActivity;
import com.fahmi.simadesav1.penduduk.InfoDesaDetailActivity;
import com.fahmi.simadesav1.umum.HomeUmumActivity;

import java.util.List;

public class AdapterDataInfoDesa extends RecyclerView.Adapter<AdapterDataInfoDesa.HolderData> {
    List<ModelDataInfoDesa> mListItems;
    Bitmap bitmap;
    Context context;

    public AdapterDataInfoDesa(Context context, List<ModelDataInfoDesa> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterDataInfoDesa.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_infodesa, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterDataInfoDesa.HolderData holder, int position) {
        ModelDataInfoDesa mlist = mListItems.get(position);

        if (mlist.getDeskripsi().length() >= 0){
            if (mlist.getDeskripsi().length()>100){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.tv_keterangan.setText(Html.fromHtml(mlist.getDeskripsi().substring(0,50)+"...", Html.FROM_HTML_MODE_COMPACT));
                } else {
                    holder.tv_keterangan.setText(Html.fromHtml(mlist.getDeskripsi()));
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.tv_keterangan.setText(Html.fromHtml(mlist.getDeskripsi(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    holder.tv_keterangan.setText(Html.fromHtml(mlist.getDeskripsi()));
                }
            }
        }

        holder.tv_title.setText(mlist.getJudul());
       // holder.tv_keterangan.setText(mlist.getDeskripsi());
        //loading image
        Glide.with(context).load(mlist.getFoto()).thumbnail(0.5f).transition(new DrawableTransitionOptions().crossFade()).into(holder.thubnail);
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
        ModelDataInfoDesa md;

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
                        detail.putExtra("id_info", md.getId_info());
                        detail.putExtra("judul", md.getJudul());
                        detail.putExtra("deskripsi", md.getDeskripsi());
                        detail.putExtra("foto", md.getFoto());
                        detail.putExtra("createdat", md.getCreatedat());
                        // sessionManager.createSessionIdToko(md.getId_info());
                        context.startActivity(detail);
                    } else if (context.getClass().equals(HomeUmumActivity.class)) {
                        Intent detail = new Intent(context, DetailInfoDesaActivity.class);

                        // detail.putExtra("klik",1);
                        detail.putExtra("id_info", md.getId_info());
                        detail.putExtra("judul", md.getJudul());
                        detail.putExtra("deskripsi", md.getDeskripsi());
                        detail.putExtra("foto", md.getFoto());
                        detail.putExtra("createdat", md.getCreatedat());
                        // sessionManager.createSessionIdToko(md.getId_info());
                        context.startActivity(detail);
                    }


                }
            });


        }
    }


}
