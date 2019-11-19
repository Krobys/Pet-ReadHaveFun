package com.akrivonos.a2chparser.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.akrivonos.a2chparser.models.database.FilterItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FilterItemDao {

    @Query("SELECT * FROM filteritem")
    fun getFilteredItemsList(): Single<List<FilterItem>>

    @Insert
    fun addFilteredItem(filterItem: FilterItem?): Completable

    @Query("DELETE FROM filteritem WHERE filterText = (:textFilter)")
    fun removeFilteredItems(textFilter: String?): Completable

}