package com.taha.chatgroup.models;
import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {
    private String groupName;
    private List<Contact> members;

    public Group(String groupName, List<Contact> members) {
        this.groupName = groupName;
        this.members   = members;
    }

    public String getGroupName()    { return groupName; }
    public List<Contact> getMembers() { return members; }
}
