package com.example.bookmap.utils.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmap.R
import com.example.bookmap.utils.constants.SEVENTY_TWO
import com.example.bookmap.utils.ui.theme.Black
import com.example.bookmap.utils.ui.theme.UnfocusField

@Composable
fun NavBarComponent(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    isSearchIconVisible: Boolean = false,
) {
    var visible by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (visible) Color.White else Black,
        animationSpec = tween(300),
        label = "backgroundColor"
    )

    val iconColor by animateColorAsState(
        targetValue = if (visible) Color.Black else Color.White,
        animationSpec = tween(300),
        label = "iconColor"
    )

    val textFieldBackgroundColor by animateColorAsState(
        targetValue = if (visible) Color.Transparent else UnfocusField,
        animationSpec = tween(300),
        label = "textFieldBackgroundColor"
    )

    val textFieldBorderColor by animateColorAsState(
        targetValue = if (visible) Color.LightGray else UnfocusField,
        animationSpec = tween(300),
        label = "textFieldBorderColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (visible) Color.Black else Color.Gray,
        animationSpec = tween(300),
        label = "textColor"
    )


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SEVENTY_TWO.dp)
            .background(backgroundColor)
            .animateContentSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AnimatedVisibility(
            visible = !visible,
            enter = fadeIn(tween(300)) +
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(300)
                    ),
            exit = fadeOut(tween(200)) +
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(200)
                    ),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_para_login),
                contentDescription = "BookMap Logo"
            )
        }

        if (isSearchIconVisible && !visible) {
            Icon(
                imageVector = Icons.Default.Search,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        visible = true
                        onClick()
                    },
                contentDescription = "Search Icon",
                tint = iconColor
            )
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(300)) +
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(300)
                    ),
            exit = fadeOut(tween(200)) +
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(200)
                    )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = iconColor,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        visible = false
                        onValueChange("")
                    }
            )
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(300)) +
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    ),
            exit = fadeOut(tween(200)) +
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(200)
                    )
        ) {
            OutlineTextComponent(
                value = value,
                onValueChange = { onValueChange(it) },
                textColor = textColor,
                backgroundColor = textFieldBackgroundColor,
                focusedBorderColor = textFieldBorderColor,
                unfocusedBorderColor = textFieldBorderColor,
                placeholder = "Buscar livros, autores...",
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavBarPreview() {
    NavBarComponent(
        isSearchIconVisible = true,
    )
}@Preview(showBackground = true)
@Composable
private fun NavBarFalsePreview() {
    NavBarComponent(
        isSearchIconVisible = false,
    )
}
