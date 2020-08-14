package bih.in.tarkariapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.thelawala.HomeActivity;
import bih.in.tarkariapp.utility.AppConstant;
import bih.in.tarkariapp.utility.Utiilties;

public class LoginActivity extends Activity {

    EditText et_username,et_password;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText(AppConstant.APP_VERSION+ Utiilties.getAppVersion(this));

        role = getIntent().getStringExtra(AppConstant.ROLE);
    }

    public void onLoginClick(View view) {
        if(isValidInput()){
            if(Utiilties.isOnline(this)){
                proceedLogin();
            }else{
                Utiilties.showAlet(this);
            }
        }
    }

    public void proceedLogin(){
        //TODO: Write login api
        Intent intnet = new Intent(this, HomeActivity.class);
        startActivity(intnet);
        finish();
    }

    Boolean isValidInput(){
        View focusView = null;
        boolean validate = true;

        if (et_username.getText().toString().equals("")) {
            et_username.setError("कृपया सही मोबाइल नंबर दर्ज करें");
            focusView = et_username;
            validate = false;
        }else if (et_username.getText().toString().length() < 10) {
            et_username.setError("कृपया सही मोबाइल नंबर दर्ज करें");
            focusView = et_username;
            validate = false;
        }

        if (et_password.getText().toString().equals("")) {
            et_password.setError("कृपया सही पासवर्ड दर्ज करें");
            focusView = et_password;
            validate = false;
        }

        if (!validate) focusView.requestFocus();
        return validate;
    }
}
