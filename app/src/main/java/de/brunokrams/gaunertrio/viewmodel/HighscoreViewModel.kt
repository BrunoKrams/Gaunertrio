package de.brunokrams.gaunertrio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.brunokrams.gaunertrio.model.Score
import de.brunokrams.gaunertrio.model.ScoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HighscoreViewModel @Inject constructor(private val scoreRepository: ScoreRepository) :
    ViewModel() {

    val highscores: StateFlow<List<Score>> = scoreRepository.getTopTen()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addScore(score: Score) {
        viewModelScope.launch { scoreRepository.addScore(score) }
    }
}



