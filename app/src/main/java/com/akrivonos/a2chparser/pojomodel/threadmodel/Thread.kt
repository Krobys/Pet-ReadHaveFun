package com.akrivonos.a2chparser.pojomodel.threadmodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Thread() : Parcelable {

    @SerializedName("banned")
    @Expose
    var banned: Int? = null
    @SerializedName("closed")
    @Expose
    var closed: Int? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("endless")
    @Expose
    var endless: Int? = null
    @SerializedName("files")
    @Expose
    var files: List<File>? = null
    @SerializedName("files_count")
    @Expose
    var filesCount: Int? = null
    @SerializedName("lasthit")
    @Expose
    var lasthit: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("num")
    @Expose
    var num: String? = null
    @SerializedName("op")
    @Expose
    var op: Int? = null
    @SerializedName("parent")
    @Expose
    var parent: String? = null
    @SerializedName("posts_count")
    @Expose
    var postsCount: Int? = null
    @SerializedName("sticky")
    @Expose
    var sticky: Int? = null
    @SerializedName("subject")
    @Expose
    var subject: String? = null
    @SerializedName("tags")
    @Expose
    var tags: String? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: Int? = null
    @SerializedName("trip")
    @Expose
    var trip: String? = null

    constructor(parcel: Parcel) : this() {
        banned = parcel.readValue(Int::class.java.classLoader) as? Int
        closed = parcel.readValue(Int::class.java.classLoader) as? Int
        comment = parcel.readString()
        date = parcel.readString()
        email = parcel.readString()
        endless = parcel.readValue(Int::class.java.classLoader) as? Int
        filesCount = parcel.readValue(Int::class.java.classLoader) as? Int
        lasthit = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        num = parcel.readString()
        op = parcel.readValue(Int::class.java.classLoader) as? Int
        parent = parcel.readString()
        postsCount = parcel.readValue(Int::class.java.classLoader) as? Int
        sticky = parcel.readValue(Int::class.java.classLoader) as? Int
        subject = parcel.readString()
        tags = parcel.readString()
        timestamp = parcel.readValue(Int::class.java.classLoader) as? Int
        trip = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(banned)
        parcel.writeValue(closed)
        parcel.writeString(comment)
        parcel.writeString(date)
        parcel.writeString(email)
        parcel.writeValue(endless)
        parcel.writeValue(filesCount)
        parcel.writeValue(lasthit)
        parcel.writeString(name)
        parcel.writeString(num)
        parcel.writeValue(op)
        parcel.writeString(parent)
        parcel.writeValue(postsCount)
        parcel.writeValue(sticky)
        parcel.writeString(subject)
        parcel.writeString(tags)
        parcel.writeValue(timestamp)
        parcel.writeString(trip)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Thread> {
        override fun createFromParcel(parcel: Parcel): Thread {
            return Thread(parcel)
        }

        override fun newArray(size: Int): Array<Thread?> {
            return arrayOfNulls(size)
        }
    }

}
