package com.akrivonos.a2chparser.models.database

import android.os.Parcel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete

@Entity(tableName = "board")
class Board() {
    @PrimaryKey(autoGenerate = true)
    var idAuto: Int = 0
    @ColumnInfo
    var nameBoards: String? = null
    @ColumnInfo
    var idBoard: String? = null

    @Ignore
    constructor(parcel: Parcel) : this() {
        nameBoards = parcel.readString()
        idBoard = parcel.readString()
    }

    @Ignore
    constructor(boardConcrete: BoardConcrete) : this() {
        nameBoards = boardConcrete.name
        idBoard = boardConcrete.id
    }

}