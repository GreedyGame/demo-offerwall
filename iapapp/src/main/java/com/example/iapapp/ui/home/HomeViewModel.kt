package com.example.iapapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iapapp.R
import com.example.iapapp.data.BookModel
import com.example.iapapp.data.GenreModel
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val defaultGenres = listOf(
        GenreModel("All", true),
        GenreModel("Detective"),
        GenreModel("Drama"),
        GenreModel("Historical")
    )

    val defaultBooks = listOf(
        BookModel(
            "Moby Dick",
            "Herman Meville",
            R.drawable.ic_book_1,
            4.5,
            "English",
            1,
            R.raw.mobydick_audio
        ),
        BookModel(
            "Sowing Seeds in Danny",
            "Nellie McClung",
            R.drawable.ic_book_2,
            4.8,
            "English",
            1,
            R.raw.sowingseedsindanny_audio
        ),
        BookModel(
            "The Feasting Dead",
            "John Metcalfe",
            R.drawable.ic_book_3,
            4.6,
            "English",
            1,
            R.raw.thefeastingdead_audio
        ),
        BookModel(
            "The Life of St. Paul",
            "James Stalker",
            R.drawable.ic_book_4,
            4.9,
            "English",
            1,
            R.raw.thelifeofstpaul_audio
        )
    )

    private val _genres = MutableLiveData<List<GenreModel>>()
    val genres: LiveData<List<GenreModel>>
        get() = _genres

    fun updateGenreSelection(genreModel: GenreModel) {
        viewModelScope.launch {
            val list = mutableListOf<GenreModel>()
            defaultGenres.forEach {
                list.add(it.copy(isSelected = it.genreName == genreModel.genreName))
            }
            _genres.postValue(list.toList())
        }
    }
}