package com.example.bookmap.presentation.Book

sealed class BookScreenAction {
    data object ErrorScreenCloseButtonAction : BookScreenAction()

    data object ErrorScreenRetryButtonAction : BookScreenAction()

}
fun BookScreenAction.fold(
    errorScreenCloseButtonAction: () -> Unit = {},
    errorScreenRetryButtonAction: () -> Unit = {},
): Unit =
    when (this) {
        BookScreenAction.ErrorScreenCloseButtonAction -> errorScreenCloseButtonAction()
        BookScreenAction.ErrorScreenRetryButtonAction -> errorScreenRetryButtonAction()
    }