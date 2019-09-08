package com.cm.airvedaasmt.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cm.airvedaasmt.data.model.Feed;

import java.util.List;

@Dao
public interface FeedDao {

    @Query("Select * from feed")
    List<Feed> getAll();

    @Query("Select * from feed where id = :id")
    Feed getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Feed> feeds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Feed feed);

    @Query("Delete from feed")
    void deleteAll();

}
