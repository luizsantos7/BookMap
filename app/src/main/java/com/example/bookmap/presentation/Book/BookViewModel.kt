package com.example.bookmap.presentation.Book

import androidx.lifecycle.ViewModel
import com.example.bookmap.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel(){

}