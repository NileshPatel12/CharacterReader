package com.sample.characterReader.implentation

import androidx.annotation.NonNull
import com.sample.characterReader.networkRequest.RetrofitRequest
import com.sample.characterReader.view_presenter.ViewPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


/**
 * Created by Nilesh Patel on 25/06/19.
 */

class Implementation(var mainView: ViewPresenter.MainView?) : ViewPresenter.MainPresenter {

    @NonNull
    var disposable: Disposable? = null

    override fun loadData() {
        mainView!!.showProgressbar()

        if (mainView!!.checkInternet()) {
            disposable = RetrofitRequest.instance
                .getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    mainView!!.hideProgressbar()
                    val responseCode = listResponse.code()

                    if (responseCode.equals(200)) {
                        mainView!!.onSuccess(listResponse)
                    }
                }, { error ->
                    mainView!!.hideProgressbar()
                    if (error is HttpException) {
                       //Handle Error
                    }
                    mainView!!.onError(error)

                })
        } else {
            mainView!!.hideProgressbar()
            mainView!!.showAlert()
        }
    }

    override fun onStop() {
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}