package bih.in.tarkariapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.thelawala.HomeActivity;
import bih.in.tarkariapp.activity.thelawala.Request_Otp_activity;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.entity.UserDetail;
import bih.in.tarkariapp.utility.AppConstant;
import bih.in.tarkariapp.utility.DataBaseHelper;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity
{

    EditText et_username,et_password;
    String username,password;
    DataBaseHelper localDBHelper;
    TextView tv_changePass;
    String logintype="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logintype=getIntent().getStringExtra(AppConstant.ROLE);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        tv_changePass = findViewById(R.id.tv_changePass);

        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText(AppConstant.APP_VERSION+ Utiilties.getAppVersion(this));

        tv_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this, Request_Otp_activity.class);
                startActivity(i);
            }
        });

        if (logintype.equals("thela")){
            tv_changePass.setVisibility(View.VISIBLE);
        }
        else if (logintype.equals("farmer")|| logintype.equals(("delivery"))){
            tv_changePass.setVisibility(View.GONE);

        }
    }

    public void onLoginClick(View view)
    {
        if(isValidInput())
        {
            username = et_username.getText().toString().trim();
            password = et_password.getText().toString().trim();
            proccedLogin();
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Enter Valid Id Password!", Toast.LENGTH_SHORT).show();
        }
    }

    Boolean isValidInput()
    {
        View focusView = null;
        boolean validate = true;

        if (et_username.getText().toString().equals(""))
        {
            et_username.setError("Please enter username");
            focusView = et_username;
            validate = false;
        }

        if (et_password.getText().toString().equals(""))
        {
            et_password.setError("Please enter password");
            focusView = et_password;
            validate = false;
        }

        if (!validate) focusView.requestFocus();
        return validate;
    }


    private void proccedLogin()
    {
        if(Utiilties.isOnline(LoginActivity.this))
        {
            JsonObject param = new JsonObject();
            param.addProperty("MobileNumber", username);
            param.addProperty("Password", password);

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Authenticating...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<LoginDetailsResponse> call = null;
            if (logintype.equals("farmer"))
            {
                call = request.AuthenticateFarmeLogin(param);
            }
            else if (logintype.equals("thela"))
            {
                call = request.AuthenticateThelaLogin(param);
            }


            call.enqueue(new Callback<LoginDetailsResponse>()
            {
                @Override
                public void onResponse(Call<LoginDetailsResponse> call, Response<LoginDetailsResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    LoginDetailsResponse userDetail = response.body();

                    if(userDetail != null)
                    {
                        // if(userDetail.getStatus() && (userDetail.getData().getRole().equals("MEMBER")||userDetail.getData().getRole().equals("THELA"))) {
                        if(userDetail.getStatus() )
                        {
                            if (logintype.equals("farmer"))
                            {
                                onGotUserDetail(userDetail.getData());
                            }
                            else if (logintype.equals("thela"))
                            {
                                onGotUserDetail(userDetail.getData1());
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, userDetail.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
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
                public void onFailure(Call<LoginDetailsResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(LoginActivity.this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Internet Connection is not avaliable..\nPlease Turn ON Network Connection")
                .setCancelable(false)
                .setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent I = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(I);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    private void onGotUserDetail(UserDetail user)
    {
        try
        {
            long c = setLoginStatus(user);

            if (c > 0)
            {
                start();
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Authentication Failed!",Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
        }

    }

    private long setLoginStatus(UserDetail details)
    {

        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("uid", username).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putBoolean("username", true).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putBoolean("password", true).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("pass", password).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("userType", logintype).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("userRole", details.getRole()).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("reg_id", details.getRegistrationNO()).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("mob", details.getApplicantMob()).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("distcode", details.getDistCode()).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("thelaid", String.valueOf(details.getTelaID())).commit();
        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("logintype", logintype).commit();
        localDBHelper = new DataBaseHelper(LoginActivity.this);
        long c = localDBHelper.insertUserDetails(details);
        return c;
    }

    public void start()
    {
        Intent iUserHome = new Intent(LoginActivity.this, HomeActivity.class);
        iUserHome.putExtra(AppConstant.ROLE, logintype);
        startActivity(iUserHome);
        finish();
    }

}
