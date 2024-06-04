package com.dicoding.trashup.data.network.response

import com.google.gson.annotations.SerializedName

data class PointsResponse(

	@field:SerializedName("PointsResponse")
	val pointsResponse: List<PointsResponseItem>
)

data class PointsResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("weightWaste")
	val weightWaste: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("points")
	val points: Int
)
