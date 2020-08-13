package bih.in.tarkariapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import bih.in.tarkariapp.R;


public class PreLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

    }

    public void onDeptartmentLoginClick(View v)
    {
//        Intent intent = new Intent(this, MultipleLoginActivity.class);
//        startActivity(intent);
//        finish();
    }

    public void onComplainSystemClick(View v)
    {
        Toast.makeText(this, "Developement Under Process!", Toast.LENGTH_SHORT).show();
    }
}
