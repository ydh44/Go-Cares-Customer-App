package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("success")
	val success: Successes? = null
)

data class Successes(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("notif_token")
	val notifToken: Any? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("esccort_id")
	val esccortId: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("age")
	val age: String? = null
)
