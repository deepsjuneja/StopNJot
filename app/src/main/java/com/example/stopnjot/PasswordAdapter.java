package com.example.stopnjot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder2> {
    List<Password> mPasswordList = new ArrayList<>();
    Context mContext;

    public PasswordAdapter(List<Password> passList, Context context) {
        this.mPasswordList = passList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_layout, parent, false);

        ViewHolder2 viewHolder = new ViewHolder2(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        holder.account.setText(mPasswordList.get(position).getAccount());
    }

    @Override
    public int getItemCount() {
        return mPasswordList.size();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView account;
        RelativeLayout passwordRelLayout;

        public ViewHolder2(View itemView) {
            super(itemView);
            this.account = itemView.findViewById(R.id.passwordTag);
            this.passwordRelLayout = itemView.findViewById(R.id.passwordView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Password passData = mPasswordList.get(getAdapterPosition());

                    Intent genPassIntent = new Intent(mContext, ViewOrDeletePassword.class);

                    genPassIntent.putExtra("pId", passData.getPId());
                    genPassIntent.putExtra("account", passData.getAccount());
                    genPassIntent.putExtra("password", passData.getPass());

                    mContext.startActivity(genPassIntent);
                }
            });
        }
    }
}