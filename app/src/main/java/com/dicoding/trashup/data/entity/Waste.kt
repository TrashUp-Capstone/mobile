package com.dicoding.trashup.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Waste(
    var id : Int = 0,
    var typeWaste: String = "",
    var photo: String= "",
    var weight: Double= 0.0,
    var points: Int = 0
) : Parcelable