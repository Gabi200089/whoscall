package com.example.whoscall;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneCallListener extends PhoneStateListener {

    private static final String TAG = "MyPhoneCallListener";
    protected PhoneListener listener;

    /**
     * 返回電話狀態
     * <p>
     * CALL_STATE_IDLE 未通話
     * CALL_STATE_OFFHOOK 通話中
     * CALL_STATE_RINGING 響鈴中
     */
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        //state 当前状态 incomingNumber
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            // 挂断
            case TelephonyManager.CALL_STATE_IDLE:
                Log.e(TAG, "現在狀態:未通話");
                com.example.whoscall.WindowsUtils.hidePopupWindow();
                break;
            // 通话中
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.e(TAG, "現在狀態:通話中");
                com.example.whoscall.WindowsUtils.hidePopupWindow();
                break;
            // 电话响铃
            case TelephonyManager.CALL_STATE_RINGING:
                Log.e(TAG, "現在狀態:響鈴中");
                listener.onCallStateRinging(incomingNumber);
                break;
            default:
                break;
        }
    }

    public void setPhoneListener(PhoneListener phoneListener) {
        this.listener = phoneListener;
    }

    public interface PhoneListener {
        void onCallStateRinging(String number);
    }
}
