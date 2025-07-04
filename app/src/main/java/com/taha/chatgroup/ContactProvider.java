package com.taha.chatgroup;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.taha.chatgroup.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactProvider {
    
    public static List<Contact> getDeviceContacts(Context context) {
        List<Contact> contacts = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );
        
        if (cursor != null) {
            try {
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                
                while (cursor.moveToNext()) {
                    String name = cursor.getString(nameIndex);
                    String phone = cursor.getString(phoneIndex);
                    
                    if (name != null && phone != null && !name.trim().isEmpty() && !phone.trim().isEmpty()) {
                        contacts.add(new Contact(name.trim(), phone.trim()));
                    }
                }
            } finally {
                cursor.close();
            }
        }
        
        return contacts;
    }
}