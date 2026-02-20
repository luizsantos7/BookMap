package com.example.bookmap.presentation.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

     private val _uiState = MutableStateFlow(DetailUiState())
     val uiState = _uiState

     fun onActionEvent(action: DetailScreenAction) {
         when (action) {
             else -> {}
         }
     }
}