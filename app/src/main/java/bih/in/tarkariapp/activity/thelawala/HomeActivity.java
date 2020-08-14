package bih.in.tarkariapp.activity.thelawala;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.PreLoginActivity;
import bih.in.tarkariapp.utility.AppConstant;
import bih.in.tarkariapp.utility.Utiilties;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText(AppConstant.APP_VERSION+ Utiilties.getAppVersion(this));
    }

    public void onGenerateOrder(View view) {
    }

    public void onViewOrder(View view) {
    }

    public void OnClickLogout(View view) {
        new AlertDialog.Builder(this)
                .setTitle("लॉग आउट ??")
                .setIcon(R.drawable.logo)
                .setMessage("क्या आप लॉग आउट करना चाहते हैं  \n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void confirmLogout(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("password", false);
        editor.commit();

        Intent intent = new Intent(this, PreLoginActivity   .class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
