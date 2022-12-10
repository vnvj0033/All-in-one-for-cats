package com.vnvj0033.bookplus.presentation.presenter

import androidx.lifecycle.ViewModel
import com.vnvj0033.bookplus.R
import com.vnvj0033.bookplus.data.entity.Book
import com.vnvj0033.bookplus.data.repository.BookRepository
import com.vnvj0033.bookplus.data.repository.FakeBookRepo
import com.vnvj0033.bookplus.domain.model.MainBook
import com.vnvj0033.bookplus.presentation.ui.state.BookListState
import com.vnvj0033.bookplus.presentation.ui.state.GenreSelectionListState
import com.vnvj0033.bookplus.presentation.ui.state.PlatformSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.single
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel() {
    private val kyobo = PlatformSelectionState("kyobo", R.drawable.logo_kyobo)
    private val yes24 = PlatformSelectionState("yes24", R.drawable.logo_yes24)
    private val aladin = PlatformSelectionState("aladin", R.drawable.logo_aladin)

    val platformStates = listOf(kyobo, yes24, aladin, kyobo, yes24, aladin)
    val genreState = GenreSelectionListState()
    val bookListState = BookListState()

    suspend fun loadGenre() {
        val genres = bookRepository.loadGenres().single()

        genreState.options.clear()
        genreState.options.addAll(genres)
    }

    suspend fun loadBooks() {
        val selectedGenre = genreState.selectGenre
        val books = bookRepository.loadBooks(selectedGenre).single().map { book ->
            book.toMainBook()
        }

        bookListState.books.clear()
        bookListState.books.addAll(books)
    }


    private fun Book.toMainBook() = MainBook("", title, "", "")
}