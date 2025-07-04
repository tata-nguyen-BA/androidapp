package com.taha.chatgroup.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_contact_join", 
        foreignKeys = {
                @ForeignKey(entity = GroupEntity.class,
                        parentColumns = "id",
                        childColumns = "group_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = ContactEntity.class,
                        parentColumns = "id",
                        childColumns = "contact_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("group_id"), @Index("contact_id")})
public class GroupContactJoin {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "group_id")
    public int groupId;

    @ColumnInfo(name = "contact_id")
    public int contactId;

    public GroupContactJoin(int groupId, int contactId) {
        this.groupId = groupId;
        this.contactId = contactId;
    }

    public int getId() { return id; }
    public int getGroupId() { return groupId; }
    public int getContactId() { return contactId; }

    public void setId(int id) { this.id = id; }
    public void setGroupId(int groupId) { this.groupId = groupId; }
    public void setContactId(int contactId) { this.contactId = contactId; }
}