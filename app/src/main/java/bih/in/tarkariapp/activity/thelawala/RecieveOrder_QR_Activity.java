package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import bih.in.tarkariapp.R;

public class RecieveOrder_QR_Activity extends AppCompatActivity {

    String orderid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_order__q_r_);

        orderid=getIntent().getStringExtra("orderId");

    }


}
