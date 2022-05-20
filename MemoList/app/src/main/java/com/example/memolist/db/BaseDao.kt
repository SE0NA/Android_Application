package com.example.memolist.db

import androidx.room.Delete
import androidx.room.Insert

interface BaseDao<T> {
    @Insert
    fun insert(obj: T)

    @Delete
    fun delete(obj: T)
}