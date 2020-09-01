package ir.yara.batman.data.remote.responce.detail

import android.media.Rating
import android.os.Parcel
import android.os.Parcelable
import ir.yara.batman.data.remote.BaseResponse


data class DetailListModel(
    var Title: String? = null,
    var Year: String? = null,
    var Rated: String? = null,
    var Released: String? = null,
    var Runtime: String? = null,
    var Genre: String? = null,
    var Director: String? = null,
    var Writer: String? = null,
    var Actors: String? = null,
    var Plot: String? = null,
    var Language: String? = null,
    var Country: String? = null,
    var Awards: String? = null,
    var Poster: String? = null,
    var Ratings: List<Rating>? = null,
    var Metascore: String? = null,
    var imdbRating: String? = null,
    var imdbVotes: String? = null,
    var imdbID: String? = null,
    var Type: String? = null,
    var DVD: String? = null,
    var BoxOffice: String? = null,
    var Production: String? = null,
    var Website: String? = null,
    var response: String? = null
) : BaseResponse(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Rating.CREATOR),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Year)
        parcel.writeString(Rated)
        parcel.writeString(Released)
        parcel.writeString(Runtime)
        parcel.writeString(Genre)
        parcel.writeString(Director)
        parcel.writeString(Writer)
        parcel.writeString(Actors)
        parcel.writeString(Plot)
        parcel.writeString(Language)
        parcel.writeString(Country)
        parcel.writeString(Awards)
        parcel.writeString(Poster)
        parcel.writeTypedList(Ratings)
        parcel.writeString(Metascore)
        parcel.writeString(imdbRating)
        parcel.writeString(imdbVotes)
        parcel.writeString(imdbID)
        parcel.writeString(Type)
        parcel.writeString(DVD)
        parcel.writeString(BoxOffice)
        parcel.writeString(Production)
        parcel.writeString(Website)
        parcel.writeString(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailListModel> {
        override fun createFromParcel(parcel: Parcel): DetailListModel {
            return DetailListModel(parcel)
        }

        override fun newArray(size: Int): Array<DetailListModel?> {
            return arrayOfNulls(size)
        }
    }

}