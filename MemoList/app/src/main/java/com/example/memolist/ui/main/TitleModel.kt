package com.example.memolist.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memolist.db.MemoRepository
import com.example.memolist.db.TitleItem

class TitleModel(application: Application): AndroidViewModel(application) {
    private val repository: MemoRepository = MemoRepository(application)
    private val allTitles: LiveData<List<TitleItem>>?
    private val searchResult: MutableLiveData<List<TitleItem>>

    init{
        allTitles = repository.allTitles
        searchResult = repository.searchResultsTitle
    }

    fun insertTitle(titleItem: TitleItem){
        repository.insertTitle(titleItem)
    }
    fun deleteTitle(id: Int){
        repository.deleteTitle(id)
    }
    fun getAllTitles(): LiveData<List<TitleItem>>?{
        return allTitles
    }
    fun getSearchResults(): MutableLiveData<List<TitleItem>>{
        return searchResult
    }
    fun updateTitle(titleItem: TitleItem){
        repository.updateTitle(titleItem)
    }
}