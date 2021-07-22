package com.alif.mangaapps.data.entity

import android.os.Parcel
import android.os.Parcelable

data class MangaEntity(
    var id: String?,
    var title: String?,
    var desc: String?,
    var demograph: String?,
    var coverArt: String?,
    var favorites: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(demograph)
        parcel.writeString(coverArt)
        parcel.writeByte(if (favorites) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MangaEntity> {
        override fun createFromParcel(parcel: Parcel): MangaEntity {
            return MangaEntity(parcel)
        }

        override fun newArray(size: Int): Array<MangaEntity?> {
            return arrayOfNulls(size)
        }
    }
}
