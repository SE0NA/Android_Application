package com.example.memolist

import com.example.memolist.db.ListItem

public interface OnListClick {
    fun onClick(listItem: ListItem)
    fun deleteList(id:Int)
    fun setSwipe()
}

