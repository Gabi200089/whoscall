package com.example.whoscall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_delC extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //firestore
    FirebaseFirestore objectFirebsaeFirestore;
    EditText numberET, whoET, classET;
    TextView valuesTV2;
    DocumentReference objectDocumentReference;
    ImageView resultIMG,bcg;
    Button btn,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_c);

        try {
            objectFirebsaeFirestore = FirebaseFirestore.getInstance();
            numberET = findViewById(R.id.numberET4);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        resultIMG= findViewById(R.id.result_img);
        btn= findViewById(R.id.deleteFieldFBtn);
        btn2= findViewById(R.id.deleteFieldFBtn2);
        btn3= findViewById(R.id.deleteFieldFBtn3);
//        btn3.setOnClickListener(btn3Listener);
        bcg= findViewById(R.id.delete_bcg);


        valuesTV2 = findViewById(R.id.valuesTV2);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_deleteC);//app一開始home就被選著

        GlobalVariable gv = (GlobalVariable)getApplicationContext();

        String tc=gv.getTcolor();
        Drawable hb=gv.getHbg();
        toolbar.setBackgroundColor(Color.parseColor(tc));
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            header.setBackground(hb); }

    }
    //取得firestore資料
    public void getValueFromFirebase(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()){
                objectDocumentReference=objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                objectDocumentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String number=documentSnapshot.getId();//用id(號碼)才能查詢
                                String who=documentSnapshot.getString("who");
                                String Class=documentSnapshot.getString("class");
                                valuesTV2.setText(
                                        "名稱:"+who+"\n"+"類別:"+Class+"\n"+"號碼:"+number
                                );
                                resultIMG.setVisibility(View.VISIBLE);
                                btn.setVisibility(View.VISIBLE);
                                btn2.setVisibility(View.VISIBLE);
                                btn3.setVisibility(View.VISIBLE);
                                bcg.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_delC.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //刪除collection(整筆號碼資料)
    public  void delCollection(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()) {
                objectDocumentReference = objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                objectDocumentReference.delete()
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_delC.this,"資訊已刪除",Toast.LENGTH_SHORT).show();
                                        numberET.setText("");
                                        valuesTV2.setText("");
                                        resultIMG.setVisibility(View.INVISIBLE);
                                        btn.setVisibility(View.INVISIBLE);
                                        btn2.setVisibility(View.INVISIBLE);
                                        btn3.setVisibility(View.INVISIBLE);
                                        bcg.setVisibility(View.VISIBLE);
                                    }
                                }
                        )
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_delC.this,"資訊刪除失敗",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "請提供電話號碼以刪除整筆資料", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //刪除document(一欄資料who)
    public void delNValue(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()) {
                objectDocumentReference = objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("who", FieldValue.delete());//將who資料刪除

                objectDocumentReference.update(objectMap)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_delC.this,"資訊已刪除",Toast.LENGTH_SHORT).show();
                                        objectDocumentReference.get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String number=documentSnapshot.getId();//用id(號碼)才能查詢
                                                        String who=documentSnapshot.getString("who");
                                                        String Class=documentSnapshot.getString("class");
                                                        valuesTV2.setText(
                                                                "名稱:"+who+"\n"+"類別:"+Class+"\n"+"號碼:"+number
                                                        );
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Activity_delC.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                        )
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_delC.this,"資訊刪除失敗",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    //刪除document(一欄資料class)
    public void delCValue(View view)
    {
        try {
            if(!numberET.getText().toString().isEmpty()) {
                objectDocumentReference = objectFirebsaeFirestore.collection("WhosCall").document(
                        numberET.getText().toString()
                );
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("class", FieldValue.delete());//將who資料刪除

                objectDocumentReference.update(objectMap)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_delC.this,"資訊已刪除",Toast.LENGTH_SHORT).show();
                                        objectDocumentReference.get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String number=documentSnapshot.getId();//用id(號碼)才能查詢
                                                        String who=documentSnapshot.getString("who");
                                                        String Class=documentSnapshot.getString("class");
                                                        valuesTV2.setText(
                                                                "名稱:"+who+"\n"+"類別:"+Class+"\n"+"號碼:"+number
                                                        );
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Activity_delC.this,"fails to get data back",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                        )
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_delC.this,"資訊刪除失敗",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
            case R.id.nav_deleteC:
                break;//在home時按home無效
            case R.id.nav_new:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent = new Intent(Activity_delC.this,Activity_new.class);
                startActivity(intent);//使用intent啟動另一個activity
                break;
            case R.id.nav_security:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent2 = new Intent(Activity_delC.this, Activity_security.class);
                startActivity(intent2);//使用intent啟動另一個activity
                break;
            case R.id.nav_home:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent3 = new Intent(Activity_delC.this,Activity_homepage.class);
                startActivity(intent3);//使用intent啟動另一個activity
                break;
            case R.id.nav_search:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent4 = new Intent(Activity_delC.this,Activity_search.class);
                startActivity(intent4);//使用intent啟動另一個activity
                break;
            case R.id.nav_update:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent5 = new Intent(Activity_delC.this,Activity_update.class);
                startActivity(intent5);//使用intent啟動另一個activity
                break;

            case R.id.nav_logout:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent7 = new Intent(Activity_delC.this,Activity_register.class);
                startActivity(intent7);//使用intent啟動另一個activity
                break;
            case R.id.nav_profile:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent8 = new Intent(Activity_delC.this,Activity_profile.class);
                startActivity(intent8);//使用intent啟動另一個activity
                break;
            case R.id.nav_phonebook:
                //intent是在相互獨立的組件(EX.兩個Activity)間提供運行時綁定功能的對象
                Intent intent9 = new Intent(Activity_delC.this,Activity_phonebook.class);
                startActivity(intent9);//使用intent啟動另一個activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}