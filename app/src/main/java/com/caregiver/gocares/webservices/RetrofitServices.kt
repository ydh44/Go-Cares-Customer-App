package com.caregiver.gocares.webservices

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitServices {

	companion object {
		private const val BaseUrl = "http://40.88.4.113/api/"
		private var mClient: OkHttpClient? = null

		/**
		 * Don't forget to remove Interceptors (or change Logging Level to NONE)
		 * in production! Otherwise people will be able to see your request and response on Log Cat.
		 */

		private val client: OkHttpClient
			get() {
				if (mClient == null) {
					val interceptor = HttpLoggingInterceptor()
					interceptor.level = HttpLoggingInterceptor.Level.BODY

					val httpBuilder = OkHttpClient.Builder()
					httpBuilder
									.connectTimeout(15, TimeUnit.SECONDS)
									.readTimeout(30, TimeUnit.SECONDS)
									.addInterceptor(interceptor)  /// show all JSON in logCat
					mClient = httpBuilder.build()

				}
				return mClient!!
			}

		fun getRetrofitServices(): Retrofit = Retrofit.Builder()
						.baseUrl(BaseUrl)
						.addConverterFactory(GsonConverterFactory.create())
						.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
						.client(client)
						.build()
	}
}