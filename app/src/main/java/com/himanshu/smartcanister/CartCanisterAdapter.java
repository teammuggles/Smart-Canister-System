package com.himanshu.smartcanister;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.himanshu.smartcanister.models.CanisterCart;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Himanshu Gupta on 8/12/2017.
 */

public class CartCanisterAdapter extends RecyclerView.Adapter<CartCanisterAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    List<CanisterCart> data= Collections.emptyList();
    Context context;
    public  CartCanisterAdapter(Context context, List<CanisterCart> data)
    {
        inflater=LayoutInflater.from(context);
        this.data=data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_canister,parent,false);  //here view will represent the root of the custom row that is Linear LAyout
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CanisterCart current=data.get(position);
        Picasso.with(context).load(Uri.parse(current.getImageurl())).into(holder.canisterImage);
        holder.nameText.setText(current.getContentName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView canisterImage;
        public TextView nameText;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameText= (TextView) itemView.findViewById(R.id.contentNameText);
            canisterImage = (CircleImageView) itemView.findViewById(R.id.canisterImage);
        }
    }
}
