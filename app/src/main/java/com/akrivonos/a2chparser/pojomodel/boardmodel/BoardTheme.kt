package com.akrivonos.a2chparser.pojomodel.boardmodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

class BoardTheme() : Parcelable {

    @Expose
    var boardThemeName: String? = null
    @Expose
    var boardConcretes: List<BoardConcrete>? = null

    constructor(boardConcretes: List<BoardConcrete>?) : this() {
        if (boardConcretes != null && boardConcretes.isNotEmpty()) {
            boardThemeName = boardConcretes[0].category
            this.boardConcretes = boardConcretes
        }
    }

    constructor(parcel: Parcel) : this() {
        boardThemeName = parcel.readString()
        boardConcretes = parcel.createTypedArrayList(BoardConcrete.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(boardThemeName)
        parcel.writeTypedList(boardConcretes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoardTheme> {
        override fun createFromParcel(parcel: Parcel): BoardTheme {
            return BoardTheme(parcel)
        }

        override fun newArray(size: Int): Array<BoardTheme?> {
            return arrayOfNulls(size)
        }
    }


}
