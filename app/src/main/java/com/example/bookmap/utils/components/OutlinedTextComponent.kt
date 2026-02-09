package com.example.bookmap.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmap.utils.ui.theme.ErrorBorderColor

@Composable
fun OutlineTextComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Color(0xFF6200EE),
    focusedBorderColor: Color = Color(0xFF6200EE),
    unfocusedBorderColor: Color = Color.Gray,
    errorBorderColor: Color = ErrorBorderColor
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            if (label.isNotEmpty()) Text(label)
        },
        placeholder = {
            if (placeholder.isNotEmpty()) Text(placeholder)
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        isError = isError,
        textStyle = TextStyle(color = textColor),
        modifier = modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedLabelColor = textColor,
            unfocusedLabelColor = textColor,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            errorBorderColor = errorBorderColor,
            errorContainerColor = backgroundColor,
            cursorColor = focusedBorderColor,
            disabledBorderColor = borderColor.copy(alpha = 0.5f)
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true)
@Composable
fun OutlineTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlineTextComponent(
            value = text,
            onValueChange = { text = it },
            label = "E-mail",
            placeholder = "Digite seu e-mail",
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
        )

        OutlineTextComponent(
            value = text,
            onValueChange = { text = it },
            label = "Senha",
            placeholder = "Digite sua senha",
            trailingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            isError = text.length < 6 && text.isNotEmpty()
        )
    }
}