package com.example.memolist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TitleDao {
    @Insert
    fun insertTitle(titleItem: TitleItem)

    @Query("DELETE FROM title WHERE id =:id")
    fun deleteTitle(id:Int)

    @Query("SELECT * FROM title ORDER BY id")
    fun getAllTitles(): LiveData<List<TitleItem>>

    @Update
    fun updateTitle(titleItem: TitleItem)
}