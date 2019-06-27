package com.sample.characterReader.view.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.characterReader.model.CommonModel
import com.sample.characterReader.view.adapter.ListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.sample.characterReader.R
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * Created by Nilesh Patel on 25/06/19.
 */

class ListFragment(val mInterface: ListFragmentInterface) : Fragment(), ListAdapter.ListAdapterInterface {


    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mListAdapter: ListAdapter
    var mList: ArrayList<CommonModel.RelatedTopics> = ArrayList()

    interface ListFragmentInterface {
        fun onItemClicked(data: CommonModel.RelatedTopics)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(com.sample.characterReader.R.id.recyclerView)

        mLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

        mListAdapter = ListAdapter(mList, context!!, this)
        recyclerView.adapter = mListAdapter

        search_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        search_edit.setOnFocusChangeListener { v,hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v);
            }
            return@setOnFocusChangeListener
        }
    }

    fun loadData(data: List<CommonModel.RelatedTopics>) {
        mList.clear()
        mList.addAll(data)
        mListAdapter.notifyDataSetChanged()
    }

    override fun onItemClicked(data: CommonModel.RelatedTopics) {
        mInterface.onItemClicked(data)
    }


    fun filter(text: String) {
        val filteredCourseAry: ArrayList<CommonModel.RelatedTopics> = ArrayList()
        val listArray : ArrayList<CommonModel.RelatedTopics> = mList
        for (eachDetail in listArray) {
            if (eachDetail.Result.toLowerCase().contains(text.toLowerCase()) || eachDetail.Text.toLowerCase().contains(text.toLowerCase())) {
                filteredCourseAry.add(eachDetail)
            }
        }
        mListAdapter.filterList(filteredCourseAry);
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}