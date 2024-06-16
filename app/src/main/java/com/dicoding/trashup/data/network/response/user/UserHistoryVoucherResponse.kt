package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserHistoryVoucherResponse(

	@field:SerializedName("data")
	val data: List<HistoryVoucherItem>? = null
)

data class HistoryVoucherItem(

	val voucher: VoucherItem? = null,

	@field:SerializedName("points_after")
	val pointsAfter: Int? = null,

	@field:SerializedName("used_at")
	val usedAt: Any? = null,

	@field:SerializedName("points_before")
	val pointsBefore: Int? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("voucher_type_id")
	val voucherTypeId: Int? = null,

	@field:SerializedName("modified_at")
	val modifiedAt: Any? = null,

	@field:SerializedName("is_used")
	val isUsed: Int? = null
)
