package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import bih.in.tarkariapp.R;

public class ViewUnion_Pvcs_Order_activity extends AppCompatActivity {

    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_union__pvcs__order_activity);

        img_back=(ImageView) findViewById(R.id.img);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
