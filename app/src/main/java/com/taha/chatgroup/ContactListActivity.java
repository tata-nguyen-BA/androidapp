package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.adapters.MemberAdapter;
import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_MEMBERS = 1;

    private TextView tvGroupName;
    private RecyclerView rvMembers;
    private MemberAdapter memberAdapter;
    private List<Group> hardCodedGroups;
    private int currentGroupIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        tvGroupName = findViewById(R.id.tvGroupName);
        rvMembers   = findViewById(R.id.rvMembers);
        rvMembers.setLayoutManager(new LinearLayoutManager(this));
        memberAdapter = new MemberAdapter(new ArrayList<>());
        rvMembers.setAdapter(memberAdapter);
        
        // Initialize hard-coded groups
        initializeHardCodedGroups();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_group:
                showCreateGroupDialog();
                return true;
            case R.id.menu_edit_group:
                editCurrentGroup();
                return true;
            case R.id.menu_delete_group:
                deleteCurrentGroup();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_MEMBERS && resultCode == RESULT_OK && data != null) {
            Group group = (Group) data.getSerializableExtra("group");
            if (group != null) {
                // Add new group to hard-coded list
                hardCodedGroups.add(group);
                currentGroupIndex = hardCodedGroups.size() - 1; // Set to the newly created group
                displayGroup(group);
                Toast.makeText(this, "Đã tạo nhóm mới: " + group.getGroupName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeHardCodedGroups() {
        hardCodedGroups = new ArrayList<>();
        
        List<Contact> group1Members = Arrays.asList(
            new Contact("Thảo", "0123456789"),
            new Contact("Nam", "0987654321")
        );
        Group group1 = new Group("Nhóm 1", group1Members);
        
        List<Contact> group2Members = Arrays.asList(
            new Contact("Tín", "0345678912"),
            new Contact("Lan", "0912345678")
        );
        Group group2 = new Group("Nhóm 2", group2Members);
        
        hardCodedGroups.add(group1);
        hardCodedGroups.add(group2);
        
        // Display first group by default
        if (!hardCodedGroups.isEmpty()) {
            displayGroup(hardCodedGroups.get(0));
        }
    }

    private void displayGroup(Group group) {
        tvGroupName.setText("Nhóm: " + group.getGroupName());
        tvGroupName.setVisibility(TextView.VISIBLE);
        memberAdapter.setMembers(group.getMembers());
    }

    private void showCreateGroupDialog() {
        startActivityForResult(
                new Intent(this, SelectMembersActivity.class),
                REQUEST_SELECT_MEMBERS
        );
    }

    private void editCurrentGroup() {
        if (hardCodedGroups.isEmpty()) {
            Toast.makeText(this, "Không có nhóm nào để sửa", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Group currentGroup = hardCodedGroups.get(currentGroupIndex);
        Toast.makeText(this, "Đang sửa nhóm: " + currentGroup.getGroupName(), Toast.LENGTH_SHORT).show();
        
        // For simplicity, just cycle through existing groups
        currentGroupIndex = (currentGroupIndex + 1) % hardCodedGroups.size();
        displayGroup(hardCodedGroups.get(currentGroupIndex));
    }

    private void deleteCurrentGroup() {
        if (hardCodedGroups.isEmpty()) {
            Toast.makeText(this, "Không có nhóm nào để xóa", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Group currentGroup = hardCodedGroups.get(currentGroupIndex);
        
        new AlertDialog.Builder(this)
            .setTitle("Xóa nhóm")
            .setMessage("Bạn có chắc chắn muốn xóa nhóm '" + currentGroup.getGroupName() + "'?")
            .setPositiveButton("Xóa", (dialog, which) -> {
                hardCodedGroups.remove(currentGroupIndex);
                
                if (hardCodedGroups.isEmpty()) {
                    tvGroupName.setVisibility(TextView.GONE);
                    memberAdapter.setMembers(new ArrayList<>());
                    currentGroupIndex = 0;
                } else {
                    if (currentGroupIndex >= hardCodedGroups.size()) {
                        currentGroupIndex = 0;
                    }
                    displayGroup(hardCodedGroups.get(currentGroupIndex));
                }
                
                Toast.makeText(this, "Đã xóa nhóm", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Hủy", null)
            .show();
    }
}
