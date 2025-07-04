package com.taha.chatgroup.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.R;
import com.taha.chatgroup.models.Contact;

import java.util.List;

public class MemberAdapter
        extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<Contact> members;

    public MemberAdapter(List<Contact> members) {
        this.members = members;
    }

    public void setMembers(List<Contact> m) {
        this.members = m;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup p, int viewType) {
        View v = LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_member, p, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        Contact c = members.get(pos);
        h.tvName.setText(c.getName());
        h.tvPhone.setText(c.getPhone());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        ViewHolder(View itemView) {
            super(itemView);
            tvName  = itemView.findViewById(R.id.tvMemberName);
            tvPhone = itemView.findViewById(R.id.tvMemberPhone);
        }
    }
}
