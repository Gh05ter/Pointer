package com.example.mrsj.zoombug.Seebug;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrsj.zoombug.R;

import java.util.ArrayList;

/**
 * Created by MR.SJ on 2016/4/25.
 */
public class SgRecycleAdapter extends RecyclerView.Adapter<SgViewHolder> implements View.OnClickListener{

    private Activity activity;
    private ArrayList<RecycleItem> list;
    public SgRecycleAdapter(Activity activity, ArrayList<RecycleItem> list) {
       this.activity=activity;
        this.list=list;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    @Override
    public void onBindViewHolder(SgViewHolder holder, int position) {
        RecycleItem recycleItem=list.get(position);
        TextView bug_key=(TextView) holder.itemView.findViewById(R.id.bug_key_tv);
        bug_key.setText(recycleItem.key);
        TextView bug_info=(TextView)holder.itemView.findViewById(R.id.bug_info_tv);
        bug_info.setText(recycleItem.info);
        holder.itemView.setTag(recycleItem.key);
    }

    @Override
    public SgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.bug_listitem,null);
        SgViewHolder sgViewHolder=new SgViewHolder(v);
        v.setOnClickListener(this);
        return sgViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }
}
