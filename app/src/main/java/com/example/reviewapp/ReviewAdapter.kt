package com.example.reviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ReviewAdapter(private var reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = reviewList[position]
        holder.title.text = item.title
        holder.writer.text = item.writer
        holder.length.text = java.lang.Long.toString(item.length)
        holder.review.text = item.review
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun updateReviewList(reviewList: List<Review>?) {
        reviewList?.let {
            this.reviewList = mutableListOf()
            (this.reviewList as MutableList<Review>).addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.itemTitle)
        var writer: TextView = itemView.findViewById(R.id.itemWriter)
        var length: TextView = itemView.findViewById(R.id.itemLength)
        var review: TextView = itemView.findViewById(R.id.itemReview)
    }
}