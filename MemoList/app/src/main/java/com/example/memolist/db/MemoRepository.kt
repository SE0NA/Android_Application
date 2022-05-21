package com.example.memolist.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoRepository(application: Application) {
    val searchResultsTitle = MutableLiveData<List<TitleItem>>()
    val searchResultsList = MutableLiveData<List<ListItem>>()

    val allTitles: LiveData<List<TitleItem>>?
    var allLists: LiveData<List<ListItem>>? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var titleDao: TitleDao?
    private var listDao: ListDao?

    init{
        val db: MemoRoomDatabase? =
            MemoRoomDatabase.getDatabase(application)
        titleDao = db?.titleDao()
        listDao = db?.listDao()

        allTitles = titleDao?.getAllTitles()

    }
    fun getAllList(titleid: Int): LiveData<List<ListItem>>?{
        allLists = listDao?.getAllListsFrom(titleid)
        return allLists
    }

    // Title
    fun insertTitle(titleItem: TitleItem){
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsertTitle(titleItem)
        }
    }
    private suspend fun asyncInsertTitle(titleItem: TitleItem){
        titleDao?.insertTitle(titleItem)
    }

    fun deleteTitle(id: Int){
        coroutineScope.launch(Dispatchers.IO){
            asyncDeleteTitle(id)
        }
    }
    private suspend fun asyncDeleteTitle(id: Int){
        listDao?.deleteListByTitle(id)
        titleDao?.deleteTitle(id)
    }

    fun updateTitle(titleItem: TitleItem){
        coroutineScope.launch(Dispatchers.IO){
            asyncUpdateTitle(titleItem)
        }
    }
    private suspend fun asyncUpdateTitle(titleItem: TitleItem){
        titleDao?.updateTitle(titleItem)
    }

    // List
    fun insertList(listItem: ListItem){
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsertList(listItem)
        }
    }
    private suspend fun asyncInsertList(listItem: ListItem){
        listDao?.insertList(listItem)
    }

    fun deleteList(id:Int){
        coroutineScope.launch(Dispatchers.IO){
            asyncDeleteList(id)
        }
    }
    private suspend fun asyncDeleteList(id: Int){
        listDao?.deleteList(id)
    }

    fun updateList(listItem: ListItem){
        coroutineScope.launch(Dispatchers.IO) {
            asyncUpdateList(listItem)
        }
    }
    private suspend fun asyncUpdateList(listItem: ListItem){
        listDao?.updateList(listItem)
    }

    fun deleteAllListIn(titleid: Int){
        coroutineScope.launch(Dispatchers.IO){
            asyncDeleteAllListIn(titleid)
        }
    }
    private suspend fun asyncDeleteAllListIn(titleid: Int){
        listDao?.deleteListByTitle(titleid)
    }
}