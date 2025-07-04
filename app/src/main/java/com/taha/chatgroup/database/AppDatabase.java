package com.taha.chatgroup.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.taha.chatgroup.database.dao.ContactDao;
import com.taha.chatgroup.database.dao.GroupDao;
import com.taha.chatgroup.database.entity.ContactEntity;
import com.taha.chatgroup.database.entity.GroupEntity;
import com.taha.chatgroup.database.entity.GroupContactJoin;

@Database(
    entities = {ContactEntity.class, GroupEntity.class, GroupContactJoin.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    
    private static volatile AppDatabase INSTANCE;
    
    public abstract ContactDao contactDao();
    public abstract GroupDao groupDao();
    
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "chatgroup_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}