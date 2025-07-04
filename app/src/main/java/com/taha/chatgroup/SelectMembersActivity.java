package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.adapters.SelectionAdapter;
import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;

import java.util.Arrays;
import java.util.List;

public class SelectMembersActivity extends AppCompatActivity {
    private SelectionAdapter selectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_members);
        
        // Set title for the activity
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.select_members_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        EditText etGroupName = findViewById(R.id.etGroupName);
        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);

        // Hard-coded contacts - Extended Vietnamese contact list
        List<Contact> contacts = Arrays.asList(
                new Contact("Nguyễn Văn Hùng", "0901234567"),
                new Contact("Trần Thị Lan", "0912345678"),
                new Contact("Lê Minh Tuấn", "0923456789"),
                new Contact("Phạm Thị Hoa", "0934567890"),
                new Contact("Hoàng Văn Long", "0945678901"),
                new Contact("Vũ Thị Mai", "0956789012"),
                new Contact("Đặng Minh Đức", "0967890123"),
                new Contact("Bùi Thị Trang", "0978901234"),
                new Contact("Phan Văn Sơn", "0989012345"),
                new Contact("Ngô Thị Linh", "0990123456"),
                new Contact("Đinh Văn Quang", "0901123456"),
                new Contact("Lý Thị Thu", "0912123456"),
                new Contact("Tôn Văn Khải", "0923123456"),
                new Contact("Cao Thị Yến", "0934123456"),
                new Contact("Dương Văn Thành", "0945123456")
        );

        selectionAdapter = new SelectionAdapter(contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(selectionAdapter);

        btnCreateGroup.setOnClickListener(v -> {
            String groupName = etGroupName.getText().toString().trim();
            List<Contact> selected = selectionAdapter.getSelectedContacts();
            if (groupName.isEmpty()) {
                Toast.makeText(this, R.string.please_enter_group_name, Toast.LENGTH_SHORT).show();
            } else if (selected.isEmpty()) {
                Toast.makeText(this, R.string.please_select_members, Toast.LENGTH_SHORT).show();
            } else {
                Intent result = new Intent();
                result.putExtra("group", new Group(groupName, selected));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
