package com.fahmi.simadesav1.penduduk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.fahmi.simadesav1.R;
import com.fahmi.simadesav1.session.SessionManager;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    Toolbar ttoolbar;
    GridLayout gridLayout;
    SessionManager sessionManager;
    EditText idak, nik;
    private BottomNavigationView bottomNavigation;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNavigation.inflateMenu(R.menu.menu_bottom_nav_view);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        ttoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ttoolbar);

        //     idak=(EditText)findViewById(R.id.idakun);
        //     nik=(EditText)findViewById(R.id.nik);
        HashMap<String, String> userr = sessionManager.getUserDetail();
        String mNik = userr.get(SessionManager.NIK);
        String mEmail = userr.get(SessionManager.ID_AKUN);


//        idak.setText(mNama);
//        nik.setText(mEmail);


        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //      gridLayout=(GridLayout)findViewById(R.id.mainGrid);
//        setSingleEvent(gridLayout);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        //sendTokenToServer();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_home:
                        fragment = new HomeFragment();


                        break;
                    case R.id.action_map:
                        fragment = new MapFragment();
                        break;
                    case R.id.action_kontak:
                        fragment = new KontakFragment();
                        break;
                    case R.id.action_panic:
                        fragment = new PanicFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        Intent infodesa = new Intent(HomeActivity.this, InfoDesaActivity.class);
                        startActivity(infodesa);
                    } else if (finalI == 1) {
                        Intent usulan = new Intent(HomeActivity.this, UsulanWargaActivity.class);
                        startActivity(usulan);
                    } else if (finalI == 2) {
                            /*Intent prestasi=new Intent(MainActivity.this, PrestasiActivity.class);
                            startActivity(prestasi);*/
                    } else if (finalI == 3) {
                        Intent layanan = new Intent(HomeActivity.this, UMKMWargaActivity.class);
                        startActivity(layanan);
                    } else if (finalI == 4) {
                        Intent potensi = new Intent(HomeActivity.this, PotensiDesaActivity.class);
                        startActivity(potensi);
                    } else if (finalI == 5) {
                            /*Intent mitra=new Intent(MainActivity.this, MitraDesaActivity.class);
                            startActivity(mitra);*/
                    } else if (finalI == 6) {
                        Intent kom = new Intent(HomeActivity.this, MitraDesaActivity.class);
                        startActivity(kom);
                    } else if (finalI == 7) {
                            /*Intent agenda=new Intent(MainActivity.this, AgendaActivity.class);
                            startActivity(agenda);*/
                    } else if (finalI == 8) {
                        sessionManager.logout();
                    }

                }
            });
        }
    }


}
