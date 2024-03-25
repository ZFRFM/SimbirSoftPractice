package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcel
import android.os.Parcelable
import ru.faimizufarov.simbirtraining.R

enum class HelpCategoryEnum(val imageView: Int, val nameCategory: Int) : Parcelable {
    CHILDREN(imageView = R.drawable.children_category, nameCategory = R.string.children),
    ADULTS(imageView = R.drawable.adults_category, nameCategory = R.string.adults),
    ELDERLY(imageView = R.drawable.elderly_category, nameCategory = R.string.elderly),
    ANIMALS(imageView = R.drawable.animals_category, nameCategory = R.string.animals),
    EVENTS(imageView = R.drawable.events_category, nameCategory = R.string.events),
    ;

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
    ) {
    }

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int,
    ) {
        parcel.writeInt(imageView)
        parcel.writeInt(nameCategory)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HelpCategoryEnum> {
        override fun createFromParcel(parcel: Parcel): HelpCategoryEnum {
            return entries[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<HelpCategoryEnum?> {
            return arrayOfNulls(size)
        }
    }
}
