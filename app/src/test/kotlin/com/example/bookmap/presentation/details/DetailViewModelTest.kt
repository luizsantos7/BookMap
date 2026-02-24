package com.example.bookmap.presentation.details

import androidx.lifecycle.SavedStateHandle
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.data.repository.StatusRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class DetailViewModelTest {

    @MockK
    lateinit var bookRepository: BookRepository

    @MockK
    lateinit var statusRepository: StatusRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadBookDetails success updates uiState`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val book = BookDetailsDataModel(
            id = 1L,
            title = "Test",
            authors = listOf(AuthorDataModel(name = "Author")),
            summaries = listOf("summary"),
            languages = listOf("en"),
            copyright = false,
            coverUrl = null,
            isRead = ReadStatusDataModel.UNREAD
        )

        coEvery { bookRepository.buscarLivroPorId("1") } returns Result.success(book)

        val savedState = SavedStateHandle(mapOf("bookId" to "1"))
        val vm = DetailViewModel(bookRepository, statusRepository, savedState)

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(book, vm.uiState.value.book)
        assertTrue(vm.uiState.value.isContinue)
        assertEquals(false, vm.uiState.value.isLoading)
    }

    @Test
    fun `loadBookDetails failure sets error`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        coEvery { bookRepository.buscarLivroPorId("1") } returns Result.failure(Exception("fail"))

        val savedState = SavedStateHandle(mapOf("bookId" to "1"))
        val vm = DetailViewModel(bookRepository, statusRepository, savedState)

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(vm.uiState.value.showError)
        assertEquals("erro ao carregar detalhes do livro", vm.uiState.value.errorMessage)
    }

    @Test
    fun `onStatusChange calls repository and updates uiState`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val book = BookDetailsDataModel(
            id = 1L,
            title = "Test",
            authors = emptyList(),
            summaries = emptyList(),
            languages = emptyList(),
            copyright = false,
            coverUrl = null,
            isRead = ReadStatusDataModel.UNREAD
        )

        coEvery { bookRepository.buscarLivroPorId("1") } returns Result.success(book)

        val savedState = SavedStateHandle(mapOf("bookId" to "1"))
        val vm = DetailViewModel(bookRepository, statusRepository, savedState)

        testDispatcher.scheduler.advanceUntilIdle()

        vm.onActionEvent(DetailScreenAction.OnStatusChange(ReadStatusDataModel.READ))

        verify { statusRepository.removeBook("1") }
        verify { statusRepository.addBook(match { it.id == 1L && it.isRead == ReadStatusDataModel.READ }) }

        assertEquals(ReadStatusDataModel.READ, vm.uiState.value.book.isRead)
    }
}
