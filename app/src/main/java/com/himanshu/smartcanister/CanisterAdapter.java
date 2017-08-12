package com.himanshu.smartcanister;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.himanshu.smartcanister.models.Canister;
import com.himanshu.smartcanister.models.MessageEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ritwick on 11/8/17.
 */

public class CanisterAdapter extends RecyclerView.Adapter<CanisterAdapter.MyViewHolder>
{
    private ArrayList<Canister> canisterList;
    Context context;

    public CanisterAdapter(Context context,ArrayList<Canister> canisterList)
    {
        this.context=context;
        this.canisterList=canisterList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contentNameText, percentageLeftText;
        public CircleImageView canisterImage;
        public ImageButton deleteButton;

        public MyViewHolder(View view) {
            super(view);
            contentNameText = (TextView) view.findViewById(R.id.contentNameText);
            percentageLeftText = (TextView) view.findViewById(R.id.percentageLeftText);
            canisterImage = (CircleImageView) view.findViewById(R.id.canisterImage);
            deleteButton=(ImageButton) view.findViewById(R.id.deleteButton);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View canisterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_canister, parent, false);
        canisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,DetailedCanisterActivity.class));
            }
        });
        return new MyViewHolder(canisterView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        Canister canister = canisterList.get(position);
        holder.contentNameText.setText(canister.getContentName());
        holder.percentageLeftText.setText(canister.getPercentageLeft());
        Picasso.with(context).load(Uri.parse(canister.getImageurl())).into(holder.canisterImage);


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canisterList.remove(position);
                notifyDatasetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return canisterList.size();
    }

    public void notifyDatasetChanged()
    {
        if(getItemCount()>0)
            EventBus.getDefault().post(new MessageEvent("visibilitylogic",View.INVISIBLE));
        else
            EventBus.getDefault().post(new MessageEvent("visibilitylogic",View.VISIBLE));

        notifyDataSetChanged();
    }
}
