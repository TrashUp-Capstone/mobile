package com.dicoding.trashup.data.network.response.driver

import com.google.gson.annotations.SerializedName

data class DriverResponse(

	@field:SerializedName("data")
	val data: DataDriver
)

data class DataDriver(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("license_plate")
	val licensePlate: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("birth")
	val birth: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("modified_at")
	val modifiedAt: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
)
