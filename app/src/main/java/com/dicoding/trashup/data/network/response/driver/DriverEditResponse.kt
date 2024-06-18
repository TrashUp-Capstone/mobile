package com.dicoding.trashup.data.network.response.driver

import com.google.gson.annotations.SerializedName

data class DriverEditResponse(
	@field:SerializedName("message")
	val message: String,
)

data class DriverEditRequest(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("birth")
	val birth: String,

	@field:SerializedName("license_plate")
	val licensePlate: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("password")
	val password: String,
)
