package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName


data class UserEditResponse(
	@field:SerializedName("message")
	val message: String,
)
data class UserEditRequest(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("birth")
	val birth: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("password")
	val password: String,
)
