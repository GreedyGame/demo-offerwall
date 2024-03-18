package com.example.iapapp.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(
    val bookName: String,
    val bookAuthor: String,
    @DrawableRes val bookCoverImage: Int,
    val rating: Double,
    val language: String,
    val durationInMin: Int
) : Parcelable