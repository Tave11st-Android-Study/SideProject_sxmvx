package com.example.reviewapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*


class AddActivity : AppCompatActivity() {
    private lateinit var reviewDB: ReviewDB
    private lateinit var rContext: Context
    private lateinit var rEditTextTitle: EditText
    private lateinit var rEditTextWriter: EditText
    private lateinit var rEditTextLength: EditText
    private lateinit var rEditTextReview: EditText
    private lateinit var rAddButton: Button
    private lateinit var job: Job

    // 코루틴으로 데이터베이스에서 데이터 가져오기
    private inner class InsertCoroutine : CoroutineScope by MainScope() {
        fun insertMusic() {
            launch {
                val review = Review()
                review.title = rEditTextTitle.text.toString()
                review.writer = rEditTextWriter.text.toString()
                review.length = rEditTextLength.text.toString().toLong()
                review.review = rEditTextReview.text.toString()
                withContext(Dispatchers.IO) {
                    reviewDB.reviewDao()?.insertAll(review)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        rAddButton = findViewById(R.id.rAddItemButton)
        rEditTextTitle = findViewById(R.id.rEditTextTitle)
        rEditTextWriter = findViewById(R.id.rEditTextWriter)
        rEditTextLength = findViewById(R.id.rEditTextLength)
        rEditTextReview = findViewById(R.id.rEditTextReview)
        reviewDB = ReviewDB.getInstance(this)!!
        rContext = applicationContext

        // 버튼 클릭 이벤트 구현
        rAddButton.setOnClickListener {
            val insertCoroutine = InsertCoroutine()
            insertCoroutine.insertMusic()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
        }
        ReviewDB.destroyInstance()
    }
}