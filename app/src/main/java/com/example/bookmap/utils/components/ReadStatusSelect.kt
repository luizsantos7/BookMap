package com.example.bookmap.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookmap.data.models.ReadStatusDataModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadStatusSelector(
    currentStatus: ReadStatusDataModel,
    onStatusChanged: (ReadStatusDataModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = ReadStatusDataModel.values()

    val backgroundColor = Color(0xFF171D23) // 0xFF2D333B
    val borderColor = Color(0xFF2D333B) // 0xFF444C56
    val textColor = Color.LightGray
    val labelColor = Color.Gray
    val dropdownBackground = Color(0xFF22272E)
    val selectedItemColor = Color(0xFF539BF5)
    val hoverColor = Color(0xFF373E47)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = when (currentStatus) {
                ReadStatusDataModel.READ -> "Lido"
                ReadStatusDataModel.READING -> "Lendo"
                ReadStatusDataModel.UNREAD -> "Pretendo ler"
                ReadStatusDataModel.PAUSED -> "Pausado"
                ReadStatusDataModel.DROPPED -> "Desistido"
            },
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    "Status de Leitura",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                disabledTextColor = textColor,
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                disabledContainerColor = backgroundColor,
                cursorColor = selectedItemColor,
                focusedLabelColor = selectedItemColor,
                unfocusedLabelColor = labelColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTrailingIconColor = textColor,
                unfocusedTrailingIconColor = labelColor
            ),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (expanded) selectedItemColor else borderColor,
                    shape = RoundedCornerShape(5.dp)
                )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(dropdownBackground)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            options.forEach { status ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = when (status) {
                                ReadStatusDataModel.READ -> "Lido"
                                ReadStatusDataModel.READING -> "Lendo"
                                ReadStatusDataModel.UNREAD -> "Pretendo ler"
                                ReadStatusDataModel.PAUSED -> "Pausado"
                                ReadStatusDataModel.DROPPED -> "Desistido"
                            },
                            color = if (status == currentStatus)
                                selectedItemColor
                            else textColor,
                            fontSize = 14.sp,
                            fontWeight = if (status == currentStatus)
                                FontWeight.SemiBold
                            else FontWeight.Normal
                        )
                    },
                    onClick = {
                        onStatusChanged(status)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = textColor,
                        leadingIconColor = textColor,
                        trailingIconColor = textColor,
                        disabledTextColor = labelColor,
                        disabledLeadingIconColor = labelColor,
                        disabledTrailingIconColor = labelColor
                    ),
                    modifier = Modifier.background(
                        if (status == currentStatus)
                            hoverColor
                        else Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1117)
@Composable
fun ReadStatusSelectorPreview() {
    var currentStatus by remember { mutableStateOf(ReadStatusDataModel.READING) }

    Column(
        modifier = Modifier
            .background(Color(0xFF0D1117))
            .padding(16.dp)
    ) {
        // Preview com status "Lendo"
        ReadStatusSelector(
            currentStatus = currentStatus,
            onStatusChanged = { currentStatus = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preview com status "Não lido"
        ReadStatusSelector(
            currentStatus = ReadStatusDataModel.UNREAD,
            onStatusChanged = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Preview com status "Lido"
        ReadStatusSelector(
            currentStatus = ReadStatusDataModel.READ,
            onStatusChanged = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1117, name = "Dark Theme Preview")
@Composable
fun ReadStatusSelectorDarkPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFF0D1117))
            .padding(24.dp)
    ) {
        Text(
            text = "Status Selector Component",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        var status by remember { mutableStateOf(ReadStatusDataModel.PAUSED) }

        ReadStatusSelector(
            currentStatus = status,
            onStatusChanged = { status = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}