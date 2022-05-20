package com.example.memolist.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memolist.db.ListItem
import com.example.memolist.db.MemoRepository

class ListModel(application: Application): AndroidViewModel(application) {
    private val repository: MemoRepository = MemoRepository(application)
    private val searchResult: MutableLiveData<List<ListItem>>

    init{
        searchResult = repository.searchResultsList
    }

    fun insertList(listItem: ListItem){
        repository.insertList(listItem)
    }
    fun deleteList(id: Int){
        repository.deleteTitle(id)
    }
    fun getAllLists(titleid: Int): LiveData<List<ListItem>>?{
        return repository.getAllList(titleid)
    }
    fun updateList(listItem: ListItem){
        repository.updateList(listItem)
    }
}