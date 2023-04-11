package com.example.whoscall;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class Activity_security extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_DIALOG_PERMISSION =1001 ;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btnOpen;
    TextView tvDesc;

    ImageView imageView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_security);//app一開始home就被選著
        GlobalVariable gv = (GlobalVariable)getApplicationContext();

        String tc=gv.getTcolor();
        Drawable hb=gv.getHbg();
        toolbar.setBackgroundColor(Color.parseColor(tc));
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            header.setBackground(hb); }

        btnOpen = findViewById(R.id.btn_open);
        tvDesc = findViewById(R.id.tv_desc);
        imageView=findViewById(R.id.imageView);

        initPermission();

        getCanDrawOverlays();

        // 設定是否開啟應用上層顯示
        btnOpen.setOnClickListener(view -> setCanDrawOverlays());

        intent = new Intent(getApplicationContext(), PhoneListenService.class);
        startService(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    /**
     * 设置弹窗应用上层显示
     */
    private void setCanDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 由于8.0对系统弹唱权限的限制，需要用户进去设置中找到对应应用设置弹窗权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                //8.0
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, REQUEST_DIALOG_PERMISSION);
            } else {
                // 6.0、7.0、9.0
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_DIALOG_PERMISSION);
            }
        }
    }

    /**
     * 獲取設置中應用上層顯示
     */
    private void getCanDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                // 当前无权限，请授权
                tvDesc.setText("通話監測尚未開啟"+"\n"+"請前往設定");
                Resources res = this.getResources();
                Drawable drawable=res.getDrawable(R.drawable.unset);
                imageView.setImageDrawable(drawable);

            } else {
                Resources res = this.getResources();
                Drawable drawable=res.getDrawable(R.drawable.setok);
                imageView.setImageDrawable(drawable);
                tvDesc.setText("通話監測已開啟囉");
            }
        }
    }

    /**
     * 权限请求
     * ## 9.0手机状态读取权限说明
     * 要从手机状态中读取电话号码，请根据您的用例更新应用以请求必要的权限：
     * 要通过 PHONE_STATE Intent 操作读取电话号码，同时需要 READ_CALL_LOG 权限和 READ_PHONE_STATE 权限。
     * 要从 onCallStateChanged() 中读取电话号码，只需要 READ_CALL_LOG 权限。 不需要 READ_PHONE_STATE 权限。
     */
    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_CALL_LOG, Permission.READ_PHONE_STATE)
                .rationale((Context context, List<String> data, RequestExecutor executor) -> {
                    //拒绝一次后重试
                    executor.execute();
                })
                .onGranted(permission -> {
                    //权限调用成功后的回调

                })
                .onDenied(permissions -> {
                    //权限调用失败后的回调
                    finish();
                })
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DIALOG_PERMISSION) {
            getCanDrawOverlays();
        }
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

    @Override
    //toolbar選項選擇之頁面跳轉
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_security:
                break;//在home時按home無效
            case R.id.nav_new:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent = new Intent(Activity_security.this, Activity_new.class);
                startActivity(intent);//使用intent啟動另一個activity
                break;
            case R.id.nav_deleteC:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent2 = new Intent(Activity_security.this, Activity_delC.class);
                startActivity(intent2);//使用intent啟動另一個activity
                break;
            case R.id.nav_home:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent3 = new Intent(Activity_security.this,Activity_homepage.class);
                startActivity(intent3);//使用intent啟動另一個activity
                break;
            case R.id.nav_search:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent4 = new Intent(Activity_security.this, Activity_search.class);
                startActivity(intent4);//使用intent啟動另一個activity
                break;
            case R.id.nav_update:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent5 = new Intent(Activity_security.this, Activity_update.class);
                startActivity(intent5);//使用intent啟動另一個activity
                break;
            case R.id.nav_logout:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent7 = new Intent(Activity_security.this, Activity_register.class);
                startActivity(intent7);//使用intent啟動另一個activity
                break;
            case R.id.nav_profile:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent8 = new Intent(Activity_security.this, Activity_profile.class);
                startActivity(intent8);//使用intent啟動另一個activity
                break;
            case R.id.nav_phonebook:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent9 = new Intent(Activity_security.this, Activity_phonebook.class);
                startActivity(intent9);//使用intent啟動另一個activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}