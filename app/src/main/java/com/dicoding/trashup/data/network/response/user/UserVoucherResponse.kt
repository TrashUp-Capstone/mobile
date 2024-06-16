package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserVoucherResponse(

	@field:SerializedName("data")
	val data: List<VoucherItem>? = null
)

data class VoucherItem(

	@field:SerializedName("cost")
	val cost: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("modified_at")
	val modifiedAt: Any? = null
)
