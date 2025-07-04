package com.taha.chatgroup.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.R;
import com.taha.chatgroup.database.entity.ContactEntity;
import com.taha.chatgroup.database.entity.GroupEntity;
import com.taha.chatgroup.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<GroupEntity> groups;
    private GroupRepository groupRepository;

    public GroupAdapter(List<GroupEntity> groups, GroupRepository groupRepository) {
        this.groups = groups;
        this.groupRepository = groupRepository;
    }

    public void updateGroups(List<GroupEntity> newGroups) {
        this.groups = newGroups;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroupEntity group = groups.get(position);
        holder.tvName.setText(group.getGroupName());
        
        // Get contacts for this group and display member info
        try {
            if (holder.itemView.getContext() instanceof LifecycleOwner) {
                groupRepository.getContactsForGroup(group.getId()).observe(
                    (LifecycleOwner) holder.itemView.getContext(), 
                    new Observer<List<ContactEntity>>() {
                        @Override
                        public void onChanged(List<ContactEntity> contactEntities) {
                            if (contactEntities != null && !contactEntities.isEmpty()) {
                                int memberCount = contactEntities.size();
                                StringBuilder memberInfo = new StringBuilder();
                                memberInfo.append(memberCount).append(" thành viên: ");
                                
                                for (int i = 0; i < Math.min(contactEntities.size(), 2); i++) {
                                    if (i > 0) memberInfo.append(", ");
                                    memberInfo.append(contactEntities.get(i).getName());
                                }
                                if (contactEntities.size() > 2) {
                                    memberInfo.append("...");
                                }
                                holder.tvPhone.setText(memberInfo.toString());
                            } else {
                                holder.tvPhone.setText("Không có thành viên");
                            }
                        }
                    });
            } else {
                holder.tvPhone.setText("Đang tải...");
            }
        } catch (Exception e) {
            holder.tvPhone.setText("Lỗi tải dữ liệu");
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        
        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMemberName);
            tvPhone = itemView.findViewById(R.id.tvMemberPhone);
        }
    }
}