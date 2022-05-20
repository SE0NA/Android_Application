package com.example.memolist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {
    @Insert
    fun insertList(listItem: ListItem)

    @Query("DELETE FROM list WHERE id=:id")
    fun deleteList(id:Int)

    @Query("SELECT * FROM list WHERE titleid = :titleid")
    fun getAllListsFrom(titleid: Int): LiveData<List<ListItem>>

    @Query("DELETE FROM list WHERE titleid = :titleid")
    fun deleteListByTitle(titleid: Int)

    @Update
    fun updateList(listItem: ListItem)
}