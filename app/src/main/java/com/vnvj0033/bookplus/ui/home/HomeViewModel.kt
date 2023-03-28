package com.vnvj0033.bookplus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnvj0033.bookplus.data.model.Book
import com.vnvj0033.bookplus.data.model.Constant
import com.vnvj0033.bookplus.data.model.MainBook
import com.vnvj0033.bookplus.data.model.toMainBook
import com.vnvj0033.bookplus.data.repository.book.BookRepository
import com.vnvj0033.bookplus.data.repository.genre.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val genreRepository: GenreRepository
): ViewModel() {

    private val genres = MutableStateFlow(emptyList<String>())
    private val books = MutableStateFlow(emptyList<Book>())

    val uiState: StateFlow<HomeUiState> =
        homeUiState(genres, books).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    fun updateGenre(inputPlatform: String) {
        val platform = Constant.Platform.loadItems().find { it == inputPlatform } ?: ""
        genres.value = genreRepository.loadGenresForPlatform(platform)

        if (genres.value.isNotEmpty()) {
            updateBooks(genres.value[0])
        }
    }

    fun updateBooks(newGenre: String) {
        books.value = bookRepository.loadBookForGenre(newGenre)
    }
}

private fun homeUiState(
    genres: Flow<List<String>>,
    books: Flow<List<Book>>
): Flow<HomeUiState> {
    return combine(genres, books) { listOfGenre, listOfBook ->
        HomeUiState.Success(
            HomeStateData(
                listOfGenre,
                listOfBook.map { it.toMainBook() }
            )
        )
//        if (listOfGenre.isNotEmpty() && listOfBook.isNotEmpty()) {
//
//        } else {
//            HomeUiState.Loading
//        }
    }
}

data class HomeStateData(
    val genres: List<String>,
    val books: List<MainBook>
)

sealed interface HomeUiState {
    data class Success(val homeStateData: HomeStateData) : HomeUiState
    object Loading : HomeUiState
}
