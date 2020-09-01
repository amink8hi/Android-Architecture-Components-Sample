package ir.yara.batman.data.remote.responce.detail

import android.os.Parcel
import android.os.Parcelable
import ir.yara.batman.data.remote.BaseResponse


data class Rating(
    var source: String? = null,
    var yevaluear: String? = null
) : BaseResponse(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(source)
        parcel.writeString(yevaluear)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rating> {
        override fun createFromParcel(parcel: Parcel): Rating {
            return Rating(parcel)
        }

        override fun newArray(size: Int): Array<Rating?> {
            return arrayOfNulls(size)
        }
    }
}