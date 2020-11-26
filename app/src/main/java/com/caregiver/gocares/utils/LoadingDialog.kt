package com.caregiver.gocares.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.caregiver.gocares.R
import java.util.logging.Handler

class LoadingDialog(mActivity: Activity) {
	private var activity:Activity? = mActivity
	private lateinit var dialog:AlertDialog
	private var loading : Boolean = false

	fun startLoading(){
		val inflater = LayoutInflater.from(activity).inflate(R.layout.loading_dialog, null)
		val builder = AlertDialog.Builder(activity)
						.setView(inflater)
						.setCancelable(false)
		dialog = builder.create()
		dialog.show()
		autoDismiss()
		loading = true
	}

	fun dismissLoading(){
		if(getLoading()){
			dialog.dismiss()
		}
		loading = false
	}

	fun getLoading() : Boolean{
		return loading
	}

	fun autoDismiss(){
		val progressRunnable = Runnable {
			dialog.dismiss()
			loading = false;
		}
		val pdCanceller = android.os.Handler()
		pdCanceller.postDelayed(progressRunnable, 10000)
	}
}