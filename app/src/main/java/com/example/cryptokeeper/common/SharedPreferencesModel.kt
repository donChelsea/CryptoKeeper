package com.example.cryptokeeper.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesModel(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()
    private val gson = Gson()

    fun put(data: String) {
        val allItems = mutableSetOf(data)
        searchHistory.forEach { allItems.add(it) }
        val jsonData = gson.toJson(allItems)
        editor.putString(SEARCH_HISTORY, jsonData)
        editor.commit()
    }

    fun clear(item: String) {
        editor.remove(item)
        editor.commit()
    }

    val searchHistory: Set<String>
        get() {
            val jsonData = pref.getString(SEARCH_HISTORY, null)
            var list: Set<String> = mutableSetOf()
            if (jsonData != null) {
                val arrString = gson.fromJson(jsonData, Array<String>::class.java)
                list = setOf(*arrString)
            }
            return list
        }

    companion object {
        private const val SEARCH_HISTORY = "searchHistory"
        private const val PREF_NAME = "sharedPref"
    }
}