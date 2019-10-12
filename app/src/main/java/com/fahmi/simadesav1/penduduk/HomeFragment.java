package com.fahmi.simadesav1.penduduk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.session.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    GridLayout gridLayout;
    SessionManager sessionManager;

    public HomeFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        sessionManager = new SessionManager(getContext());
        gridLayout = (GridLayout) home.findViewById(R.id.mainGrid);
        setSingleEvent(gridLayout);
        return home;
    }

    private void setSingleEvent(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Toast.makeText(MainActivity.this,"Clicked at index "+ finalI,
                            Toast.LENGTH_SHORT).show();*/

                    if (finalI == 0) {
                        Intent infodesa = new Intent(getContext(), InfoDesaActivity.class);
                        startActivity(infodesa);
                    } else if (finalI == 1) {
                        Intent usulan = new Intent(getContext(), UsulanWargaActivity.class);
                        startActivity(usulan);
                    } else if (finalI == 2) {
                        Intent prestasi = new Intent(getContext(), LayananDesanActivity.class);
                        startActivity(prestasi);
                    } else if (finalI == 3) {
                        Intent layanan = new Intent(getContext(), UMKMWargaActivity.class);
                        startActivity(layanan);
                    } else if (finalI == 4) {
                        Intent potensi = new Intent(getContext(), PotensiDesaActivity.class);
                        startActivity(potensi);
                    } else if (finalI == 5) {
                        Intent lapor = new Intent(getContext(), LaporActivity.class);
                        startActivity(lapor);
                    } else if (finalI == 6) {
                        Intent kom = new Intent(getContext(), MitraDesaActivity.class);
                        startActivity(kom);
                    } else if (finalI == 7) {
                        Intent regisalat = new Intent(getContext(), RegisAlatActivity.class);
                        startActivity(regisalat);
                    } else if (finalI == 8) {
                        sessionManager.logout();
                    }

                }
            });
        }
    }

}
