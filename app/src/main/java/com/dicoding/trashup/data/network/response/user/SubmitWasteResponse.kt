package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class SubmitWasteResponse(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("message")
	val message: String
)
