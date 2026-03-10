package com.example.bookmap.utils.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmap.R
import com.example.bookmap.utils.constants.SEVENTY_TWO
import com.example.bookmap.utils.ui.theme.Black
import com.example.bookmap.utils.ui.theme.UnfocusField
import com.example.bookmap.utils.ui.theme.focusFieldBorder

@Composable
fun NavBarComponent(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    isSearchIconVisible: Boolean = false,
) {
    var isSearchVisible by remember { mutableStateOf(false) }

    val iconScale by animateFloatAsState(
        targetValue = if (isSearchVisible) 0.0001f else 1f,
        label = "iconScale"
    )
    val inputScale by animateFloatAsState(
        targetValue = if (isSearchVisible) 1f else 0.00001f,
        label = "inputScale"
    )

    val inputWeight by animateFloatAsState(
        targetValue = if (isSearchVisible) 1f else 0.0001f,
        label = "inputWeight"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SEVENTY_TWO.dp)
            .background(Black)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AnimatedVisibility(
            visible = !isSearchVisible,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_para_login),
                contentDescription = "BookMap Logo",
                modifier = Modifier
                    .scale(iconScale)
                    .padding(end = 8.dp)
            )
        }

        OutlineTextComponent(
            value = value,
            onValueChange = { onValueChange(it) },
            textColor = Color.Gray,
            backgroundColor = UnfocusField,
            focusedBorderColor = focusFieldBorder,
            unfocusedBorderColor = UnfocusField,
            placeholder = "Buscar livros, autores...",
            modifier = Modifier
                .animateContentSize()
                .weight(inputWeight)
                .scale(inputScale)
        )

        if (isSearchIconVisible) {
            Spacer(Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Search,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        isSearchVisible = !isSearchVisible
                        onClick()
                    },
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun NavBarPreview() {
    NavBarComponent()
}
