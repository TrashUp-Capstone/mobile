package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: UserData? = null
)

data class UserData(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("birth")
	val birth: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("modified_at")
	val modifiedAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
