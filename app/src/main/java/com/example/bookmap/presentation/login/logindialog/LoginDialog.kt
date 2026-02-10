package com.example.bookmap.presentation.login.logindialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bookmap.presentation.login.LoginScreenAction
import com.example.bookmap.presentation.login.LoginViewModel
import com.example.bookmap.utils.components.GenderSelector
import com.example.bookmap.utils.ui.theme.RegisterDialogBackground
import com.example.bookmap.utils.components.OutlineTextComponent
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookmap.presentation.login.LoginUiState
import kotlinx.coroutines.delay

@Composable
fun CustomLoginDialog(
    onDismissAction: () -> Unit,
    onContinueAction: () -> Unit,
    viewModel: LoginViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Dialog(
        onDismissRequest = onDismissAction,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        CustomLoginDialogContent(
            modifier = Modifier.padding(horizontal = 8.dp),
            onDismissAction = onDismissAction,
            onContinueAction = onContinueAction,
            state = state,
            name = state.userRegister.name, // Remova '?.'
            email = state.userRegister.email,
            confirmEmail = state.userRegister.confirmEmail,
            password = state.userRegister.password,
            birthday = state.userRegister.birthday,
            gender = state.userRegister.gender,
            onNameChange = { viewModel.onActionEvent(LoginScreenAction.RegisterNameChanged(it)) },
            onEmailChange = { viewModel.onActionEvent(LoginScreenAction.RegisterEmailChanged(it)) },
            onConfirmEmailChange = {
                viewModel.onActionEvent(
                    LoginScreenAction.RegisterConfirmEmailChanged(
                        it
                    )
                )
            },
            onPasswordChange = {
                viewModel.onActionEvent(
                    LoginScreenAction.RegisterPasswordChanged(
                        it
                    )
                )
            },
            onBirthdayChange = {
                viewModel.onActionEvent(
                    LoginScreenAction.RegisterBirthdayChanged(
                        it
                    )
                )
            },
            onGeneryChange = { viewModel.onActionEvent(LoginScreenAction.RegisterGenderChanged(it)) },
            enabled = viewModel.registerButtonEnabled()
        )
    }
}

@Composable
fun CustomLoginDialogContent(
    modifier: Modifier = Modifier,
    onDismissAction: () -> Unit,
    onContinueAction: () -> Unit,
    name: String = "",
    email: String = "",
    state: LoginUiState,
    confirmEmail: String = "",
    password: String = "",
    birthday: String = "",
    gender: String = "",
    onNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onConfirmEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onBirthdayChange: (String) -> Unit = {},
    onGeneryChange: (String) -> Unit = {},
    enabled: Boolean = false
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .background(color = RegisterDialogBackground, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
        ) {
            item {
                Text(
                    text = buildAnnotatedString {
                        append("Seja bem vindo ao ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFD7C9A0),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("BookMap")
                        }
                        append("!")
                    },
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Para continuar, precisamos de algumas informações suas.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                OutlineTextComponent(
                    value = name,
                    onValueChange = onNameChange,
                    label = "Nome",
                    placeholder = "Digite seu nome",
                    trailingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Ícone de pessoa")
                    },
                    textColor = Color.LightGray,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                )
            }

            item {
                OutlineTextComponent(
                    value = email,
                    onValueChange = onEmailChange,
                    label = "Email",
                    placeholder = "Digite seu email",
                    trailingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Ícone de email")
                    },
                    textColor = Color.LightGray,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                )
            }

            item {
                OutlineTextComponent(
                    value = confirmEmail,
                    onValueChange = onConfirmEmailChange,
                    label = "Confirmar Email",
                    placeholder = "Confirme seu email",
                    trailingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Ícone de email")
                    },
                    textColor = Color.LightGray,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                )
            }

            item {
                OutlineTextComponent(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = "Senha",
                    placeholder = "Digite sua senha",
                    trailingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Ícone de cadeado")
                    },
                    textColor = Color.LightGray,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            item {
                OutlineTextComponent(
                    value = birthday,
                    onValueChange = onBirthdayChange,
                    label = "Data de Nascimento",
                    placeholder = "DD/MM/AAAA",
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Ícone de calendário")
                    },
                    textColor = Color.LightGray,
                    backgroundColor = Color.Transparent,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                GenderSelector(
                    selectedGender = gender,
                    onGenderSelected = onGeneryChange
                )
            }

            item {
                var showErrorMessage by remember { mutableStateOf(false) }

                LaunchedEffect(state.errorMessage) {
                    if (state.showError && state.errorMessage.isNotBlank()) {
                        showErrorMessage = true
                        delay(2000L)
                        onDismissAction()
                        showErrorMessage = false
                    }
                }

                AnimatedVisibility(visible = showErrorMessage) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.Red.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismissAction,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray.copy(alpha = 0.3f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            onContinueAction()
                        },
                        enabled = enabled,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD7C9A0),
                            contentColor = Color(0xFF2C3E50)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Continuar")
                    }
                }
            }
        }
    }
}
