package com.example.bookmap.utils.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.dp
import com.example.bookmap.utils.ui.theme.ErrorBorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelector(
    selectedGender: String = "",
    onGenderSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Masculino", "Feminino", "Outro")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            shape = RoundedCornerShape(16.dp),
            label = { Text("Gênero") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color.LightGray,
                unfocusedTextColor = Color.LightGray,
                focusedLabelColor = Color.LightGray,
                unfocusedLabelColor = Color.LightGray,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                errorBorderColor = ErrorBorderColor,
                errorContainerColor = Color.Transparent,
                cursorColor = Color.Gray,
                disabledBorderColor = Color.Gray.copy(alpha = 0.5f)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(text = gender, modifier = Modifier.padding(start = 8.dp)) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }
}