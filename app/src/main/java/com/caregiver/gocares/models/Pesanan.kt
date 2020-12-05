package com.caregiver.gocares.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Pesanan
(
				@field:SerializedName("id") val id : String?,
				@field:SerializedName("esccort_id") val esccort_id : String?,
				@field:SerializedName("esccort_name") val esccort_name : String?,
				@field:SerializedName("esccort_gender") val esccort_gender : String?,
				@field:SerializedName("photo") val photo : String?,
				@field:SerializedName("address") val address : String?,
				@field:SerializedName("rating") val rating : String?,
				@field:SerializedName("keahlian") val keahlian : String?,
				@field:SerializedName("lansia_name") val lansia_name : String?,
				@field:SerializedName("lansia_gender") val lansia_gender : String?,
				@field:SerializedName("umur") val umur : String?,
				@field:SerializedName("status") val status : String?,
				@field:SerializedName("order_time") val order_time : String?,
				@field:SerializedName("durasi") val durasi : String?,
				@field:SerializedName("alamat") val alamat : String?,
				@field:SerializedName("nomor_telp") val nomor_telp : String?,
				@field:SerializedName("deskripsi_kerja") val deskripsi_kerja : String?,
				@field:SerializedName("hobi") val hobi : String?,
				@field:SerializedName("riwayat") val riwayat : String?,
				@field:SerializedName("total_bayar") val total_bayar : String?
) : Parcelable {
	constructor(parcel: Parcel) : this(
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString(),
					parcel.readString())

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
		parcel.writeString(esccort_id)
		parcel.writeString(esccort_name)
		parcel.writeString(esccort_gender)
		parcel.writeString(photo)
		parcel.writeString(address)
		parcel.writeString(rating)
		parcel.writeString(keahlian)
		parcel.writeString(lansia_name)
		parcel.writeString(lansia_gender)
		parcel.writeString(umur)
		parcel.writeString(status)
		parcel.writeString(order_time)
		parcel.writeString(durasi)
		parcel.writeString(alamat)
		parcel.writeString(nomor_telp)
		parcel.writeString(deskripsi_kerja)
		parcel.writeString(hobi)
		parcel.writeString(riwayat)
		parcel.writeString(total_bayar)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Pesanan> {
		override fun createFromParcel(parcel: Parcel): Pesanan {
			return Pesanan(parcel)
		}

		override fun newArray(size: Int): Array<Pesanan?> {
			return arrayOfNulls(size)
		}
	}
}