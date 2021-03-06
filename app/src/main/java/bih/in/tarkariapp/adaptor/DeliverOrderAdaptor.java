package bih.in.tarkariapp.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.deliveryboy.OrderDetailsActivity;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.entity.GetDeliveryOrderEntity;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class DeliverOrderAdaptor extends RecyclerView.Adapter<DeliverOrderAdaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<GetDeliveryOrderEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;
    String date;

    public DeliverOrderAdaptor(Activity listViewshowedit, ArrayList<GetDeliveryOrderEntity> rlist,String orderdate)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;

        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.date=orderdate;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_deliver_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final GetDeliveryOrderEntity info = ThrList.get(position);

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        holder.tv_veg_count.setText(info.getVegCount());
        holder.tv_thela_address.setText(info.getAddress());
        holder.tv_mob_no.setText(info.getMobile());
        holder.tv_delivery_status.setText(info.getStatus());
        holder.tv_total_amt_cnf.setText(info.getAmount());

        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", info.getMobile(), null));
                activity.startActivity(intent);
            }
        });
        holder.tv_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity, OrderDetailsActivity.class);
                i.putExtra("orderid",info.getOrderId());
                i.putExtra("order_date",date);
                activity.startActivity(i);
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
        TextView tv_sl_no,tv_veg_count,tv_thela_address,tv_total_amt_cnf,tv_mob_no,tv_delivery_status,tv_view_detail;
        ImageView iv_call;
        LinearLayout ll_req_quantity;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_veg_count=itemView.findViewById(R.id.tv_veg_count);
            tv_thela_address=itemView.findViewById(R.id.tv_thela_address);
            tv_mob_no=itemView.findViewById(R.id.tv_mob_no);
            tv_delivery_status=itemView.findViewById(R.id.tv_delivery_status);
            tv_total_amt_cnf=itemView.findViewById(R.id.tv_total_amt_cnf);
            iv_call=itemView.findViewById(R.id.iv_call);
            tv_view_detail=itemView.findViewById(R.id.tv_view_detail);

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
