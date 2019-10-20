package com.akrivonos.a2chparser.models

import android.os.Parcel
import android.os.Parcelable

class SaveTypeModel(val nameSave: String?, val typeSaveItem: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(nameSave)
        dest?.writeInt(typeSaveItem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SaveTypeModel> {
        override fun createFromParcel(parcel: Parcel): SaveTypeModel {
            return SaveTypeModel(parcel)
        }

        override fun newArray(size: Int): Array<SaveTypeModel?> {
            return arrayOfNulls(size)
        }
    }
}