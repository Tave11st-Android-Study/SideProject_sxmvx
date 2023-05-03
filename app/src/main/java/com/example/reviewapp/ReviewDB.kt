package com.example.reviewapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Review::class], version = 2)
abstract class ReviewDB : RoomDatabase() {
    abstract fun reviewDao(): ReviewDao?

    // 싱글톤 디자인 패턴으로 인스턴스 생성
    companion object {
        private var INSTANCE: ReviewDB? = null
        fun getInstance(context: Context): ReviewDB? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDB::class.java, "review.db"
                ).build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}