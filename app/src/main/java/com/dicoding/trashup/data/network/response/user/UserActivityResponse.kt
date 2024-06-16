package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserActivityResponse(

	@field:SerializedName("data")
	val data: List<DataActivities>? = null
)

data class DataActivities(

	@field:SerializedName("activity_time")
	val activityTime: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("activity_status_id")
	val activityStatusId: Int? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("total_weight")
	val totalWeight: Int? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("started_at")
	val startedAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("modified_at")
	val modifiedAt: String? = null,

	@field:SerializedName("ended_at")
	val endedAt: String? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)
