package bih.in.tarkariapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.utility.AppConstant;
import bih.in.tarkariapp.utility.Utiilties;

public class LoginActivity extends Activity {

    EditText et_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView et_username = findViewById(R.id.et_username);
        TextView et_password = findViewById(R.id.et_password);

        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText(AppConstant.APP_VERSION+ Utiilties.getAppVersion(this));
    }

    public void onLoginClick(View view) {

    }
}
