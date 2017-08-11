package com.himanshu.smartcanister;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himanshu.smartcanister.models.Canister;
import com.himanshu.smartcanister.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ritwick on 11/8/17.
 */

public class CanisterAdapter extends RecyclerView.Adapter<CanisterAdapter.MyViewHolder>
{
    private ArrayList<Canister> canisterList;

    public CanisterAdapter(ArrayList<Canister> canisterList)
    {
        this.canisterList=canisterList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contentNameText, percentageLeftText;
        public CircleImageView canisterImage;

        public MyViewHolder(View view) {
            super(view);
            contentNameText = (TextView) view.findViewById(R.id.contentNameText);
            percentageLeftText = (TextView) view.findViewById(R.id.percentageLeftText);
            canisterImage = (CircleImageView) view.findViewById(R.id.canisterImage);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View canisterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_canister, parent, false);
        return new MyViewHolder(canisterView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Canister canister = canisterList.get(position);
        holder.contentNameText.setText(canister.getContentName());
        holder.percentageLeftText.setText(canister.getPercentageLeft());
        holder.canisterImage.setImageURI(Uri.parse(canister.getImageurl()));
    }

    @Override
    public int getItemCount()
    {
        return canisterList.size();
    }

    public void notifyDatasetChanged()
    {
        System.out.println("asdasdasdasdasd");

        if(getItemCount()>0)
            EventBus.getDefault().post(new MessageEvent("visibilitylogic",View.INVISIBLE));
        else
            EventBus.getDefault().post(new MessageEvent("visibilitylogic",View.VISIBLE));

        notifyDataSetChanged();
    }
}
