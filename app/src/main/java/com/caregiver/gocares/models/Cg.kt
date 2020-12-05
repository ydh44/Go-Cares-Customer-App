package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

data class Cg (
				@field:SerializedName("id") val id: String?,
				@field:SerializedName("photo") val photo: String?,
				@field:SerializedName("name") val name: String?,
				@field:SerializedName("gender") val gender: String?,
				@field:SerializedName("address") val address: String?,
				@field:SerializedName("keahlian") val keahlian: String?,
				@field:SerializedName("rating") val rating: String?
				)