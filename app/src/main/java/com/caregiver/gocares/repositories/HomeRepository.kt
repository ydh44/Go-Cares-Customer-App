package com.caregiver.gocares.repositories

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.caregiver.gocares.models.CgResponse
import com.caregiver.gocares.models.PesananResponse
import com.caregiver.gocares.utils.SessionLog
import com.caregiver.gocares.webservices.ApiServices
import com.caregiver.gocares.webservices.RetrofitServices.Companion.getRetrofitServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeRepository(context: Context?) {
	private var apiServices: ApiServices? = null
	var context: Context? = null
	var pesanans: PesananResponse? = null
	var cgs: CgResponse? = null
	public var getted: MutableLiveData<Boolean>? = null
	lateinit var obs1 : Observable<CgResponse>

	init {
		apiServices = getRetrofitServices().create(ApiServices::class.java)
		getted = MutableLiveData()
		this.context = context
		get()

	}

	@SuppressLint("CheckResult")
	private fun get() {

		val getcg : Observable<CgResponse>? = apiServices?.getCg()
						?.map{CgResponse(it.data, it.dalamProses)}
						?.subscribeOn(Schedulers.io())
						?.onErrorReturn { CgResponse(null, null) }
		val getPesanan : Observable<PesananResponse>? = apiServices?.getPesanan(SessionLog.GetId(context))
						?.map { PesananResponse(it.pesan) }
						?.subscribeOn(Schedulers.io())
						?.onErrorReturn { PesananResponse(null) }
		Observable.zip(
						getcg?.subscribeOn(Schedulers.io()),
						getPesanan?.subscribeOn(Schedulers.io()),
						{
							t1, t2 ->
							cgs = t1
							pesanans = t2
						})
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe{
							getted?.postValue(true)
						}
	}

}