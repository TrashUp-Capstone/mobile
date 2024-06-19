package com.dicoding.trashup.data.network.response.driver

import com.google.gson.annotations.SerializedName

data class ListWastePickUpResponse(

	@field:SerializedName("data")
	val dataListWaste: List<DataListWasteItem>
)

data class DataListWasteItem(

	@field:SerializedName("activity_id")
	val activityId: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("waste_type_id")
	val wasteTypeId: Int,

	@field:SerializedName("id")
	val id: String
)
