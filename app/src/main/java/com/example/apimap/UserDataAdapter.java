package com.example.apimap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by D on 10/18/2018.
 */

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {
    private ArrayList<UserData> list;
    private Context ctx;
    private OnItemClickListener listener;

    public UserDataAdapter(Context context, ArrayList<UserData> lists) {
        ctx = context;
        list = lists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create ViewHolder when not enough
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Define the data shows in the card View
        UserData currentItem = list.get(position);


        double latitude = currentItem.getLatitude();
        double longitude = currentItem.getLongitude();

        String picture =  currentItem.getPicture();
        String _id =  currentItem.get_id();
        String name =  currentItem.getName();
        String email =  currentItem.getEmail();


       Glide.with(ctx).load(picture).into(holder.iv_user_image);

        holder.tv_user_name.setText(name);

        holder.tv_user_email.setText(email);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //ViewHolder set up(mandatory for RecyclerView)
        public ImageView iv_user_image;
        public TextView tv_user_name;
        public TextView tv_user_email;



        public ViewHolder(View itemView) {
            super(itemView);
            iv_user_image = itemView.findViewById(R.id.iv_user_image);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_user_email = itemView.findViewById(R.id.tv_user_email);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    //OnClick UI

    public void setOnItemClickListener(OnItemClickListener listeners) {
        listener = listeners;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }




}
