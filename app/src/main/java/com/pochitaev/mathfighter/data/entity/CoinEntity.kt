package com.pochitaev.mathfighter.data.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "coins") var coins: Int?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(coins)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoinEntity> {
        override fun createFromParcel(parcel: Parcel): CoinEntity {
            return CoinEntity(parcel)
        }

        override fun newArray(size: Int): Array<CoinEntity?> {
            return arrayOfNulls(size)
        }
    }
}



