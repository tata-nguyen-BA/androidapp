package com.taha.chatgroup.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class GroupEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "group_name")
    public String groupName;

    public GroupEntity(String groupName) {
        this.groupName = groupName;
    }

    public int getId() { return id; }
    public String getGroupName() { return groupName; }

    public void setId(int id) { this.id = id; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
}