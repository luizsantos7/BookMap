package com.example.bookmap.presentation.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(){
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState = _uiState

    private val _presentation = MutableStateFlow(LoginUiState.Presentation())
    val loginPresentation = _presentation


    fun onActionEvent(action: LoginScreenAction) {
        action.fold(
            onEmailChanged =:: onEmailChange,
            onPasswordChanged = :: onPasswordChange,
            //onSubmitLogin = :: onSubmitLogin
        )
    }

    fun enableLoginButton(): Boolean {
        val currentPresentation = _presentation.value
        return currentPresentation.email.isNotEmpty() && currentPresentation.password.isNotEmpty()
    }

    fun onEmailChange(newEmail: String) {
        _presentation.value = _presentation.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _presentation.value = _presentation.value.copy(password = newPassword)
    }


}