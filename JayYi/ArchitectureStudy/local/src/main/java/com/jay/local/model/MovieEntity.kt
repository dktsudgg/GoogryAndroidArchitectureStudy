package com.jay.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val director: String,
    val actor: String,
    @ColumnInfo(name = "pub_date")
    val pubDate: String,
    @ColumnInfo(name = "user_rating")
    val userRating: Float
)

data class MovieLocalData(
    val movies: List<MovieEntity>
)