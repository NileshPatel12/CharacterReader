package com.sample.characterReader.view_presenter

import com.sample.characterReader.model.CommonModel
import retrofit2.Response

/**
 * Created by Nilesh Patel on 25/06/19.
 */

interface ViewPresenter {

    interface MainView {
        fun showAlert()
        fun showProgressbar()
        fun hideProgressbar()
        fun onSuccess(response: Response<CommonModel>)
        fun onError(throwable: Throwable)
        fun checkInternet(): Boolean
    }

    interface MainPresenter {
        fun loadData()
        fun onStop()
    }
}