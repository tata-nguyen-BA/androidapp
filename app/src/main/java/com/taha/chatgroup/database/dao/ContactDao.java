package com.taha.chatgroup.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taha.chatgroup.database.entity.ContactEntity;

import java.util.List;

@Dao
public interface ContactDao {
    
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    LiveData<List<ContactEntity>> getAllContacts();

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    List<ContactEntity> getAllContactsSync();

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    ContactEntity getContactById(int contactId);

    @Insert
    long insertContact(ContactEntity contact);

    @Insert
    List<Long> insertContacts(List<ContactEntity> contacts);

    @Update
    void updateContact(ContactEntity contact);

    @Delete
    void deleteContact(ContactEntity contact);

    @Query("DELETE FROM contacts")
    void deleteAllContacts();
}