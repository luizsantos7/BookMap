package com.example.bookmap.presentation.login

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.repository.UserRepository
import com.example.bookmap.presentation.login.LoginScreenAction.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState

    fun onActionEvent(action: LoginScreenAction) {
        when (action) {
            is EmailChanged -> onEmailChange(action.newEmail)
            is PasswordChanged -> onPasswordChange(action.newPassword)
            is SubmitLogin -> onSubmitLogin(action.email, action.password)
            is newRegister -> callRegisterDialog()
            is RegisterNameChanged -> onNameRegisterChange(action.name)
            is RegisterEmailChanged -> onEmailRegisterChange(action.email)
            is RegisterConfirmEmailChanged -> onConfirmEmailRegisterChange(action.confirmEmail)
            is RegisterPasswordChanged -> onPasswordRegisterChange(action.password)
            is RegisterBirthdayChanged -> onBirthdayRegisterChange(action.birthday)
            is RegisterGenderChanged -> onGenderRegisterChange(action.gender)
            is SubmitRegister -> onSubmitRegister(action.userEntity)
            //is CancelRegister -> onCancelRegister()
        }
    }

    fun onSubmitLogin(email: String, password: String) {
        viewModelScope.launch {
            val userFound = userRepository.loginUser(email = email, password = password)
            if (userFound) {
                _uiState.value = LoginUiState(isLoading = false, showError = false)
            } else {
                _uiState.value = LoginUiState(isLoading = false, showError = true)
            }
        }
    }

    // LOGIN FUNCTIONS
    fun enableLoginButton(): Boolean {
        val password = _uiState.value.password
        return password.isNotEmpty() && password.isNotEmpty() && password.length >= 6
    }

    fun onDismissError() {
        _uiState.update { it.copy(showError = false) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    // DIALOG FUNCTIONS
    fun callRegisterDialog() {
        _uiState.update { it.copy(showRegisterDialog = true) }
    }

    fun dismissRegisterDialog() {
        _uiState.update { it.copy(showRegisterDialog = false) }
    }

    //REGISTER FUNCTIONS
    fun onSubmitRegister(userEntity: UserEntity) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val success = userRepository.createUser(userEntity)

            if (success) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        showError = false,
                        showRegisterDialog = false,
                        userRegister = userEntity
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        showError = true
                    )
                }
            }
        }
    }

    fun registerButtonEnabled(): Boolean {
        val user = _uiState.value.userRegister

        return user.name.isNotBlank() &&
                user.email.isNotBlank() &&
                user.confirmEmail.isNotBlank() &&
                user.password.isNotBlank() &&
                user.birthday.isNotBlank() &&
                user.gender.isNotBlank() &&
                user.confirmEmail == user.email &&
                user.password.length >= 6
    }

    fun onNameRegisterChange(name: String) {
        _uiState.update { it.copy(userRegister = it.userRegister.copy(name = name)) }
    }

    fun onEmailRegisterChange(email: String) {
        _uiState.update { it.copy(userRegister = it.userRegister.copy(email = email)) }
    }

    fun onConfirmEmailRegisterChange(confirmEmail: String) {
        _uiState.update { it.copy(userRegister = it.userRegister.copy(confirmEmail = confirmEmail)) }
    }

    fun onPasswordRegisterChange(password: String) {
        _uiState.update { it.copy(userRegister = it.userRegister.copy(password = password)) }
    }

    fun onBirthdayRegisterChange(input: String) {
        val digits = input.filter { it.isDigit() }

        val limited = digits.take(8)

        val formatted = when {
            limited.length >= 5 -> "${limited.substring(0, 2)}/${limited.substring(2, 4)}/${limited.substring(4)}"
            limited.length >= 3 -> "${limited.substring(0, 2)}/${limited.substring(2)}"
            limited.isNotEmpty() -> limited
            else -> ""
        }

        _uiState.update { it.copy(userRegister = it.userRegister.copy(birthday = formatted)) }
    }

    fun onGenderRegisterChange(gender: String) {
        _uiState.update { it.copy(userRegister = it.userRegister.copy(gender = gender)) }
    }
}