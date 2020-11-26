package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

data class CgResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("dalam_proses")
	val dalamProses: List<Int?>? = null
)

data class DataItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("salary")
	val salary: String? = null,

	@field:SerializedName("keahlian")
	val keahlian: String? = null,

	@field:SerializedName("age")
	val age: String? = null
)

data class Data(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)
