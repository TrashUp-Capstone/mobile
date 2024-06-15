package com.dicoding.trashup.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Voucher (
    val points: Int,
    val price: Int,
    val date: String
): Parcelable