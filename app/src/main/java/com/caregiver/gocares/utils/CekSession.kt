package com.caregiver.gocares.utils

import android.content.Context

public class CekSession {
	var bool : Boolean = false

	fun getSession(context: Context?) : Boolean {
		if (SessionLog.GetStatus(context) && SessionLog.GetToken(context) != null){
			bool = true
		}else{
			bool = false
		}
		return bool
	}
}