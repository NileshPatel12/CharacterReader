package com.sample.characterReader.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


import com.sample.characterReader.utils.NetworkChecker
import com.sample.characterReader.implentation.Implementation
import com.sample.characterReader.model.CommonModel
import com.sample.characterReader.view.fragment.ListFragment
import com.sample.characterReader.view_presenter.ViewPresenter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import android.content.Intent
import android.widget.FrameLayout
import android.widget.Toast
import com.google.gson.Gson
import com.sample.characterReader.R
import com.sample.characterReader.view.fragment.DetailFragment
import kotlinx.android.synthetic.main.activity_main.lyt_frgmentManager as lyt_frgmentManager1


/**
 * Created by Nilesh Patel on 25/06/19.
 */

class MainActivity : AppCompatActivity(), ViewPresenter.MainView, ListFragment.ListFragmentInterface {


    var implementation: Implementation? = null
    var arrayList: ArrayList<CommonModel>? = ArrayList()
    lateinit var mListFragment: ListFragment
    var mDetailFragment: DetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializations()
    }


    private fun initializations() {
        mListFragment = ListFragment(this)
        val mTransactiont = supportFragmentManager.beginTransaction()
        mTransactiont.replace(
            R.id.fragmentList,
            mListFragment,
            mListFragment.javaClass.name
        );
        mTransactiont.commit();

        //tablet Mode
        if (findViewById<FrameLayout>(R.id.fragmentDetails) != null) {
            mDetailFragment = DetailFragment()
            val vTransaction = supportFragmentManager.beginTransaction()
            vTransaction.replace(
                R.id.fragmentDetails,
                mDetailFragment!!,
                mDetailFragment!!.javaClass.name
            );
            vTransaction.commit();
        } else {
            mDetailFragment = null
        }

        implementation = Implementation(this)
        implementation!!.loadData()

        //Try again If Internet Not available
        btnTry.setOnClickListener {
            implementation!!.loadData()
        }
    }

    override fun onItemClicked(data: CommonModel.RelatedTopics) {
        if (mDetailFragment != null) {
            mDetailFragment!!.loadUI(data)
        } else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data", Gson().toJson(data))
            startActivity(intent)
        }
    }

    override fun showAlert() {
        lyt_NoInternet.visibility = View.VISIBLE
        fragmentList.visibility = View.GONE
        lyt_frgmentManager1.visibility = View.GONE
    }

    override fun showProgressbar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progressbar.visibility = View.GONE
    }

    override fun onSuccess(response: Response<CommonModel>) {
        if (response.body() != null) {
            fragmentList.visibility = View.VISIBLE
            lyt_frgmentManager1.visibility = View.VISIBLE
            lyt_NoInternet.visibility = View.GONE
            mListFragment.loadData(response.body()!!.relatedTopics)
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this@MainActivity, R.string.str_error_msg, Toast.LENGTH_SHORT).show()
    }

    override fun checkInternet(): Boolean {
        return NetworkChecker.checkInternet(this)
    }


}
