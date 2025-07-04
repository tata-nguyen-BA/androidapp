package com.taha.chatgroup.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.taha.chatgroup.database.entity.GroupEntity;
import com.taha.chatgroup.database.entity.GroupContactJoin;
import com.taha.chatgroup.database.entity.ContactEntity;

import java.util.List;

@Dao
public interface GroupDao {
    
    @Query("SELECT * FROM groups ORDER BY group_name ASC")
    LiveData<List<GroupEntity>> getAllGroups();

    @Query("SELECT * FROM groups WHERE id = :groupId")
    GroupEntity getGroupById(int groupId);

    @Insert
    long insertGroup(GroupEntity group);

    @Update
    void updateGroup(GroupEntity group);

    @Delete
    void deleteGroup(GroupEntity group);

    @Insert
    void insertGroupContactJoin(GroupContactJoin join);

    @Insert
    void insertGroupContactJoins(List<GroupContactJoin> joins);

    @Query("DELETE FROM group_contact_join WHERE group_id = :groupId")
    void deleteGroupContacts(int groupId);

    @Transaction
    @Query("SELECT c.* FROM contacts c " +
           "INNER JOIN group_contact_join gcj ON c.id = gcj.contact_id " +
           "WHERE gcj.group_id = :groupId " +
           "ORDER BY c.name ASC")
    LiveData<List<ContactEntity>> getContactsForGroup(int groupId);

    @Transaction
    @Query("SELECT c.* FROM contacts c " +
           "INNER JOIN group_contact_join gcj ON c.id = gcj.contact_id " +
           "WHERE gcj.group_id = :groupId " +
           "ORDER BY c.name ASC")
    List<ContactEntity> getContactsForGroupSync(int groupId);
}