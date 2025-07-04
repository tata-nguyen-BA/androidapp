package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
        
        // Initialize with sample data to demonstrate the app functionality
        List<Contact> sampleContacts = Arrays.asList(
                new Contact("Nguyễn Văn Hùng", "0901234567"),
                new Contact("Trần Thị Lan", "0912345678"),
                new Contact("Lê Minh Tuấn", "0923456789")
        );
        
        memberAdapter = new MemberAdapter(sampleContacts);
        rvMembers.setAdapter(memberAdapter);
        
        // Show sample group info
        tvGroupName.setText(getString(R.string.group_prefix) + getString(R.string.sample_group_name));
        tvGroupName.setVisibility(TextView.VISIBLE);
        
        // Show welcome message
        Toast.makeText(this, getString(R.string.welcome_message), Toast.LENGTH_LONG).show();
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
                tvGroupName.setText(getString(R.string.group_prefix) + group.getGroupName());
                tvGroupName.setVisibility(TextView.VISIBLE);
                memberAdapter.setMembers(group.getMembers());
                Toast.makeText(this, getString(R.string.group_created_success), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
