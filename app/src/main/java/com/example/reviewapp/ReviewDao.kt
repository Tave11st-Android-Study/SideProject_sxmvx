package com.example.reviewapp

import androidx.room.*

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review ORDER BY id DESC")
    fun getAll(): List<Review>

    @Query("SELECT * FROM review WHERE id IN (:reviewIds)")
    fun loadAllByIds(reviewIds: IntArray): List<Review>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg reviews: Review)

    @Delete
    fun delete(review: Review)
}