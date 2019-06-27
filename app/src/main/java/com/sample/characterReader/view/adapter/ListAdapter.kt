package com.sample.characterReader.view.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.characterReader.R
import com.sample.characterReader.model.CommonModel
import kotlinx.android.synthetic.main.adapter_list.view.*


/**
 * Created by Nilesh Patel on 25/06/19.
 */

class ListAdapter(
    var mItems: ArrayList<CommonModel.RelatedTopics>,
    val mContext: Context,
    val mInterface: ListAdapterInterface
) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    interface ListAdapterInterface {
        fun onItemClicked(data: CommonModel.RelatedTopics)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_list, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = mItems[position]
        holder.txtTitle.text = Html.fromHtml(data.Result)
        holder.itemView.tag = data
        holder.itemView.setOnClickListener {
            val tag = it.tag
            mInterface.onItemClicked(tag as CommonModel.RelatedTopics)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle = view.txtTitle
    }

    //Update list on search enter
    fun filterList(filteredDataList: ArrayList<CommonModel.RelatedTopics>) {
        mItems = filteredDataList;
        notifyDataSetChanged();
    }
}