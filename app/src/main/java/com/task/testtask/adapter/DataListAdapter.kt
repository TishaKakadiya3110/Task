package com.task.testtask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.testtask.R
import com.task.testtask.model.DataListModel

class DataListAdapter(private val onItemClick: (DataListModel) -> Unit) :
    RecyclerView.Adapter<DataListAdapter.PostViewHolder>() {

    private var dataList = emptyList<DataListModel>()

    private var parentArrayList = emptyList<ArrayList<Int>>()


    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(post: DataListModel) {

            itemView.findViewById<TextView>(R.id.TvIdTitle).text =
                post.id.toString() + ". " + post.title
            itemView.findViewById<TextView>(R.id.TvDescription).text = post.body

            itemView.setOnClickListener { onItemClick(post) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_list_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    //    override fun getItemCount(): Int = dataList.size
    override fun getItemCount(): Int = parentArrayList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newPosts: List<DataListModel>, parentArrayList: MutableList<ArrayList<Int>>) {
        this.dataList = newPosts
        this.parentArrayList = parentArrayList
        notifyDataSetChanged()
    }
}