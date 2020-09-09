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

import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.utility.DataBaseHelper;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

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

        }


    }



}
