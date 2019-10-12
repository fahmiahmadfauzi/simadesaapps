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
import com.fahmi.simadesav1.model.ModelDataUMKM;
import com.fahmi.simadesav1.penduduk.UMKMWargaTambahActivity;
import com.fahmi.simadesav1.session.SessionManager;

import java.util.HashMap;
import java.util.List;

public class AdapterDataUMKM extends RecyclerView.Adapter<AdapterDataUMKM.HolderData> {
    List<ModelDataUMKM> mListItems;
    Bitmap bitmap;
    Context context;
    SessionManager sessionManager;

    public AdapterDataUMKM(Context context, List<ModelDataUMKM> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterDataUMKM.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_umkm, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterDataUMKM.HolderData holder, int position) {
        ModelDataUMKM mlist = mListItems.get(position);

        holder.tv_title.setText(mlist.getNamausaha());
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
        ModelDataUMKM md;

        public HolderData(View v) {
            super(v);
            thubnail = v.findViewById(R.id.img_info);
            tv_title = v.findViewById(R.id.tv_title);
            tv_keterangan = v.findViewById(R.id.tv_description);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sessionManager = new SessionManager(context);
                    HashMap<String, String> userr = sessionManager.getUserDetail();
                    String mNama = userr.get(SessionManager.NIK);

                    if (md.getNik().equals(mNama)) {

                        //if (context.getApplicationContext().equals(UMKMWargaFragment.class)) {
                        // Toast.makeText(context, "tes", Toast.LENGTH_SHORT).show();
                        Intent detail = new Intent(context, UMKMWargaTambahActivity.class);
                        // detail.putExtra("klik",1);
                        detail.putExtra("update", 1);
                        detail.putExtra("id_umkm", md.getId_umkm());
                        detail.putExtra("namausaha", md.getNamausaha());
                        detail.putExtra("deskripsi", md.getDeskripsi());
                        detail.putExtra("foto", md.getImg());
                        detail.putExtra("nik", md.getNik());
                        // sessionManager.createSessionIdToko(md.getId_info());
                        context.startActivity(detail);

                    }


                }
            });


        }
    }
}
