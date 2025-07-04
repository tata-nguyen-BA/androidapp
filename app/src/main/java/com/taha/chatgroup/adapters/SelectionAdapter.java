package com.taha.chatgroup.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.R;
import com.taha.chatgroup.database.entity.ContactEntity;
import com.taha.chatgroup.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class SelectionAdapter
        extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {

    private List<Contact> contacts;
    private List<ContactEntity> contactEntities;
    private final List<Contact> selectedContacts = new ArrayList<>();
    private final List<ContactEntity> selectedContactEntities = new ArrayList<>();

    public SelectionAdapter(List<Contact> contacts) {
        this.contacts = contacts;
        this.contactEntities = new ArrayList<>();
    }

    public void updateContacts(List<Contact> contacts, List<ContactEntity> entities) {
        this.contacts = contacts;
        this.contactEntities = entities;
        selectedContacts.clear();
        selectedContactEntities.clear();
        notifyDataSetChanged();
    }

    public List<Contact> getSelectedContacts() {
        return selectedContacts;
    }

    public List<ContactEntity> getSelectedContactEntities() {
        return selectedContactEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_select, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Contact c = contacts.get(pos);
        ContactEntity entity = contactEntities.size() > pos ? contactEntities.get(pos) : null;
        
        holder.tvName.setText(c.getName());
        holder.tvPhone.setText(c.getPhone());

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedContacts.contains(c));
        holder.checkBox.setOnCheckedChangeListener((cb, checked) -> {
            if (checked) {
                selectedContacts.add(c);
                if (entity != null) {
                    selectedContactEntities.add(entity);
                }
            } else {
                selectedContacts.remove(c);
                if (entity != null) {
                    selectedContactEntities.remove(entity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        CheckBox checkBox;
        ViewHolder(View itemView) {
            super(itemView);
            tvName   = itemView.findViewById(R.id.tvContactName);
            tvPhone  = itemView.findViewById(R.id.tvContactPhone);
            checkBox = itemView.findViewById(R.id.cbSelect);
        }
    }
}
