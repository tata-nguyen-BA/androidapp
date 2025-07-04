package com.taha.chatgroup;

import org.junit.Test;

import static org.junit.Assert.*;

import com.taha.chatgroup.models.Contact;
import com.taha.chatgroup.models.Group;
import com.taha.chatgroup.repository.ContactRepository;

import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testContactModelConversion() {
        // Test ContactRepository helper methods
        Contact contact = new Contact("Test User", "123456789");
        
        // Test basic properties
        assertEquals("Test User", contact.getName());
        assertEquals("123456789", contact.getPhone());
    }

    @Test
    public void testGroupModel() {
        // Test Group model
        List<Contact> contacts = Arrays.asList(
                new Contact("User 1", "111"),
                new Contact("User 2", "222")
        );
        
        Group group = new Group("Test Group", contacts);
        
        assertEquals("Test Group", group.getGroupName());
        assertEquals(2, group.getMembers().size());
        assertEquals("User 1", group.getMembers().get(0).getName());
    }
}