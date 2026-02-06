package com.example.bookmap.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.repository.UserRepository
import com.example.bookmap.presentation.login.LoginScreenAction.EmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.PasswordChanged
import com.example.bookmap.presentation.login.LoginScreenAction.SubmitLogin
import com.example.bookmap.presentation.login.LoginScreenAction.newRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState

    fun onActionEvent(action: LoginScreenAction) {
        when (action) {
            is EmailChanged -> onEmailChange(action.newEmail)
            is PasswordChanged -> onPasswordChange(action.newPassword)
            is SubmitLogin -> onSubmitLogin(action.email, action.password)
            is newRegister -> callRegisterDialog()
        }
    }

    fun onSubmitLogin(email: String, password: String){
        viewModelScope.launch {
            val userFound = userRepository.loginUser(email = email, password = password)
            if (userFound) {
                _uiState.value = LoginUiState(isLoading = false, showError = false)
            } else {
                _uiState.value = LoginUiState(isLoading = false, showError = true)
            }
        }
    }

    fun callRegisterDialog(){
        _uiState.value = uiState.value.copy(showRegisterDialog = true)
    }

    fun dismissRegisterDialog(){
        _uiState.value = uiState.value.copy(showRegisterDialog = false)
    }

    fun enableLoginButton(): Boolean {
        val password = uiState.value.password
        return password.isNotEmpty() && password.isNotEmpty() && password.length>=6
    }

    fun onDismissError() {
        _uiState.value = uiState.value.copy(showError = false)
    }

    fun onEmailChange(newEmail: String) {
        uiState.value = uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        uiState.value = uiState.value.copy(password = newPassword)
    }


}