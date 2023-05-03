package com.example.reviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var reviewDB: ReviewDB
    private lateinit var reviewDao: ReviewDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton: Button = findViewById(R.id.rAddButton)
        val recyclerView: RecyclerView = findViewById(R.id.rRecyclerView)

        reviewDB = ReviewDB.getInstance(this)!!
        reviewDao = reviewDB.reviewDao()!!
        reviewAdapter = ReviewAdapter(emptyList())

        recyclerView.adapter = reviewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            val reviewList = reviewDao.getAll()
            withContext(Dispatchers.Main) {
                reviewAdapter?.updateReviewList(reviewList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ReviewDB.destroyInstance()
    }

    class ReviewAdapter(private var reviewList: List<Review>) :
        RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
            return ReviewViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            val currentReview = reviewList[position]
            holder.bind(currentReview)
        }

        override fun getItemCount() = reviewList.size

        fun updateReviewList(newReviewList: List<Review>) {
            reviewList = newReviewList
            notifyDataSetChanged()
        }

        class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val reviewTitleTextView: TextView = itemView.findViewById(R.id.itemTitle)
            private val reviewArtistTextView: TextView =
                itemView.findViewById(R.id.itemWriter)

            fun bind(review: Review) {
                reviewTitleTextView.text = review.title
                reviewArtistTextView.text = review.writer
            }
        }
    }

}