package com.example.whoscall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Activity_profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //選單
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_profile);//app一開始home就被選著
        //cardview(顏色框框)
        CardView cv1=findViewById(R.id.cv1);
        CardView cv2=findViewById(R.id.cv2);
        CardView cv3=findViewById(R.id.cv3);
        CardView cv4=findViewById(R.id.cv4);
        CardView cv5=findViewById(R.id.cv5);
        CardView cv6=findViewById(R.id.cv6);
        cv1.setOnClickListener(cv1Listener);
        cv2.setOnClickListener(cv2Listener);
        cv3.setOnClickListener(cv3Listener);
        cv4.setOnClickListener(cv4Listener);
        cv5.setOnClickListener(cv5Listener);
        cv6.setOnClickListener(cv6Listener);

        GlobalVariable gv = (GlobalVariable)getApplicationContext();
        String tc=gv.getTcolor();
        Drawable hb=gv.getHbg();
        toolbar.setBackgroundColor(Color.parseColor(tc));
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            header.setBackground(hb); }
    }
    //toolbar的來回
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }
    private CardView.OnClickListener cv1Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.setTcolor("#FFECBD47");
            gv.setHbg("mango");
            gv.setHomebg("mango");
            String tc=gv.getTcolor();
            Drawable hb=gv.getHbg();
            toolbar.setBackgroundColor(Color.parseColor(tc));
            if(navigationView.getHeaderCount() > 0) {
                View header = navigationView.getHeaderView(0);
                header.setBackground(hb); }
        }
    };
    private CardView.OnClickListener cv2Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {

            GlobalVariable gv = (GlobalVariable)getApplicationContext();

            gv.setTcolor("#FFA0ECFA");
            gv.setHbg("marshmello");
            gv.setHomebg("marshmello");
            String tc=gv.getTcolor();
            Drawable hb=gv.getHbg();
            toolbar.setBackgroundColor(Color.parseColor(tc));
            if(navigationView.getHeaderCount() > 0) {
                View header = navigationView.getHeaderView(0);
                header.setBackground(hb); }
        }
    };
    private CardView.OnClickListener cv3Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();

            gv.setTcolor("#FFFDC3C1");
            gv.setHbg("strawberry");
            gv.setHomebg("strawberry");
            String tc=gv.getTcolor();
            Drawable hb=gv.getHbg();
            toolbar.setBackgroundColor(Color.parseColor(tc));
            if(navigationView.getHeaderCount() > 0) {
                View header = navigationView.getHeaderView(0);
                header.setBackground(hb); }
        }
    };
    private CardView.OnClickListener cv4Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();

            gv.setTcolor("#FFFBBA94");
            gv.setHbg("mix");
            gv.setHomebg("mix");
            String tc=gv.getTcolor();
            Drawable hb=gv.getHbg();
            toolbar.setBackgroundColor(Color.parseColor(tc));
            if(navigationView.getHeaderCount() > 0) {
                View header = navigationView.getHeaderView(0);
                header.setBackground(hb); }
        }
    };
    private CardView.OnClickListener cv5Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();

            gv.setTcolor("#FFA2D7CB");
            gv.setHbg("mint");
            gv.setHomebg("mint");
            String tc=gv.getTcolor();
            Drawable hb=gv.getHbg();
            toolbar.setBackgroundColor(Color.parseColor(tc));
            if(navigationView.getHeaderCount() > 0) {
                View header = navigationView.getHeaderView(0);
                header.setBackground(hb); }
        }
    };
    private CardView.OnClickListener cv6Listener = new CardView.OnClickListener() {
        @Override
        public void onClick(View v) {
            GlobalVariable gv = (GlobalVariable)getApplicationContext();

            gv.setTcolor("#FFC8C4E7");
            gv.setHbg("blueberry");
            gv.setHomebg("blueberry");
            String tc=gv.getTcolor();
            Drawable hb=gv.getHbg();
            toolbar.setBackgroundColor(Color.parseColor(tc));
            if(navigationView.getHeaderCount() > 0) {
                View header = navigationView.getHeaderView(0);
                header.setBackground(hb); }
        }
    };
    @Override
    //toolbar選項選擇之頁面跳轉
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_profile:
                break;//在home時按home無效
            case R.id.nav_logout:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent = new Intent(Activity_profile.this,Activity_register.class);
                startActivity(intent);//使用intent啟動另一個activity
                break;
            case R.id.nav_security:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent2 = new Intent(Activity_profile.this, Activity_security.class);
                startActivity(intent2);//使用intent啟動另一個activity
                break;
            case R.id.nav_home:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent3 = new Intent(Activity_profile.this,Activity_homepage.class);
                startActivity(intent3);//使用intent啟動另一個activity
                break;
            case R.id.nav_search:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent4 = new Intent(Activity_profile.this,Activity_search.class);
                startActivity(intent4);//使用intent啟動另一個activity
                break;
            case R.id.nav_update:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent5 = new Intent(Activity_profile.this,Activity_update.class);
                startActivity(intent5);//使用intent啟動另一個activity
                break;
            case R.id.nav_deleteC:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent6 = new Intent(Activity_profile.this,Activity_delC.class);
                startActivity(intent6);//使用intent啟動另一個activity
                break;
            case R.id.nav_new:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent8 = new Intent(Activity_profile.this,Activity_new.class);
                startActivity(intent8);//使用intent啟動另一個activity
                break;
            case R.id.nav_phonebook:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent9 = new Intent(Activity_profile.this,Activity_phonebook.class);
                startActivity(intent9);//使用intent啟動另一個activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}