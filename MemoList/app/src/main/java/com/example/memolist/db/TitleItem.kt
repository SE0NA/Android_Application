package com.example.memolist.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "title")
class TitleItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0

    @NonNull
    var title:String = ""

    constructor(title:String){
        this.title = title
    }
}