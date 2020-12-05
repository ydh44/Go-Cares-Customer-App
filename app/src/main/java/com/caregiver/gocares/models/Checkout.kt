package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

data class Checkout(
				@field:SerializedName("nama") var nama: String?,
				@field:SerializedName("umur") var umur: String?,
				@field:SerializedName("gender") var gender: String?,
				@field:SerializedName("hobi") var hobi: String?,
				@field:SerializedName("riwayat") var riwayat: String?,
				@field:SerializedName("paket") var paket: String?,
				@field:SerializedName("durasi") var durasi: String?,
				@field:SerializedName("aktivitas") var aktivitas: String?,
				@field:SerializedName("alamat") var alamat: String?,
				@field:SerializedName("telepon") var telepon: String?,
				@field:SerializedName("user_id") var user_id: String?,
				@field:SerializedName("cg_id") var cg_id: String?,
				@field:SerializedName("lansia_id") var lansia_id: String?,
				@field:SerializedName("lansia_uses") var lansiauses: String?

)
