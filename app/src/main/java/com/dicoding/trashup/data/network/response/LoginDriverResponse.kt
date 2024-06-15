package com.dicoding.trashup.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginDriverResponse(

	@field:SerializedName("token")
	val token: String
)
