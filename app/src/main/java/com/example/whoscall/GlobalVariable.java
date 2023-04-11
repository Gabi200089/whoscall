package com.example.whoscall;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class GlobalVariable extends Application {
    private String Tcolor="#FFECBD47";//預設黃色
    Drawable Hbg;//預設黃色背景
    Drawable homebg;//預設黃色背景
    //修改 變數値
    String Mail;

    public void setMail(String mail){
        this.Mail = mail;
    }
    public void setTcolor(String tcolor){
        this.Tcolor = tcolor;
    }
    public void setHbg(String hbg){
        Drawable drawable = null;
        Resources res = this.getResources();
        if(hbg=="mango")
            drawable = res.getDrawable(R.drawable.mango);
        if(hbg=="marshmello")
            drawable = res.getDrawable(R.drawable.marshmello);
        if(hbg=="mix")
            drawable = res.getDrawable(R.drawable.mix);
        if(hbg=="mint")
            drawable = res.getDrawable(R.drawable.mint);
        if(hbg=="blueberry")
            drawable = res.getDrawable(R.drawable.blueberry);
        if(hbg=="strawberry")
            drawable = res.getDrawable(R.drawable.strawberry);
        this.Hbg = drawable;
    }
    public void setHomebg(String homebg){
        Drawable drawable = null;
        Resources res = this.getResources();
        if(homebg=="mango")
            drawable = res.getDrawable(R.drawable.hmango);
        if(homebg=="marshmello")
            drawable = res.getDrawable(R.drawable.hmarshmello);
        if(homebg=="mix")
            drawable = res.getDrawable(R.drawable.hmix);
        if(homebg=="mint")
            drawable = res.getDrawable(R.drawable.hmint);
        if(homebg=="blueberry")
            drawable = res.getDrawable(R.drawable.hblueberry);
        if(homebg=="strawberry")
            drawable = res.getDrawable(R.drawable.hstrawberry);
        this.homebg = drawable;
    }
    //取得 變數值
    public String getMail(){
        return Mail ;
    }
    public String getTcolor() {
        return Tcolor;
    };
    public Drawable getHbg() {
        if(Hbg==null) {
            Resources res = this.getResources();
            Hbg = res.getDrawable(R.drawable.mango);
        }
        return Hbg;
    };
    public Drawable getHomebg() {
        if(homebg==null) {
            Resources res = this.getResources();
            homebg = res.getDrawable(R.drawable.hmango);
        }
        return homebg;
    };


}