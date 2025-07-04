package com.taha.chatgroup.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taha.chatgroup.database.AppDatabase;
import com.taha.chatgroup.database.dao.GroupDao;
import com.taha.chatgroup.database.entity.ContactEntity;
import com.taha.chatgroup.database.entity.GroupEntity;
import com.taha.chatgroup.database.entity.GroupContactJoin;
import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupRepository {
    
    private GroupDao groupDao;
    private LiveData<List<GroupEntity>> allGroups;
    private ExecutorService databaseWriteExecutor;

    public GroupRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        groupDao = db.groupDao();
        allGroups = groupDao.getAllGroups();
        databaseWriteExecutor = Executors.newFixedThreadPool(4);
    }

    public LiveData<List<GroupEntity>> getAllGroups() {
        return allGroups;
    }

    public LiveData<List<ContactEntity>> getContactsForGroup(int groupId) {
        return groupDao.getContactsForGroup(groupId);
    }

    public void insertGroupWithContacts(Group group, List<ContactEntity> contacts) {
        databaseWriteExecutor.execute(() -> {
            // Insert group and get its ID
            GroupEntity groupEntity = new GroupEntity(group.getGroupName());
            long groupId = groupDao.insertGroup(groupEntity);
            
            // Insert group-contact relationships
            for (ContactEntity contact : contacts) {
                GroupContactJoin join = new GroupContactJoin((int) groupId, contact.getId());
                groupDao.insertGroupContactJoin(join);
            }
        });
    }

    public void updateGroup(GroupEntity group) {
        databaseWriteExecutor.execute(() -> {
            groupDao.updateGroup(group);
        });
    }

    public void deleteGroup(GroupEntity group) {
        databaseWriteExecutor.execute(() -> {
            groupDao.deleteGroup(group);
        });
    }

    public void updateGroupContacts(int groupId, List<ContactEntity> contacts) {
        databaseWriteExecutor.execute(() -> {
            // Remove existing relationships
            groupDao.deleteGroupContacts(groupId);
            
            // Add new relationships
            for (ContactEntity contact : contacts) {
                GroupContactJoin join = new GroupContactJoin(groupId, contact.getId());
                groupDao.insertGroupContactJoin(join);
            }
        });
    }

    // Helper method to convert GroupEntity to Group with contacts
    public Group entityToModelWithContacts(GroupEntity groupEntity, List<ContactEntity> contactEntities) {
        List<Contact> contacts = ContactRepository.entitiesToModels(contactEntities);
        return new Group(groupEntity.getGroupName(), contacts);
    }
}