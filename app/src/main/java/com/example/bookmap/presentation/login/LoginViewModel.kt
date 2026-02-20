package com.example.bookmap.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.Profile
import com.example.bookmap.data.models.UserRegisterDataModel
import com.example.bookmap.data.repository.UserRepository
import com.example.bookmap.presentation.login.LoginScreenAction.EmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.PasswordChanged
import com.example.bookmap.presentation.login.LoginScreenAction.RegisterBirthdayChanged
import com.example.bookmap.presentation.login.LoginScreenAction.RegisterConfirmEmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.RegisterEmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.RegisterGenderChanged
import com.example.bookmap.presentation.login.LoginScreenAction.RegisterNameChanged
import com.example.bookmap.presentation.login.LoginScreenAction.RegisterPasswordChanged
import com.example.bookmap.presentation.login.LoginScreenAction.SubmitLogin
import com.example.bookmap.presentation.login.LoginScreenAction.SubmitRegister
import com.example.bookmap.presentation.login.LoginScreenAction.newRegister
import com.example.bookmap.utils.constants.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

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
            is SubmitRegister -> onSubmitRegister()
        }
    }

    //Login functions
    fun onSubmitLogin(email: String, password: String) {
        userRepository.loginUser(
            email,
            password,
            onSuccess = {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.ToHomeScreen)
                }
            },
            onFailure = {
                _uiState.value = LoginUiState(showError = true)
            }
        )
    }

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
    private fun validateEmails(email: String, confirmEmail: String): Boolean {
        return when {
            email.isBlank() || confirmEmail.isBlank() -> {
                _uiState.update {
                    it.copy(
                        showError = true,
                        errorMessage = "Por favor, preencha ambos os campos de e‑mail"
                    )
                }
                false
            }

            email != confirmEmail -> {
                _uiState.update {
                    it.copy(showError = true, errorMessage = "Os e‑mails não coincidem")
                }
                false
            }

            else -> {
                _uiState.update {
                    it.copy(showError = false, errorMessage = EMPTY_STRING)
                }
                true
            }
        }
    }

    fun onSubmitRegister() {
        _uiState.update { it.copy(showError = false) }

        if (!validateEmails(_uiState.value.userRegister.email, _uiState.value.userRegister.confirmEmail)) {
            _uiState.update {
                it.copy(
                    showError = true,
                    errorMessage = "Os e-mails não coincidem"
                )
            }
            return
        }
        val user : UserRegisterDataModel = with(_uiState.value.userRegister) {
            UserRegisterDataModel(
                email = email,
                confirmEmail = confirmEmail,
                password = password,
                profile = Profile(
                    name = name,
                    birthday = birthday,
                    gender = gender
                )
            )
        }
        userRepository.createUser(
            user,
            onSuccess = {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(showRegisterDialog = false)
                    }
                    _navigationEvent.emit(NavigationEvent.ToHomeScreen)
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        showError = true,
                        errorMessage = "Email já cadastrado!"
                    )
                }
            }
        )
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
            limited.length >= 5 -> "${limited.substring(0, 2)}/${
                limited.substring(
                    2,
                    4
                )
            }/${limited.substring(4)}"

            limited.length >= 3 -> "${limited.substring(0, 2)}/${limited.substring(2)}"
            limited.isNotEmpty() -> limited
            else -> ""
        }

        _uiState.update { it.copy(userRegister = it.userRegister.copy(birthday = formatted)) }
    }

    fun onGenderRegisterChange(gender: String) {
        _uiState.update { it.copy(userRegister = it.userRegister.copy(gender = gender)) }
    }

    sealed class NavigationEvent {
        data object ToHomeScreen : NavigationEvent()
        object ToLoginScreen : NavigationEvent()
    }
}