package com.example.memolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TitleItem::class, ListItem::class), version = 1)
abstract class MemoRoomDatabase: RoomDatabase() {
    abstract fun titleDao(): TitleDao
    abstract fun listDao(): ListDao

    companion object{
        private var INSTANCE: MemoRoomDatabase? = null

        internal fun getDatabase(context: Context): MemoRoomDatabase?{
            if(INSTANCE==null){
                synchronized(MemoRoomDatabase::class.java){
                    if(INSTANCE == null){
                        INSTANCE =
                            Room.databaseBuilder<MemoRoomDatabase>(
                                context.applicationContext,
                                MemoRoomDatabase::class.java,
                                "memo_database"
                            ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}