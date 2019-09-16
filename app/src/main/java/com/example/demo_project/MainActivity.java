package com.example.demo_project;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.view.PagerAdapter;

import com.example.demo_project.Fragments.AboutFragment;
import com.example.demo_project.Fragments.HomeFragment;
import com.example.demo_project.Fragments.InventoryFragment;
import com.example.demo_project.Fragments.SalesFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    TextView username;
    TabLayout tablayout;
    ViewPager viewPager;
    String Musername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.Mtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //Musername = getIntent().getStringExtra("username");

        /*Bundle bundle = new Bundle();
        bundle.putString("username", Musername);
        InventoryFragment fragment = new InventoryFragment();
        fragment.setArguments(bundle);

        Bundle bundle2 = new Bundle();
        bundle.putString("username", Musername);
        SalesFragment fragment2 = new SalesFragment();
        fragment2.setArguments(bundle2);*/


        Musername = LoginActivity.token;


        username = findViewById(R.id.Musername);
        username.setText(Musername);

        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new InventoryFragment(), "Inventory");
        viewPagerAdapter.addFragment(new SalesFragment(), "Sales");
        viewPagerAdapter.addFragment(new HomeFragment(), "Home");
        viewPagerAdapter.addFragment(new AboutFragment(), "About");

        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){

            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout) {

            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);

        }

        return super.onOptionsItemSelected(item);
    }
}
