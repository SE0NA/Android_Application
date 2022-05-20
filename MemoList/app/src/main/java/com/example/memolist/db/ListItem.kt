package com.example.memolist.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "list")
class ListItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0

    @NonNull
    var titleid: Int = 0

    @NonNull
    var text:String = ""

    @NonNull
    var onoff: Boolean = false

    constructor(titleid:Int, text:String, onoff:Boolean){
        this.titleid = titleid
        this.text = text
        this.onoff = onoff
    }
}