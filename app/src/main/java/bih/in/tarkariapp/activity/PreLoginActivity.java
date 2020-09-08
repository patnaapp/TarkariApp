package bih.in.tarkariapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.deliveryboy.Delvry_LoginActivity;
import bih.in.tarkariapp.utility.AppConstant;
import bih.in.tarkariapp.utility.Utiilties;


public class PreLoginActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText(AppConstant.APP_VERSION+ Utiilties.getAppVersion(this));
    }

    public void onThelaLogin(View view)
    {
        Intent intent = new Intent(PreLoginActivity.this, LoginActivity.class);
        intent.putExtra(AppConstant.ROLE, AppConstant.THELA_LOGIN);
        startActivity(intent);
    }

    public void onFarmerLogin(View view)
    {
        Intent intent = new Intent(PreLoginActivity.this, LoginActivity.class);
        intent.putExtra(AppConstant.ROLE, AppConstant.FARMER_LOGIN);
        startActivity(intent);
    }


    public void onDeliveryVendorLogin(View view)
    {
        Intent intent = new Intent(PreLoginActivity.this, Delvry_LoginActivity.class);
        intent.putExtra(AppConstant.ROLE, AppConstant.DelvryVendor_LOGIN);
        startActivity(intent);
    }
}
