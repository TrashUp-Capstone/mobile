package com.dicoding.trashup.data.network.response

import com.google.gson.annotations.SerializedName

data class DetailPickUpResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("weightWaste")
	val weightWaste: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("id")
	val id: String
)
