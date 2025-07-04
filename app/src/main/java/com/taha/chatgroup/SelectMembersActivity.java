package com.taha.chatgroup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private EditText etGroupName;
    private List<Contact> allContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_members);

        etGroupName = findViewById(R.id.etGroupName);
        RecyclerView rvContacts = findViewById(R.id.rvContacts);
        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);

        // Hard-coded contacts
        allContacts = Arrays.asList(
                new Contact("Thảo", "0123456789"),
                new Contact("Nam",  "0987654321"),
                new Contact("Tín",  "0345678912"),
                new Contact("Lan",  "0912345678")
        );

        selectionAdapter = new SelectionAdapter(allContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(selectionAdapter);

        // Add hard-coded group templates
        addGroupTemplates();

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

    private void addGroupTemplates() {
        // Get the main layout to add template buttons
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.etGroupName).getParent();
        
        // Create template buttons
        Button btnTemplate1 = new Button(this);
        btnTemplate1.setText("Mẫu: Nhóm Công việc");
        btnTemplate1.setOnClickListener(v -> applyTemplate("Nhóm Công việc", Arrays.asList("Thảo", "Nam")));
        
        Button btnTemplate2 = new Button(this);
        btnTemplate2.setText("Mẫu: Nhóm Bạn bè");
        btnTemplate2.setOnClickListener(v -> applyTemplate("Nhóm Bạn bè", Arrays.asList("Tín", "Lan")));
        
        Button btnTemplate3 = new Button(this);
        btnTemplate3.setText("Mẫu: Nhóm Đầy đủ");
        btnTemplate3.setOnClickListener(v -> applyTemplate("Nhóm Đầy đủ", Arrays.asList("Thảo", "Nam", "Tín", "Lan")));
        
        // Insert template buttons after the EditText
        int index = mainLayout.indexOfChild(findViewById(R.id.etGroupName)) + 1;
        mainLayout.addView(btnTemplate1, index);
        mainLayout.addView(btnTemplate2, index + 1);
        mainLayout.addView(btnTemplate3, index + 2);
    }

    private void applyTemplate(String groupName, List<String> memberNames) {
        etGroupName.setText(groupName);
        
        // Clear current selections
        selectionAdapter.clearSelections();
        
        // Select members based on template
        for (String name : memberNames) {
            for (Contact contact : allContacts) {
                if (contact.getName().equals(name)) {
                    selectionAdapter.selectContact(contact);
                    break;
                }
            }
        }
        
        Toast.makeText(this, "Đã áp dụng mẫu: " + groupName, Toast.LENGTH_SHORT).show();
    }
}
