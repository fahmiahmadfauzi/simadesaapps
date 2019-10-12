package com.fahmi.simadesav1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.model.ModelDataInvestor;

import java.util.List;

public class AdapterDataInvestor extends RecyclerView.Adapter<AdapterDataInvestor.HolderData> {
    List<ModelDataInvestor> mListItems;
    Bitmap bitmap;
    Context context;

    public AdapterDataInvestor(Context context, List<ModelDataInvestor> items) {
        this.mListItems = items;
        this.context = context;
    }


    @Override
    public AdapterDataInvestor.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_investor, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterDataInvestor.HolderData holder, int position) {
        ModelDataInvestor mlist = mListItems.get(position);

        holder.tv_keterangan.setText(mlist.getKeterangan());
        holder.tv_nama.setText(mlist.getNama_investor());
        holder.tv_kontak.setText(mlist.getKontak());
        holder.md = mlist;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {


        TextView tv_nama, tv_keterangan, tv_kontak;
        ModelDataInvestor md;

        public HolderData(View v) {
            super(v);
            tv_nama = v.findViewById(R.id.tv_namainves);
            tv_keterangan = v.findViewById(R.id.tv_description);
            tv_kontak = v.findViewById(R.id.tv_kontak);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //    if (context.getClass().equals(InfoDesaActivity.class))
                /*    {
                        Intent detail = new Intent(context, InfoDesaDetailActivity.class);

                        // detail.putExtra("klik",1);
                        detail.putExtra("id_info", md.getId_info());
                        detail.putExtra("judul", md.getJudul());
                        detail.putExtra("deskripsi", md.getDeskripsi());
                        detail.putExtra("foto", md.getFoto());
                        detail.putExtra("createdat",md.getCreatedat());
                        // sessionManager.createSessionIdToko(md.getId_info());
                        context.startActivity(detail);
                    }
                    else if (context.getClass().equals(HomeUmumActivity.class))
                    {
                        Intent detail = new Intent(context, DetailInfoDesaActivity.class);

                        // detail.putExtra("klik",1);
                        detail.putExtra("id_info", md.getId_info());
                        detail.putExtra("judul", md.getJudul());
                        detail.putExtra("deskripsi", md.getDeskripsi());
                        detail.putExtra("foto", md.getFoto());
                        detail.putExtra("createdat",md.getCreatedat());
                        // sessionManager.createSessionIdToko(md.getId_info());
                        context.startActivity(detail);
                    }*/


                }
            });


        }
    }


}