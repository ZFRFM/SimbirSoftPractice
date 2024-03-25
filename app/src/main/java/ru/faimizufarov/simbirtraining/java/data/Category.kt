package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcel
import android.os.Parcelable

data class Category(
    val enumValue: HelpCategoryEnum?,
    var checked: Boolean = true,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(HelpCategoryEnum::class.java.classLoader),
        parcel.readByte() != 0.toByte(),
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int,
    ) {
        parcel.writeParcelable(enumValue, flags)
        parcel.writeByte(if (checked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}
