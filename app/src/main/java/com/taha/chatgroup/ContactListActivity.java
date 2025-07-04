package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taha.chatgroup.adapters.GroupAdapter;
import com.taha.chatgroup.database.entity.GroupEntity;
import com.taha.chatgroup.models.Group;
import com.taha.chatgroup.repository.GroupRepository;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_MEMBERS = 1;

    private TextView tvGroupName;
    private RecyclerView rvGroups;
    private GroupAdapter groupAdapter;
    private GroupRepository groupRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        // Initialize repository
        groupRepository = new GroupRepository(getApplication());

        tvGroupName = findViewById(R.id.tvGroupName);
        rvGroups = findViewById(R.id.rvMembers); // Reusing the same RecyclerView
        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new GroupAdapter(new ArrayList<>(), groupRepository);
        rvGroups.setAdapter(groupAdapter);

        // Update UI to show groups
        tvGroupName.setText("Danh sách nhóm");
        tvGroupName.setVisibility(TextView.VISIBLE);

        // Observe groups from database
        groupRepository.getAllGroups().observe(this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(List<GroupEntity> groupEntities) {
                groupAdapter.updateGroups(groupEntities);
            }
        });
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
                // Groups are automatically updated via LiveData observer
                // No need to manually update the UI here
            }
        }
    }
}
