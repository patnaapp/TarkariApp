package bih.in.tarkariapp.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.activity.thelawala.RecieveOrder_QR_Activity;
import bih.in.tarkariapp.entity.GetOrderPlacedEntity;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OrderPlacedByThelaAdaptor extends RecyclerView.Adapter<OrderPlacedByThelaAdaptor.ViewHolder>
{

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<GetOrderPlacedEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;

    public OrderPlacedByThelaAdaptor(Activity listViewshowedit, ArrayList<GetOrderPlacedEntity> rlist)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_placed_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final GetOrderPlacedEntity info = ThrList.get(position);

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        holder.tv_veg_name.setText(info.getVegname());
        holder.tv_veg_qty.setText(info.getOrderquantity());
        holder.tv_delvry_date.setText(info.getOrderdate());
        holder.tv_order_date.setText(info.getEntrydate());
        //  holder.tv_veg_price.setText(info.getActualrate());

        if(info.getIsdelivery().equals("N"))
        {
            holder.tv_status.setText("डिलीवरी पेंडिंग है");
        }
        else {
            holder.tv_status.setText("डिलीवरी हो चूका है");
        }

        holder.sblist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do whatever
                if(info.getIsdelivery().equals("N"))
                {
                    Intent intent = new Intent(activity, RecieveOrder_QR_Activity.class);
                    intent.putExtra("orderId",String.valueOf(info.getId()));
                    activity.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_sl_no,tv_veg_name,tv_veg_qty,tv_delvry_date,tv_order_date,tv_status;

        LinearLayout ll_req_quantity,sblist;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_veg_name=itemView.findViewById(R.id.tv_veg_name);
            tv_delvry_date=itemView.findViewById(R.id.tv_delvry_date);
            tv_order_date=itemView.findViewById(R.id.tv_order_date);
            tv_veg_qty=itemView.findViewById(R.id.tv_veg_qty);
            sblist=itemView.findViewById(R.id.sblist);
            tv_status=itemView.findViewById(R.id.tv_status);

        }

        @Override
        public void onClick(View v)
        {

        }

    }

    public String getGenderHindi(String gender)
    {
        return gender;
    }

}
