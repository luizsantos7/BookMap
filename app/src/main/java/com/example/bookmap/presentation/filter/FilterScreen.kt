package com.example.bookmap.presentation.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilterDialog(
    onDismissRequest: () -> Unit,
    onApplyFilter: (language: String, category: String) -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Filtros", style = MaterialTheme.typography.titleLarge) },
        text = {
            FilterDialogContent(
                selectedLanguage = selectedLanguage,
                onLanguageChange = { selectedLanguage = it },
                selectedCategory = selectedCategory,
                onCategoryChange = { selectedCategory = it }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onApplyFilter(selectedLanguage, selectedCategory)
                    onDismissRequest()
                },
                shape = RoundedCornerShape(9.dp)
            ) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                shape = RoundedCornerShape(9.dp)
            ) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialogContent(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit
) {
    val languageOptions = listOf("pt", "en", "es", "fr", "de")
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        // Campo de Linguagem
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedLanguage,
                onValueChange = {},
                label = { Text("Linguagem") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            androidx.compose.material3.DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                languageOptions.forEach { lang ->
                    DropdownMenuItem(
                        text = { Text(lang) },
                        onClick = {
                            onLanguageChange(lang)
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = selectedCategory,
            onValueChange = onCategoryChange,
            label = { Text("Categoria / Assunto") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterDialog() {
    MaterialTheme {
        Surface {
            var showDialog by remember { mutableStateOf(true) }

            if (showDialog) {
                FilterDialog(
                    onDismissRequest = { showDialog = false },
                    onApplyFilter = { lang, category ->
                        println("Filtro aplicado: $lang | $category")
                        showDialog = false
                    }
                )
            }
        }
    }
}