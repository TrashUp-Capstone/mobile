package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserRedeemedVoucherResponse(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class RedeemVoucherRequest(
	@SerializedName("voucher_type_id") val voucherTypeId: Int
)
