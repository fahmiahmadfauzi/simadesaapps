package com.fahmi.simadesav1.penduduk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fahmi.simadesav1.R;

import java.util.ArrayList;
import java.util.List;

public class UMKMWargaActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umkmwarga);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }

    public void tambahusul(MenuItem mi) {
        //startActivity(new Intent(UMKMWargaActivity.this, UMKMWargaTambahActivity.class));
        Intent umkm = new Intent(UMKMWargaActivity.this, UMKMWargaTambahActivity.class);
        startActivity(umkm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutambahusulan, menu);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        UMKMWargaActivity.ViewPagerAdapter adapter = new UMKMWargaActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new UMKMWargaFragment(), "UMKM Warga");
        adapter.addFrag(new UMKMAndaFragment(), "UMKM Anda");

        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("UMKM Warga");
        // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_home, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("UMKM Anda");

        //   tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_home, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
