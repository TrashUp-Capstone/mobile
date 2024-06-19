package com.dicoding.trashup.data.network.response.driver

import com.google.gson.annotations.SerializedName

data class PickUpUserResponse(

	@field:SerializedName("data")
	val data: List<DataPickUpUser>
)

data class DataPickUpUser(

	@field:SerializedName("activity_time")
	val activityTime: String,

	@field:SerializedName("driver_id")
	val driverId: String,

	@field:SerializedName("activity_status_id")
	val activityStatusId: Int,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("total_weight")
	val totalWeight: Int,

	@field:SerializedName("points")
	val points: Int,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("started_at")
	val startedAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("modified_at")
	val modifiedAt: String,

	@field:SerializedName("ended_at")
	val endedAt: String,

	@field:SerializedName("longitude")
	val longitude: Any
)
