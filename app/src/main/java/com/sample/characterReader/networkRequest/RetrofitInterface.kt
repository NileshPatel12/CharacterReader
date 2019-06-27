package com.sample.characterReader.networkRequest

import com.sample.characterReader.BuildConfig
import com.sample.characterReader.model.CommonModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Nilesh Patel on 25/06/19.
 */

interface RetrofitInterface {

    //End URL will change based on Build selection
    @GET(BuildConfig.PARAM)
    fun getData(): Observable<Response<CommonModel>>
}

