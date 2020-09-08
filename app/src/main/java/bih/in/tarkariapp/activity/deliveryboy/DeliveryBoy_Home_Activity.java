package bih.in.tarkariapp.activity.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import bih.in.tarkariapp.R;

public class DeliveryBoy_Home_Activity extends AppCompatActivity {

    String username,phone,reg_no;
    TextView tv_username,tv_regno,tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy__home_);
        initialisation();

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
}
