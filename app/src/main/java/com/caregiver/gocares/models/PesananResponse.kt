package com.caregiver.gocares.models

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

data class PesananResponse(

				@field:SerializedName("success") val pesan: List<Pesan>?
)

data class Pesan (

				@field:SerializedName("esccort_name") val esccort_name : String,
				@field:SerializedName("esccort_gender") val esccort_gender : String,
				@field:SerializedName("lansia_gender") val lansia_gender : String,
				@field:SerializedName("transaksi_id") val transaksi_id : Int,
				@field:SerializedName("lansia_name") val lansia_name : String,
				@field:SerializedName("esccort_id") val esccort_id : Int,
				@field:SerializedName("userid-dupe") val useriddupe : String,
				@field:SerializedName("lansia_id") val lansia_id : Int,
				@field:SerializedName("user_name") val user_name : String,
				@field:SerializedName("id") val id : Int,
				@field:SerializedName("order_time") val order_time : String,
				@field:SerializedName("complate_time") val complate_time : String,
				@field:SerializedName("paket") val paket : String,
				@field:SerializedName("durasi") val durasi : Int,
				@field:SerializedName("alamat") val alamat : String,
				@field:SerializedName("nomor_telp") val nomor_telp : String,
				@field:SerializedName("deskripsi_kerja") val deskripsi_kerja : String,
				@field:SerializedName("total_bayar") val total_bayar : Int,
				@field:SerializedName("status") val status : String,
				@field:SerializedName("payment") val payment : String,
				@field:SerializedName("bukti_foto") val bukti_foto : String,
				@field:SerializedName("user_id") val user_id : String,
				@field:SerializedName("name") val name : String,
				@field:SerializedName("salary") val salary : Int,
				@field:SerializedName("keahlian") val keahlian : String,
				@field:SerializedName("age") val age : Int,
				@field:SerializedName("address") val address : String,
				@field:SerializedName("gender") val gender : String,
				@field:SerializedName("phone") val phone : String,
				@field:SerializedName("photo") val photo : String,
				@field:SerializedName("rating") val rating : String,
				@field:SerializedName("deleted_at") val deleted_at : String,
				@field:SerializedName("nama") val nama : String,
				@field:SerializedName("umur") val umur : String,
				@field:SerializedName("hobi") val hobi : String,
				@field:SerializedName("riwayat") val riwayat : String
)