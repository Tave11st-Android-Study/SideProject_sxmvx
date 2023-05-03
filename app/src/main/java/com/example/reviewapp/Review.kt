package com.example.reviewapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Review {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @JvmField
    @ColumnInfo(name = "title")
    var title: String? = null

    @JvmField
    @ColumnInfo(name = "Singer")
    var writer: String? = null

    @JvmField
    @ColumnInfo(name = "play_time")
    var length: Long = 0

    @JvmField
    @ColumnInfo(name = "lyrics")
    var review: String? = null
}