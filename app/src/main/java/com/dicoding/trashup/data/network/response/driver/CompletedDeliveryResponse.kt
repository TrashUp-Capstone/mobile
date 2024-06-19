package com.dicoding.trashup.data.network.response.driver

import com.google.gson.annotations.SerializedName

data class CompletedDeliveryResponse(

	@field:SerializedName("message")
	val message: String
)
