package com.caregiver.gocares

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater

class LoadingDialog(mActivity:Activity) {
	private var activity:Activity? = mActivity
	private lateinit var dialog:AlertDialog

	fun startLoading(){
		val inflater = LayoutInflater.from(activity).inflate(R.layout.loading_dialog, null)
		val builder = AlertDialog.Builder(activity)
						.setView(inflater)
						.setCancelable(false)
		dialog = builder.create()
		dialog.show()
	}

	fun dismissLoading(){
		dialog.dismiss()
	}
}