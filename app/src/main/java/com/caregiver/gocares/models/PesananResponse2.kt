package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

data class PesananResponse2(

	@field:SerializedName("success")
	val pesan2: Pesan2? = null
)

data class Pesan2(

	@field:SerializedName("nomor_telp")
	val nomorTelp: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("deskripsi_kerja")
	val deskripsiKerja: String? = null,

	@field:SerializedName("lansia_id")
	val lansiaId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("durasi")
	val durasi: String? = null,

	@field:SerializedName("esccort_id")
	val esccortId: String? = null,

	@field:SerializedName("total_bayar")
	val totalBayar: Int? = null,

	@field:SerializedName("paket")
	val paket: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
