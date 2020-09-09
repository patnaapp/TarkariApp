package bih.in.tarkariapp.activity.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.PreLoginActivity;
import bih.in.tarkariapp.activity.thelawala.HomeActivity;

public class DeliveryBoy_Home_Activity extends AppCompatActivity {

    String username,phone,reg_no;
    TextView tv_username,tv_regno,tv_phone;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy__home_);
        initialisation();

        try {
            if (ActivityCompat.checkSelfPermission(DeliveryBoy_Home_Activity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(DeliveryBoy_Home_Activity.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        reg_no= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("reg_id", "");
        username= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uname", "");
        phone= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("mob", "");

        tv_username.setText(username);
        tv_regno.setText(reg_no);
        tv_phone.setText(phone);

    }

    public void initialisation()
    {
        tv_username=findViewById(R.id.tv_username);
        tv_regno=findViewById(R.id.tv_regno);
        tv_phone=findViewById(R.id.tv_phone);
    }

    public void OnClickLogout(View view)
    {
        new AlertDialog.Builder(DeliveryBoy_Home_Activity.this)
                .setTitle("लॉग आउट ??")
                .setIcon(R.drawable.logo)
                .setMessage("क्या आप लॉग आउट करना चाहते हैं ?")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void confirmLogout()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DeliveryBoy_Home_Activity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("username", false);
        editor.putString("userType", "");
        editor.commit();

        Intent intent = new Intent(this, PreLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onDeliverOrder(View view)
    {
        Intent i=new Intent(DeliveryBoy_Home_Activity.this,OrderListForDelivery_Activity.class);
        startActivity(i);
    }
}
