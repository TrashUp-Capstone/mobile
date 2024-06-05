package com.dicoding.trashup.data.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class WasteColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "waste"
            const val _ID = "_id"
            const val TYPEWASTE = "typewaste"
            const val PHOTO = "photo"
            const val WEIGHT = "weight"
        }
    }
}