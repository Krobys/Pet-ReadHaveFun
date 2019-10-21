package com.akrivonos.a2chparser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akrivonos.a2chparser.models.database.Board

@Database(entities = [Board::class], version = 1)
abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun boardsDao(): BoardsDao

    companion object {
        private var INSTANCE: RoomAppDatabase? = null

        fun getAppDataBase(context: Context): RoomAppDatabase? {
            if (INSTANCE == null) {
                synchronized(RoomAppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomAppDatabase::class.java, "database.save").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}