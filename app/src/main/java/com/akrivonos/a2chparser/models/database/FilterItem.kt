package com.akrivonos.a2chparser.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filteritem")
class FilterItem() {
    @PrimaryKey(autoGenerate = true)
    var idAuto: Int = 0
    @ColumnInfo
    var filterText: String? = null
}