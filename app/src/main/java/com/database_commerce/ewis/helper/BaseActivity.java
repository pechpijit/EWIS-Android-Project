package com.database_commerce.ewis.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    public static String BASE_URL = "http://check2rod.com/api/";
    public static String BASE_URL_PICTURE = "http://check2rod.com";

//    public static String BASE_URL = "http://192.168.1.38:8989/api/";
//    public static String BASE_URL_PICTURE = "http://192.168.1.38:8989";

    public static String AUTH = "กำลังเข้าสู่ระบบ...";
    public static String REGIS = "กำลังสมัครสมาชิก...";
    public static String LOAD = "กำลังโหลดข้อมูล...";
    public static String VERIFY = "กำลังตรวจสอบ...";


    @VisibleForTesting
    private SweetAlertDialog pDialog;

    public void showProgressDialog(String message) {

        if (pDialog == null) {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#03a9f4"));
            pDialog.setContentText("");
            pDialog.setTitleText(message);
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }

        pDialog.show();
    }

    public void hideProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public String getAppversion(Activity context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public void dialogResultErrorInternet() {
        dialogResultError("ไม่สามารถเชื่อมอินเทร์เน็ตได้\nกรุณาตรวจสอบอินเทอร์เน็ต\nและลองใหม่อีกครั้ง");
    }

    public void dialogResultError(String string) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ขออภัย")
                .setContentText(string+" กรุณาลองใหม่อีกครั้ง")
                .setConfirmText("ตกลง")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void dialogResultSuccess() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Premium Version!")
                .setContentText("ขอบคุณที่สนับสนุน กรุณารีสตาร์ทแอปพลิเคชั่น เพื่อตั้งค่าระบบใหม่")
                .setConfirmText("รีสตาร์ท")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
