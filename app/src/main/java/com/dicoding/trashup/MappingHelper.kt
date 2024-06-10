package com.dicoding.trashup

import android.database.Cursor
import com.dicoding.trashup.data.db.DatabaseContract
import com.dicoding.trashup.data.entity.Waste
import com.dicoding.trashup.utils.getImageUri

object MappingHelper {
    // Mengonversi dari Cursor ke Arraylist
    fun mapCursorToArrayList(wasteCursor: Cursor?): ArrayList<Waste> {
        val wasteList = ArrayList<Waste>()
        wasteCursor?.apply {
            while (moveToNext()) {
                //  Di sini kita ambil datanya satu per satu menggunakan getColumnIndexOrThrow
                //  dan dimasukkan ke dalam ArrayList.
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.WasteColumns._ID))
                val typeWaste = getString(getColumnIndexOrThrow(DatabaseContract.WasteColumns.TYPEWASTE))
                val weight = getDouble(getColumnIndexOrThrow(DatabaseContract.WasteColumns.WEIGHT))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.WasteColumns.PHOTO))
                val points = getInt(getColumnIndexOrThrow(DatabaseContract.WasteColumns.POINTS))
                wasteList.add(Waste(id, typeWaste, photo, weight, points))
            }
        }
        return wasteList
    }
}