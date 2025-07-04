package com.taha.chatgroup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.adapters.SelectionAdapter;
import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectMembersActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    
    private SelectionAdapter selectionAdapter;
    private RecyclerView rvContacts;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_members);

        EditText etGroupName = findViewById(R.id.etGroupName);
        rvContacts = findViewById(R.id.rvContacts);
        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);
        progressBar = findViewById(R.id.progressBar); // We'll assume this exists in the layout

        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        // Check permission and load contacts
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            loadContacts();
        }

        btnCreateGroup.setOnClickListener(v -> {
            String groupName = etGroupName.getText().toString().trim();
            List<Contact> selected = selectionAdapter != null ? selectionAdapter.getSelectedContacts() : null;
            if (groupName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên nhóm", Toast.LENGTH_SHORT).show();
            } else if (selected == null || selected.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một thành viên", Toast.LENGTH_SHORT).show();
            } else {
                Intent result = new Intent();
                result.putExtra("group", new Group(groupName, selected));
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            } else {
                // Permission denied, use fallback contacts
                loadFallbackContacts();
                Toast.makeText(this, "Quyền truy cập danh bạ bị từ chối. Sử dụng danh bạ mẫu.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadContacts() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        rvContacts.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<Contact> contacts = ContactProvider.getDeviceContacts(SelectMembersActivity.this);
            
            mainHandler.post(() -> {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                rvContacts.setVisibility(View.VISIBLE);

                if (contacts.isEmpty()) {
                    loadFallbackContacts();
                    Toast.makeText(SelectMembersActivity.this, "Không tìm thấy danh bạ. Sử dụng danh bạ mẫu.", Toast.LENGTH_SHORT).show();
                } else {
                    selectionAdapter = new SelectionAdapter(contacts);
                    rvContacts.setAdapter(selectionAdapter);
                }
            });
        });
    }

    private void loadFallbackContacts() {
        // Fallback to hardcoded contacts if permission denied or no contacts found
        List<Contact> contacts = Arrays.asList(
                new Contact("Thảo", "0123456789"),
                new Contact("Nam",  "0987654321"),
                new Contact("Tín",  "0345678912"),
                new Contact("Lan",  "0912345678")
        );
        
        selectionAdapter = new SelectionAdapter(contacts);
        rvContacts.setAdapter(selectionAdapter);
    }
}
