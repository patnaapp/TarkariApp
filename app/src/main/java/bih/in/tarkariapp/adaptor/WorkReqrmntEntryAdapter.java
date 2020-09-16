package bih.in.tarkariapp.adaptor;

import android.app.Activity;

import android.preference.PreferenceManager;
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
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class WorkReqrmntEntryAdapter extends RecyclerView.Adapter<WorkReqrmntEntryAdapter.ViewHolder>
{

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<GetVegEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;
    Double amount=0.0;
    String logintype="";
    Integer count=0;


    public WorkReqrmntEntryAdapter(Activity listViewshowedit, ArrayList<GetVegEntity> rlist, GenerateOrderListener listner)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        this.listener = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_work_reqmntentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final GetVegEntity info = ThrList.get(position);
        logintype= PreferenceManager.getDefaultSharedPreferences(activity).getString("logintype", "");

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        holder.tv_veg_name.setText(info.getVegname());
        holder.tv_veg_price.setText(info.getActualrate());
        holder.iv_chk_veg.setOnCheckedChangeListener(null);

        holder.iv_chk_veg.setChecked(info.getChecked());
        amount = Double.parseDouble(info.getActualrate());

        //holder.ll_req_quantity.removeAllViews();

        count=info.getVegcount();

        if (info.getChecked())
        {
            holder.tv_veg_qty.setText(info.getVegcount().toString());
            holder.tv_total_amt.setText("Rs."+String.valueOf(amount*count));
            holder.ll_req_quantity.setVisibility(View.VISIBLE);
            holder.ll_total_amnt.setVisibility(View.VISIBLE);
        }
        else if (info.getChecked()==false)
        {
            holder.tv_veg_qty.setText(info.getVegcount().toString());
            holder.tv_total_amt.setText("Rs."+String.valueOf(amount*count));
            holder.ll_req_quantity.setVisibility(View.GONE);
            holder.ll_total_amnt.setVisibility(View.GONE);
        }
        holder.iv_chk_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                                     {
                                                         @Override
                                                         public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                                         {
                                                             //LandOwnersModel detail = new LandOwnersModel();

                                                             //info.setChecked(isChecked);

                                                             listener.onPlaceOrder(position, isChecked);

                                                             //notifyDataSetChanged();
                                                         }

                                                     }
        );

        holder.iv_remove_veg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(info.getVegcount()>0)
                {
                    listener.onChangeQty(position,false);
                }
            }
        });

        holder.iv_add_veg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                listener.onChangeQty(position,true);
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
        final TextView tv_sl_no,tv_veg_name,tv_veg_price,tv_veg_qty,tv_total_amt;
        final ImageView iv_remove_veg,iv_add_veg;
        final LinearLayout ll_req_quantity,ll_total_amnt;
        final CheckBox iv_chk_veg;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_veg_name=itemView.findViewById(R.id.tv_veg_name);
            tv_veg_price=itemView.findViewById(R.id.tv_veg_price);
            ll_req_quantity=itemView.findViewById(R.id.ll_req_quantity);
            ll_total_amnt=itemView.findViewById(R.id.ll_total_amnt);
            iv_chk_veg=itemView.findViewById(R.id.iv_chk_veg);
            tv_veg_qty=itemView.findViewById(R.id.tv_veg_qty);
            iv_remove_veg=itemView.findViewById(R.id.iv_remove_veg);
            iv_add_veg=itemView.findViewById(R.id.iv_add_veg);
            tv_total_amt=itemView.findViewById(R.id.tv_total_amt);

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
