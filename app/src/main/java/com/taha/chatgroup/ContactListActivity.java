package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.adapters.ContactAdapter;
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
    private ContactAdapter contactAdapter;

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
        
        // Initialize adapters
        memberAdapter = new MemberAdapter(new ArrayList<>());
        
        // Hardcoded contact list for initial display
        List<Contact> contacts = Arrays.asList(
                new Contact("Thảo", "0123456789"),
                new Contact("Nam",  "0987654321"),
                new Contact("Tín",  "0345678912"),
                new Contact("Lan",  "0912345678")
        );
        contactAdapter = new ContactAdapter(contacts);
        
        // Show contact list initially
        rvMembers.setAdapter(contactAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_group) {
            startActivityForResult(
                    new Intent(this, SelectMembersActivity.class),
                    REQUEST_SELECT_MEMBERS
            );
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
                tvGroupName.setText("Nhóm: " + group.getGroupName());
                tvGroupName.setVisibility(TextView.VISIBLE);
                // Switch to member adapter to show group members
                rvMembers.setAdapter(memberAdapter);
                memberAdapter.setMembers(group.getMembers());
            }
        }
    }
}
