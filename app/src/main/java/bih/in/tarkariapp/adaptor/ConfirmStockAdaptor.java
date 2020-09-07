package bih.in.tarkariapp.adaptor;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegStockEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class ConfirmStockAdaptor extends RecyclerView.Adapter<ConfirmStockAdaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<GetVegStockEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;
    String logintype="";

    public ConfirmStockAdaptor(Activity listViewshowedit, ArrayList<GetVegStockEntity> rlist)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_confirm_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final GetVegStockEntity info = ThrList.get(position);
        logintype= PreferenceManager.getDefaultSharedPreferences(activity).getString("logintype", "");


        if(logintype.equals("thela"))
        {
            holder.ll_total_amnt.setVisibility(View.VISIBLE);
        }
        else if(logintype.equals("farmer"))
        {
            holder.ll_total_amnt.setVisibility(View.GONE);
        }


        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        holder.tv_veg_name.setText(info.getVegnamehn());
        holder.tv_veg_qty.setText(info.getVegQty());
        holder.tv_delvry_date.setText(info.getVegstockdate());
       // holder.tv_order_date.setText(info.getExpecteddel_date());
        //  holder.tv_veg_price.setText(info.getActualrate());

    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_sl_no,tv_veg_name,tv_veg_qty,tv_delvry_date,tv_order_date;

        LinearLayout ll_req_quantity,ll_total_amnt;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_veg_name=itemView.findViewById(R.id.tv_veg_name);
            tv_delvry_date=itemView.findViewById(R.id.tv_delvry_date);
            tv_order_date=itemView.findViewById(R.id.tv_order_date);
            tv_veg_qty=itemView.findViewById(R.id.tv_veg_qty);
            ll_total_amnt=itemView.findViewById(R.id.ll_total_amnt);

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
