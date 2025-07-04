package com.taha.chatgroup;

import android.app.Application;

import com.taha.chatgroup.repository.ContactRepository;

public class ChatGroupApplication extends Application {
    
    private ContactRepository contactRepository;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize repository and default data
        contactRepository = new ContactRepository(this);
        contactRepository.initializeDefaultContacts();
    }
    
    public ContactRepository getContactRepository() {
        return contactRepository;
    }
}