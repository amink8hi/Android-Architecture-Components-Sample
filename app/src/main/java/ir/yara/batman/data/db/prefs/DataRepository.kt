package ir.yara.batman.data.db.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

data class DataRepository(private val context: Context) {

    private val preferences: SharedPreferences
    private val prefsTag = "ir.yara.batman.prefs"


    var token: String?
        @Singleton
        get() = preferences.getString(prefsTag + "fadaweqweqweqwdwqd", "")!!
        @Singleton
        set(value) = preferences.edit().putString(prefsTag + "fadaweqweqweqwdwqd", value).apply()


    init {
        preferences = context.getSharedPreferences(
            prefsTag, Context.MODE_PRIVATE
        )
    }
}