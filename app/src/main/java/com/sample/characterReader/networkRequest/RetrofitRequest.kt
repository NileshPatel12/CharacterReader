package com.sample.characterReader.networkRequest

import com.sample.characterReader.model.CommonModel
import com.sample.characterReader.utils.Constants
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Nilesh Patel on 25/06/19.
 */

class RetrofitRequest {

    private val retrofitInterface: RetrofitInterface

    init {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
    }

    companion object {
        private var retrofitRequest: RetrofitRequest? = null
        val instance: RetrofitRequest
            get() {
                if (retrofitRequest == null) {
                    retrofitRequest = RetrofitRequest()
                }
                return retrofitRequest as RetrofitRequest
            }

    }

    fun getList(): Observable<Response<CommonModel>> {
        return retrofitInterface.getData()
    }

}




