package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import bih.in.tarkariapp.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class RecieveOrder_QR_Activity extends AppCompatActivity
{

    String orderid="";
    ImageView imgview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_order__q_r_);

        orderid=getIntent().getStringExtra("orderId");
        imgview=findViewById(R.id.imgview);


        try {
            Bitmap bitmap = encodeAsBitmap(orderid);
            imgview.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException
    {
        BitMatrix result;
        try
        {
            result = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, 500, 500, null);
        }
        catch (IllegalArgumentException iae)
        {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++)
        {
            int offset = y * w;
            for (int x = 0; x < w; x++)
            {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }
}
