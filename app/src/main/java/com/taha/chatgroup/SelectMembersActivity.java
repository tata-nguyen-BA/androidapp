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

        EditText etGroupName = findViewById(R.id.etGroupName);
        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);

        // Hard-coded contacts
        List<Contact> contacts = Arrays.asList(
                new Contact("Thảo", "0123456789"),
                new Contact("Nam",  "0987654321"),
                new Contact("Tín",  "0345678912"),
                new Contact("Lan",  "0912345678")
        );

        selectionAdapter = new SelectionAdapter(contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(selectionAdapter);

        btnCreateGroup.setOnClickListener(v -> {
            String groupName = etGroupName.getText().toString().trim();
            List<Contact> selected = selectionAdapter.getSelectedContacts();
            if (groupName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên nhóm", Toast.LENGTH_SHORT).show();
            } else if (selected.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một thành viên", Toast.LENGTH_SHORT).show();
            } else {
                Intent result = new Intent();
                result.putExtra("group", new Group(groupName, selected));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
