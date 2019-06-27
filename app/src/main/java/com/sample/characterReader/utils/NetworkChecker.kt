package com.sample.characterReader.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Nilesh Patel on 25/06/19.
 */

class NetworkChecker {

    //Utility to check internet availability
    companion object {
        fun checkInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}