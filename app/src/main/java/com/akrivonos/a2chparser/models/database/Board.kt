package com.akrivonos.a2chparser.models.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete

@Entity(tableName = "board")
class Board() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var idAuto: Int = 0
    @ColumnInfo
    var nameBoards: String? = null
    @ColumnInfo
    var idBoard: String? = null


    @Ignore
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(idAuto)
        dest?.writeString(idBoard)
        dest?.writeString(nameBoards)
    }

    @Ignore
    override fun describeContents(): Int = 0

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

    companion object CREATOR : Parcelable.Creator<Board> {
        override fun createFromParcel(parcel: Parcel): Board {
            return Board(parcel)
        }

        override fun newArray(size: Int): Array<Board?> {
            return arrayOfNulls(size)
        }
    }

}