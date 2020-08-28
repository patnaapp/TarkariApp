package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bih.in.tarkariapp.R;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et_mobile_no,et_password,et_cnfrm_pass;
    String mobile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mobile=getIntent().getStringExtra("mob");

        et_mobile_no=findViewById(R.id.et_mobile_no);
        et_password=findViewById(R.id.et_password);
        et_cnfrm_pass=findViewById(R.id.et_cnfrm_pass);

        et_mobile_no.setText(mobile);
        et_mobile_no.setEnabled(false);
    }

    public void onChange_Pass(View view)
    {

    }
}
