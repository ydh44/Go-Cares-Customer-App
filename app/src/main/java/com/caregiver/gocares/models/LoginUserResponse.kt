package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(

	@field:SerializedName("success")
	val success: Success? = null
)

data class Success(

	@field:SerializedName("token")
	val token: String? = null
)
