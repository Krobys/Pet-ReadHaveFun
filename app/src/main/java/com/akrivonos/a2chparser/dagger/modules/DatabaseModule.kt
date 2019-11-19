package com.akrivonos.a2chparser.dagger.modules

import android.content.Context
import androidx.room.Room
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.database.RoomAppDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDatabase(context: Context): RoomAppDatabase {
        return Room.databaseBuilder(context,
                RoomAppDatabase::class.java,
                DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideBoardsDao(database: RoomAppDatabase) = database.boardsDao()

    @JvmStatic
    @Singleton
    @Provides
    fun provideFilterItemDao(database: RoomAppDatabase) = database.filterItemDao()

}