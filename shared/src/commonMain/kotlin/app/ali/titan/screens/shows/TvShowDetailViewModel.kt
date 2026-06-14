package app.ali.titan.screens.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ali.titan.screens.shows.domain.GetTvShowDetailUseCase
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

class TvShowDetailViewModel(
    private val tvShowId: Int,
    private val presentLabel: String,
    private val getTvShowDetail: GetTvShowDetailUseCase,
    observeIsInWatchlist: ObserveIsInWatchlistUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<TvShowDetailUiState>(TvShowDetailUiState.Loading)
    val uiState: StateFlow<TvShowDetailUiState> = _uiState.asStateFlow()

    val isInWatchlist: StateFlow<Boolean> =
        observeIsInWatchlist(tvShowId, MediaType.TV)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(WATCHLIST_STATE_TIMEOUT_MS),
                initialValue = false,
            )

    init {
        loadTvShowDetail()
    }

    fun loadTvShowDetail() {
        viewModelScope.launch {
            _uiState.value = TvShowDetailUiState.Loading
            try {
                _uiState.value = TvShowDetailUiState.Success(getTvShowDetail(tvShowId, presentLabel))
            } catch (e: Exception) {
                _uiState.value = TvShowDetailUiState.Error(e.toAppError())
            }
        }
    }

    fun toggleWatchlist(tvShow: TvShowUiModel) {
        viewModelScope.launch {
            toggleWatchlistUseCase(tvShow)
        }
    }

    private companion object {
        const val WATCHLIST_STATE_TIMEOUT_MS = 5_000L
    }
}
