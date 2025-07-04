package com.taha.chatgroup.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taha.chatgroup.database.AppDatabase;
import com.taha.chatgroup.database.dao.ContactDao;
import com.taha.chatgroup.database.entity.ContactEntity;
import com.taha.chatgroup.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    
    private ContactDao contactDao;
    private LiveData<List<ContactEntity>> allContacts;
    private ExecutorService databaseWriteExecutor;

    public ContactRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
        databaseWriteExecutor = Executors.newFixedThreadPool(4);
    }

    public LiveData<List<ContactEntity>> getAllContacts() {
        return allContacts;
    }

    public void insert(ContactEntity contact) {
        databaseWriteExecutor.execute(() -> {
            contactDao.insertContact(contact);
        });
    }

    public void insert(List<ContactEntity> contacts) {
        databaseWriteExecutor.execute(() -> {
            contactDao.insertContacts(contacts);
        });
    }

    public void update(ContactEntity contact) {
        databaseWriteExecutor.execute(() -> {
            contactDao.updateContact(contact);
        });
    }

    public void delete(ContactEntity contact) {
        databaseWriteExecutor.execute(() -> {
            contactDao.deleteContact(contact);
        });
    }

    public void initializeDefaultContacts() {
        databaseWriteExecutor.execute(() -> {
            List<ContactEntity> existingContacts = contactDao.getAllContactsSync();
            if (existingContacts.isEmpty()) {
                List<ContactEntity> defaultContacts = new ArrayList<>();
                defaultContacts.add(new ContactEntity("Thảo", "0123456789"));
                defaultContacts.add(new ContactEntity("Nam", "0987654321"));
                defaultContacts.add(new ContactEntity("Tín", "0345678912"));
                defaultContacts.add(new ContactEntity("Lan", "0912345678"));
                contactDao.insertContacts(defaultContacts);
            }
        });
    }

    // Helper method to convert ContactEntity to Contact
    public static Contact entityToModel(ContactEntity entity) {
        return new Contact(entity.getName(), entity.getPhone());
    }

    // Helper method to convert Contact to ContactEntity
    public static ContactEntity modelToEntity(Contact contact) {
        return new ContactEntity(contact.getName(), contact.getPhone());
    }

    // Helper method to convert list of ContactEntity to list of Contact
    public static List<Contact> entitiesToModels(List<ContactEntity> entities) {
        List<Contact> contacts = new ArrayList<>();
        for (ContactEntity entity : entities) {
            contacts.add(entityToModel(entity));
        }
        return contacts;
    }
}