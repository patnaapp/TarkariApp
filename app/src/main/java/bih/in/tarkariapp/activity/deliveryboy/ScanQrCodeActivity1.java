package bih.in.tarkariapp.activity.deliveryboy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonObject;
import com.google.zxing.Result;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.LoginActivity;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.entity.OrderDeliveredResponse;
import bih.in.tarkariapp.utility.DataBaseHelper;

import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQrCodeActivity1 extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    String BLOCKCODE="";
    DataBaseHelper dataBaseHelper;
    String orderid="";

    String Intentdata="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code1);
        dataBaseHelper=new DataBaseHelper(getApplicationContext());

        orderid=getIntent().getStringExtra("order_code");
        ActivityCompat.requestPermissions(ScanQrCodeActivity1.this,
                new String[]{Manifest.permission.CAMERA},
                1);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(ScanQrCodeActivity1.this, "Permission denied to camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }



    @Override
    public void onResume()
    {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //  Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        Intentdata=result.getText();
        //     BLOCKCODE= CommonPref.getUserDetails(getApplicationContext()).get_BlockCode();
        if (Intentdata.equals(orderid))
        {
            DeliverVegetable();
        }
        else {
            AlertDialog.Builder ab = new AlertDialog.Builder(ScanQrCodeActivity1.this);
            ab.setTitle(R.string.app_name);
            ab.setIcon(R.drawable.logo);
            ab.setMessage("आर्डर आईडी मैच नहीं हुआ ");
            ab.setPositiveButton("ओके", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int whichButton)
                {

                    dialog.dismiss();
                    mScannerView.setResultHandler(ScanQrCodeActivity1.this);
                    mScannerView.startCamera();
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            ab.show();
        }


    }



    private void DeliverVegetable()
    {
        if(Utiilties.isOnline(ScanQrCodeActivity1.this))
        {
            JsonObject param = new JsonObject();
            param.addProperty("orderid", orderid);
            param.addProperty("Status", "Y");

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(ScanQrCodeActivity1.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Delivering...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<OrderDeliveredResponse> call = null;

            call = request.DeliverVeg(param);



            call.enqueue(new Callback<OrderDeliveredResponse>()
            {
                @Override
                public void onResponse(Call<OrderDeliveredResponse> call, Response<OrderDeliveredResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    OrderDeliveredResponse userDetail = response.body();

                    if(userDetail != null)
                    {
                        // if(userDetail.getStatus() && (userDetail.getData().getRole().equals("MEMBER")||userDetail.getData().getRole().equals("THELA"))) {
                        if(userDetail.getStatus() )
                        {
                            AlertDialog.Builder ab = new AlertDialog.Builder(ScanQrCodeActivity1.this);
                            ab.setTitle(R.string.app_name);
                            ab.setIcon(R.drawable.logo);
                            ab.setMessage("आर्डर डिलीवरी सफल हुआ ");
                            ab.setPositiveButton("ओके", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    Intent i=new Intent(ScanQrCodeActivity1.this,DeliveryBoy_Home_Activity.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                            });

                            ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                            ab.show();
                        }
                        else
                        {
                            Toast.makeText(ScanQrCodeActivity1.this, userDetail.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(ScanQrCodeActivity1.this);
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
                public void onFailure(Call<OrderDeliveredResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(ScanQrCodeActivity1.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(ScanQrCodeActivity1.this)
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
