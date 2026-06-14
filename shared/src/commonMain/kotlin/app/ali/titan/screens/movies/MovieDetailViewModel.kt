package app.ali.titan.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ali.titan.screens.movies.domain.GetMovieDetailUseCase
import app.ali.titan.screens.watchlist.domain.MediaType
import app.ali.titan.screens.watchlist.domain.ObserveIsInWatchlistUseCase
import app.ali.titan.screens.watchlist.domain.ToggleWatchlistUseCase
import app.ali.titan.utils.toAppError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    observeIsInWatchlist: ObserveIsInWatchlistUseCase,
    private val movieId: Int,
    private val getMovieDetail: GetMovieDetailUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    val isInWatchlist: StateFlow<Boolean> =
        observeIsInWatchlist(movieId, MediaType.MOVIE)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(WATCHLIST_STATE_TIMEOUT_MS),
                initialValue = false,
            )

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            try {
                _uiState.value = MovieDetailUiState.Success(getMovieDetail(movieId))
            } catch (e: Exception) {
                _uiState.value = MovieDetailUiState.Error(e.toAppError())
            }
        }
    }

    fun toggleWatchlist(movie: MovieUiModel) {
        viewModelScope.launch {
            toggleWatchlistUseCase(movie)
        }
    }

    private companion object {
        const val WATCHLIST_STATE_TIMEOUT_MS = 5_000L
    }
}
