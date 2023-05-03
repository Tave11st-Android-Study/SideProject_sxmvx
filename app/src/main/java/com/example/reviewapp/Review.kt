package com.example.reviewapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// DB
@Entity
class Review {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @JvmField
    @ColumnInfo(name = "title")
    var title: String? = null

    @JvmField
    @ColumnInfo(name = "writer")
    var writer: String? = null

    @JvmField
    @ColumnInfo(name = "length")
    var length: Long = 0

    @JvmField
    @ColumnInfo(name = "review")
    var review: String? = null
}