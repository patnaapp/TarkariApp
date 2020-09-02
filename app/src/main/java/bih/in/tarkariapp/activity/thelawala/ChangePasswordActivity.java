package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.LoginActivity;
import bih.in.tarkariapp.entity.ChangePassResponse;
import bih.in.tarkariapp.entity.GetOTPEntity;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et_mobile_no,et_password,et_cnfrm_pass;
    String mobile="",pass="",cnf_pass="";

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
        if(isValidInput())
        {
            pass = et_password.getText().toString().trim();
            cnf_pass = et_cnfrm_pass.getText().toString().trim();
            ChangePassword();
        }
        else
        {
            Toast.makeText(ChangePasswordActivity.this, "पासवर्ड और कन्फर्म पासवर्ड नहीं मैच हुआ", Toast.LENGTH_SHORT).show();
        }
    }

    Boolean isValidInput()
    {
        View focusView = null;
        boolean validate = true;

        if (et_mobile_no.getText().toString().length()<10)
        {
            et_mobile_no.setError("कृपया मान्य मोबाइल नंबर दर्ज करे");
            focusView = et_mobile_no;
            validate = false;
        }

        if (et_password.getText().toString().equals(""))
        {
            et_password.setError("कृपया पासवर्ड डाले");
            focusView = et_password;
            validate = false;
        }

        if (et_cnfrm_pass.getText().toString().equals(""))
        {
            et_cnfrm_pass.setError("कृपया कन्फर्म पासवर्ड डाले");
            focusView = et_cnfrm_pass;
            validate = false;
        }

       String pass=et_password.getText().toString();
       String cnfrm_pass=et_cnfrm_pass.getText().toString();
       if (!cnfrm_pass.equals(pass)){
           et_cnfrm_pass.setError("पासवर्ड मैच नहीं हुआ");
           focusView = et_cnfrm_pass;
           validate = false;
       }

        if (!validate) focusView.requestFocus();
        return validate;
    }


    private void ChangePassword()
    {
        if(Utiilties.isOnline(ChangePasswordActivity.this))
        {
            JsonObject param = new JsonObject();
            param.addProperty("MobileNumber", mobile);
            param.addProperty("Password", et_cnfrm_pass.getText().toString());
            //  param.addProperty("UserID", mobile_no);

            Log.e("Mobile", param.toString());

            final ProgressDialog dialog = new ProgressDialog(ChangePasswordActivity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Procesing...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);
            Call<ChangePassResponse> call = request.ChangePassword(param);

            call.enqueue(new Callback<ChangePassResponse>()
            {
                @Override
                public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    final ChangePassResponse userDetail = response.body();

                    if(userDetail != null && userDetail.getStatus())
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(ChangePasswordActivity.this);
                        ab.setTitle("सफल ");
                        ab.setMessage("पासवर्ड सफलतापूर्वक बदला गया");
                        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                                dialog.dismiss();
                                finish();
                            }
                        });

                        ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                        ab.show();
                    }
                    else
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(ChangePasswordActivity.this);
                        ab.setTitle("Server Down");
                        ab.setMessage("Server Down, Please try again later!");
                        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                dialog.dismiss();
                            }
                        });

                        ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                        ab.show();
                    }

                }

                @Override
                public void onFailure(Call<ChangePassResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            showAlertDialog();
        }
    }

    public void showAlertDialog()
    {
        new AlertDialog.Builder(ChangePasswordActivity.this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("इन्टरनेट कनेक्शन उपलब्ध नहीं है..\nकृपया नेटवर्क कनेक्शन चालू करे")
                .setCancelable(false)
                .setPositiveButton("नेटवर्क कनेक्शन चालू करे", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent I = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(I);
                    }
                })
                .setNegativeButton("कैंसिल", null)
                .show();
    }
}
