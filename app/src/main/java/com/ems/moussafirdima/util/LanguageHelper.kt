package com.ems.moussafirdima.util

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LanguageHelper {
    companion object {
        const val GENERAL_STORAGE = "GENERAL_STORAGE"
        const val KEY_USER_LANGUAGE = "KEY_USER_LANGUAGE"

        fun updateLanguage(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = context.resources
            val config = Configuration(res.configuration)
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }

        fun storeLanguage(context: Context, language: String) {
            context.getSharedPreferences(GENERAL_STORAGE, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_USER_LANGUAGE, language)
                .apply()
        }

        fun getUserLanguage(context: Context): String {
            return context.getSharedPreferences(GENERAL_STORAGE, Context.MODE_PRIVATE)
                .getString(KEY_USER_LANGUAGE, null) ?: "en"
        }
    }

}