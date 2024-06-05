package com.dicoding.trashup.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.dicoding.trashup.data.db.DatabaseContract.WasteColumns.Companion.TABLE_NAME

class WasteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    // Untuk mengambil data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }

    // Untuk menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // Untuk memperbarui data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    // Untuk menghapus data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    // Delete semua data
    fun deleteAllData() {
        database.delete(TABLE_NAME, null, null)
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: WasteHelper? = null
        fun getInstance(context: Context): WasteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: WasteHelper(context)
            }
    }
}