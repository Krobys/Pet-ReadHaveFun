package com.akrivonos.a2chparser.pojomodel.boardmodel

import android.os.Parcel
import android.os.Parcelable

class BoardConcrete
// Getter Methods

protected constructor(`in`: Parcel) : Parcelable {
    // Setter Methods

    var bump_limit: Float = 0.toFloat()
    var category: String? = null
    var default_name: String? = null
    var enable_dices: Float = 0.toFloat()
    var enable_flags: Float = 0.toFloat()
    var enable_icons: Float = 0.toFloat()
    var enable_likes: Float = 0.toFloat()
    var enable_names: Float = 0.toFloat()
    var enable_oekaki: Float = 0.toFloat()
    var enable_posting: Float = 0.toFloat()
    var enable_sage: Float = 0.toFloat()
    var enable_shield: Float = 0.toFloat()
    var enable_subject: Float = 0.toFloat()
    var enable_thread_tags: Float = 0.toFloat()
    var enable_trips: Float = 0.toFloat()
    var icons: List<Icons>? = null
    var id: String? = null
    var name: String? = null
    var pages: Float = 0.toFloat()
    var sage: Float = 0.toFloat()
    var tripcodes: Float = 0.toFloat()

    init {
        bump_limit = `in`.readFloat()
        category = `in`.readString()
        default_name = `in`.readString()
        enable_dices = `in`.readFloat()
        enable_flags = `in`.readFloat()
        enable_icons = `in`.readFloat()
        enable_likes = `in`.readFloat()
        enable_names = `in`.readFloat()
        enable_oekaki = `in`.readFloat()
        enable_posting = `in`.readFloat()
        enable_sage = `in`.readFloat()
        enable_shield = `in`.readFloat()
        enable_subject = `in`.readFloat()
        enable_thread_tags = `in`.readFloat()
        enable_trips = `in`.readFloat()
        id = `in`.readString()
        name = `in`.readString()
        pages = `in`.readFloat()
        sage = `in`.readFloat()
        tripcodes = `in`.readFloat()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeFloat(bump_limit)
        parcel.writeString(category)
        parcel.writeString(default_name)
        parcel.writeFloat(enable_dices)
        parcel.writeFloat(enable_flags)
        parcel.writeFloat(enable_icons)
        parcel.writeFloat(enable_likes)
        parcel.writeFloat(enable_names)
        parcel.writeFloat(enable_oekaki)
        parcel.writeFloat(enable_posting)
        parcel.writeFloat(enable_sage)
        parcel.writeFloat(enable_shield)
        parcel.writeFloat(enable_subject)
        parcel.writeFloat(enable_thread_tags)
        parcel.writeFloat(enable_trips)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeFloat(pages)
        parcel.writeFloat(sage)
        parcel.writeFloat(tripcodes)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BoardConcrete> = object : Parcelable.Creator<BoardConcrete> {
            override fun createFromParcel(`in`: Parcel): BoardConcrete {
                return BoardConcrete(`in`)
            }

            override fun newArray(size: Int): Array<BoardConcrete> {
                return emptyArray()
            }
        }
    }
}
