package com.example.homework13

import android.os.Parcel
import android.os.Parcelable

data class FormField(
    val field_id: Int,
    val hint: String,
    val field_type: String,
    val keyboard: String?,
    val required: Boolean,
    val is_active: Boolean,
    val icon: String,
    var enteredValue: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(field_id)
        parcel.writeString(hint)
        parcel.writeString(field_type)
        parcel.writeString(keyboard)
        parcel.writeByte(if (required) 1 else 0)
        parcel.writeByte(if (is_active) 1 else 0)
        parcel.writeString(icon)
        parcel.writeString(enteredValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FormField> {
        override fun createFromParcel(parcel: Parcel): FormField {
            return FormField(parcel)
        }

        override fun newArray(size: Int): Array<FormField?> {
            return arrayOfNulls(size)
        }
    }
}




