package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class SubmitPhotoResponse(

	@field:SerializedName("message")
	val message: String
)
