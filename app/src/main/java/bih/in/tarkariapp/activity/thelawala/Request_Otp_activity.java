package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.LoginActivity;
import bih.in.tarkariapp.entity.GetOTPEntity;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.entity.UserDetail;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request_Otp_activity extends AppCompatActivity
{
    EditText et_mobile_no,et_OTP;
    Button btn_get_otp;
    String mobile_no="";
   TextInputLayout Material_top;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__otp_activity);

        initialisation();
    }

    public void initialisation()
    {
        et_mobile_no=findViewById(R.id.et_mobile_no);
        et_mobile_no.addTextChangedListener(inputTextWatcher1);
        btn_get_otp=findViewById(R.id.btn_get_otp);
        Material_top=findViewById(R.id.Material_top);
        et_OTP=findViewById(R.id.et_OTP);
    }

    public void onGET_OTP(View view)
    {

    }

    private TextWatcher inputTextWatcher1 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_mobile_no.getText().length() == 10)
            {
                try {
                    if(isValidInput())
                    {
                        mobile_no = et_mobile_no.getText().toString().trim();

                        RequestOTP();
                    }
                    else
                    {
                        Toast.makeText(Request_Otp_activity.this, "Enter Mobile No!", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {
//            if (!validAadhaar && et_father_aadhar_number_new.getText().toString().length() == 12) {
////                CommonMethods.showErrorDialog(getResources().getString(R.string.invalid_value),
////                        getResources().getString(R.string.check_aadhaar_no));
//            }
        }
    };

    Boolean isValidInput()
    {
        View focusView = null;
        boolean validate = true;

        if (et_mobile_no.getText().toString().equals(""))
        {
            et_mobile_no.setError("Please enter mobileno");
            focusView = et_mobile_no;
            validate = false;
        }

        if (!validate) focusView.requestFocus();
        return validate;
    }


    private void RequestOTP()
    {
        if(Utiilties.isOnline(Request_Otp_activity.this))
        {
            JsonObject param = new JsonObject();
            param.addProperty("Mobile", mobile_no);
            //  param.addProperty("UserID", mobile_no);

            Log.e("Mobile", param.toString());

            final ProgressDialog dialog = new ProgressDialog(Request_Otp_activity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Authenticating...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);
            Call<GetOTPEntity> call = request.GetOtp(param);

            call.enqueue(new Callback<GetOTPEntity>()
            {
                @Override
                public void onResponse(Call<GetOTPEntity> call, Response<GetOTPEntity> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    final GetOTPEntity userDetail = response.body();

                    if(userDetail != null && userDetail.getStatus())
                    {

                        btn_get_otp.setVisibility(View.VISIBLE);
                        Material_top.setVisibility(View.VISIBLE);

                        btn_get_otp.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (et_OTP.getText().toString().length()<0)
                                {
                                    Toast.makeText(Request_Otp_activity.this, "कृपया  otp डाले", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if (et_OTP.getText().toString().equals(userDetail.getGenerateOPTNO()))
                                    {
                                        Intent i=new Intent(Request_Otp_activity.this,ChangePasswordActivity.class);
                                        i.putExtra("mob",mobile_no);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                            }
                        });

                        //  onGotUserDetail(userDetail);
                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else if(userDetail != null && userDetail.getStatus()==false)
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(Request_Otp_activity.this);
                        ab.setTitle(userDetail.getMsg());
                        ab.setMessage("मोबाइल नंबर सही नहीं है ");
                        ab.setPositiveButton("ओके", new DialogInterface.OnClickListener()
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
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(Request_Otp_activity.this);
                        ab.setTitle("सर्वर डाउन है");
                        ab.setMessage("सर्वर डाउन है कृपया बाद में प्रयास करें |");
                        ab.setPositiveButton("ठीक है", new DialogInterface.OnClickListener()
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
                public void onFailure(Call<GetOTPEntity> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(Request_Otp_activity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(Request_Otp_activity.this)
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
