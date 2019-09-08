package com.cm.airvedaasmt.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cm.airvedaasmt.data.model.Feed;

@Database(entities = {
        Feed.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FeedDao feedDao();

}
