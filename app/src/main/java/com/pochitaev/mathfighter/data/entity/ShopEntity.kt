package com.pochitaev.mathfighter.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price")
data class ShopEntity(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "price") var price: String?,
    @ColumnInfo(name = "isSolded") val isSolded: Boolean,
    @ColumnInfo(name = "value") var value : Int?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeByte(if (isSolded) 1 else 0)
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShopEntity> {
        override fun createFromParcel(parcel: Parcel): ShopEntity {
            return ShopEntity(parcel)
        }

        override fun newArray(size: Int): Array<ShopEntity?> {
            return arrayOfNulls(size)
        }
    }
}


