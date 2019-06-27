package com.sample.characterReader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.sample.characterReader.R
import com.sample.characterReader.model.CommonModel
import com.sample.characterReader.view.fragment.DetailFragment

/**
 * Created by Nilesh Patel on 25/06/19.
 */

class DetailActivity : AppCompatActivity() {

    lateinit var mDetailFragment : DetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        initialization()

    }

    private fun initialization(){
        val data = Gson().fromJson(intent!!.getStringExtra("data"), CommonModel.RelatedTopics::class.java)

        mDetailFragment = DetailFragment.create(data)
        val vTransaction = supportFragmentManager.beginTransaction()
        vTransaction.replace(R.id.fragmentDetails, mDetailFragment, mDetailFragment.javaClass.name)
        vTransaction.commit();
    }
}