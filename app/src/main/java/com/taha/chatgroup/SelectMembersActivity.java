package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.adapters.SelectionAdapter;
import com.taha.chatgroup.database.entity.ContactEntity;
import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;
import com.taha.chatgroup.repository.ContactRepository;
import com.taha.chatgroup.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

public class SelectMembersActivity extends AppCompatActivity {
    private SelectionAdapter selectionAdapter;
    private ContactRepository contactRepository;
    private GroupRepository groupRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_members);

        EditText etGroupName = findViewById(R.id.etGroupName);
        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);

        // Initialize repositories
        contactRepository = new ContactRepository(getApplication());
        groupRepository = new GroupRepository(getApplication());

        // Initialize default contacts if database is empty
        contactRepository.initializeDefaultContacts();

        // Set up RecyclerView
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        selectionAdapter = new SelectionAdapter(new ArrayList<>());
        rvContacts.setAdapter(selectionAdapter);

        // Observe contacts from database
        contactRepository.getAllContacts().observe(this, new Observer<List<ContactEntity>>() {
            @Override
            public void onChanged(List<ContactEntity> contactEntities) {
                List<Contact> contacts = ContactRepository.entitiesToModels(contactEntities);
                selectionAdapter.updateContacts(contacts, contactEntities);
            }
        });

        btnCreateGroup.setOnClickListener(v -> {
            String groupName = etGroupName.getText().toString().trim();
            List<Contact> selected = selectionAdapter.getSelectedContacts();
            if (groupName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên nhóm", Toast.LENGTH_SHORT).show();
            } else if (selected.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một thành viên", Toast.LENGTH_SHORT).show();
            } else {
                // Save group to database
                List<ContactEntity> selectedEntities = selectionAdapter.getSelectedContactEntities();
                Group group = new Group(groupName, selected);
                groupRepository.insertGroupWithContacts(group, selectedEntities);
                
                Intent result = new Intent();
                result.putExtra("group", group);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
