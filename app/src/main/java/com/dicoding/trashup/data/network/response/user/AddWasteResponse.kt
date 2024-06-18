package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class AddWasteResponse(

	@field:SerializedName("activity_id")
	val activityId: String,

	@field:SerializedName("message")
	val message: String
)
