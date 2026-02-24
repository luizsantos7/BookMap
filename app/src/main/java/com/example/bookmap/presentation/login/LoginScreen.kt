package com.example.bookmap.presentation.login

import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookmap.presentation.login.LoginViewModel.NavigationEvent.ToHomeScreen
import com.example.bookmap.presentation.login.logindialog.CustomLoginDialog
import com.example.bookmap.presentation.login.LoginViewModel.NavigationEvent.ToLoginScreen
import com.example.bookmap.utils.ui.theme.UnfocusField
import com.example.bookmap.utils.ui.theme.focusFieldBorder
import com.example.bookmap.utils.components.FixedButton
import com.example.bookmap.utils.components.OutlineTextComponent
import com.example.bookmap.utils.components.SignUpPrompt
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var visible by remember { mutableStateOf(false) }
    val email = uiState.email
    val password = uiState.password

    LaunchedEffect(uiState.showError) {
        if (uiState.showError) {
            visible = true
            delay(3000L)
            viewModel.onDismissError()
            visible = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is ToHomeScreen -> {
                    navController.navigate("home_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                }
                is ToLoginScreen -> {
                    navController.navigate("login_screen")
                }
            }
        }
    }

    Image(
        modifier = modifier.fillMaxSize(),
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
            modifier = modifier.fillMaxSize(),
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
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

            }
            if (visible) {
                Text(
                    text = "Erro: E-mail ou senha inválidos",
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(modifier = Modifier.height(22.dp))
            }
            FixedButton(
                primaryButtonText = "Entrar",
                secundaryButtonText = "Continuar como convidado 🔒",
                primaryClickButton = {
                    viewModel.onSubmitLogin(email = email, password = password)
                },
                secundaryClickButton = {
                    Toast.makeText(context, "Função ainda será implementada", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(horizontal = 48.dp),
                enabled = viewModel.enableLoginButton()
            )

            Spacer(Modifier.height(32.dp))
            SignUpPrompt(onSignUpClick = { viewModel.callRegisterDialog()})

            if (uiState.showRegisterDialog) {
                CustomLoginDialog(
                    onDismissAction = {viewModel.dismissRegisterDialog()},
                    onContinueAction = {viewModel.onActionEvent(LoginScreenAction.SubmitRegister) },
                    viewModel = viewModel
                )
            }
        }
    }
}


@Preview
@Composable
private fun SDsd() {
}