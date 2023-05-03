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

        // 데이터베이스 인스턴스 생성
        reviewDB = ReviewDB.getInstance(this)!!
        // reviewDao, Adapter 초기화
        reviewDao = reviewDB.reviewDao()!!
        reviewAdapter = ReviewAdapter(emptyList())

        recyclerView.adapter = reviewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 버튼 클릭 이벤트 연결
        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // 코루틴으로 데이터베이스 데이터 가져오기
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

    // 어댑터 연결
    class ReviewAdapter(private var reviewList: List<Review>) :
        RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

        // 뷰홀더에 아이템 연결
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
            return ReviewViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            val currentReview = reviewList[position]
            holder.bind(currentReview)
        }

        // 아이템 수만큼 리사이클러뷰 보이기
        override fun getItemCount() = reviewList.size

        fun updateReviewList(newReviewList: List<Review>) {
            reviewList = newReviewList
            notifyDataSetChanged()
        }

        // 리사이클러뷰 아이템 연결
        class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val reviewTitleTextView: TextView = itemView.findViewById(R.id.itemTitle)
            private val reviewWriterTextView: TextView =
                itemView.findViewById(R.id.itemWriter)
            private val reviewLengthTextView: TextView = itemView.findViewById(R.id.itemLength)

            fun bind(review: Review) {
                reviewTitleTextView.text = review.title
                reviewWriterTextView.text = review.writer
                reviewLengthTextView.text = review.length.toString()
            }
        }
    }

}