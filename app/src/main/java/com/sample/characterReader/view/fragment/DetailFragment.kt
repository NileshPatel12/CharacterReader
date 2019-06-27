package com.sample.characterReader.view.fragment

import android.os.Bundle
import android.text.Html.fromHtml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.sample.characterReader.R
import com.sample.characterReader.model.CommonModel
import com.sample.characterReader.utils.NetworkChecker
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Created by Nilesh Patel on 25/06/19.
 */

class DetailFragment : Fragment() {

    lateinit var txtTitle: TextView
    lateinit var txtText: TextView
    lateinit var imageView: ImageView

    companion object {
        fun create(data: CommonModel.RelatedTopics): DetailFragment {
            val gson = Gson()
            val bundle = Bundle()
            bundle.putString("data", gson.toJson(data))

            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle
            return detailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.mImageView);
        txtTitle = view.findViewById(R.id.txtTitle)
        txtText = view.findViewById(R.id.txtText)

        if (arguments != null) {
            val gson = Gson()
            val data = gson.fromJson(arguments!!.getString("data"), CommonModel.RelatedTopics::class.java)
            loadUI(data)
        }else
        lytWarning.visibility = View.VISIBLE
    }


    fun loadUI(data: CommonModel.RelatedTopics) {
        txtTitle.text = fromHtml(data.Result)
        txtText.text = fromHtml(data.Text)
        lytWarning.visibility = View.GONE

        //if URL Empty OR Internet Not Available to load Image, Use default image
        if (data.icon.URL.isNotEmpty() && NetworkChecker.checkInternet(activity!!.applicationContext)) {
            //cache image to avoid repeating Network calls
            Picasso.get().load(data.icon.URL).priority(Picasso.Priority.HIGH).into(imageView)
        } else {
            //Skip memory cache to cover internet off and on scenario, Default image no need to cache
            Picasso.get().load(R.mipmap.no_image_icn).priority(Picasso.Priority.HIGH).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageView)
        }
    }
}

