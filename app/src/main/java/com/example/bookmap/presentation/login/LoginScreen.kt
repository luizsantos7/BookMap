package com.example.bookmap.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bookmap.presentation.Book.BookUiState
import com.example.bookmap.presentation.Book.BookViewModel
import com.example.bookmap.presentation.ui.theme.UnfocusField
import com.example.bookmap.presentation.ui.theme.focusFieldBorder
import com.example.bookmap.utils.components.FixedButton
import com.example.bookmap.utils.components.OutlineTextComponent
import com.example.bookmap.utils.components.SignUpPrompt
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val presentation by viewModel.loginPresentation.collectAsStateWithLifecycle()

    val password = presentation.password
    val email = presentation.email
    val verify = if (password.length < 6 && password.isNotEmpty()) false else true

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = com.example.bookmap.R.drawable.login),
        contentDescription = "Login Image",
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Black, Color.Transparent),
                    startY = Float.POSITIVE_INFINITY,
                    endY = 0f
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(200.dp),
                contentDescription = "Logo BookMap",
                contentScale = ContentScale.Crop,
                painter = painterResource(id = com.example.bookmap.R.drawable.logo_para_login),
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 38.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlineTextComponent(
                    value = email,
                    onValueChange = {
                        viewModel.onActionEvent(LoginScreenAction.EmailChanged(it))
                    },
                    label = "E-mail",
                    backgroundColor = UnfocusField,
                    focusedBorderColor = focusFieldBorder,
                    unfocusedBorderColor = UnfocusField,
                    textColor = Color.Gray,
                    placeholder = "Digite seu e-mail",
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                OutlineTextComponent(
                    value = password,
                    onValueChange = {
                        viewModel.onActionEvent(LoginScreenAction.PasswordChanged(it))
                    },
                    textColor = Color.Gray,
                    backgroundColor = UnfocusField,
                    focusedBorderColor = focusFieldBorder,
                    unfocusedBorderColor = UnfocusField,
                    label = "Senha",
                    placeholder = "Digite sua senha",
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    isError = password.length < 6 && password.isNotEmpty(),
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            FixedButton(
                primaryButtonText = "Entrar",
                secundaryButtonText = "Continuar como convidado",
                primaryClickButton = {
                    viewModel.onActionEvent(LoginScreenAction.SubmitLogin)
                },
                secundaryClickButton = { /* ação visitante */ },
                modifier = Modifier
                    .padding(horizontal = 48.dp),
                enabled = if (viewModel.enableLoginButton() && verify) true else false
            )

            Spacer(Modifier.height(32.dp))
            SignUpPrompt(onSignUpClick = { /* ação cadastro */ })

            when (uiState) {
                is LoginUiState.Initial -> {} // nada
                is LoginUiState.Loading -> CircularProgressIndicator()
                is LoginUiState.Error -> {
                    val message = (uiState as LoginUiState.Error).message
                    Text(
                        text = "Erro: $message",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is LoginUiState.Success -> {
                    //vai ter o navigate
                }
            }
        }
    }
}


@Preview
@Composable
private fun SDsd() {
    LoginScreen()
}