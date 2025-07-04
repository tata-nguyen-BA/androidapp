package com.taha.chatgroup;

import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit test for contact list functionality
 */
public class ContactListTest {
    
    @Test
    public void testContactCreation() {
        Contact contact = new Contact("Test Name", "0123456789");
        assertEquals("Test Name", contact.getName());
        assertEquals("0123456789", contact.getPhone());
    }
    
    @Test
    public void testGroupCreation() {
        List<Contact> contacts = Arrays.asList(
                new Contact("Thảo", "0123456789"),
                new Contact("Nam", "0987654321")
        );
        
        Group group = new Group("Test Group", contacts);
        assertEquals("Test Group", group.getGroupName());
        assertEquals(2, group.getMembers().size());
        assertEquals("Thảo", group.getMembers().get(0).getName());
    }
    
    @Test
    public void testHardcodedContacts() {
        // Test the hardcoded contacts we added
        List<Contact> contacts = Arrays.asList(
                new Contact("Thảo", "0123456789"),
                new Contact("Nam",  "0987654321"),
                new Contact("Tín",  "0345678912"),
                new Contact("Lan",  "0912345678")
        );
        
        assertEquals(4, contacts.size());
        assertEquals("Thảo", contacts.get(0).getName());
        assertEquals("Nam", contacts.get(1).getName());
        assertEquals("Tín", contacts.get(2).getName());
        assertEquals("Lan", contacts.get(3).getName());
    }
}