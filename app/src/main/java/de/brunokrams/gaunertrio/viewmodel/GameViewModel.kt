package de.brunokrams.gaunertrio.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.brunokrams.gaunertrio.model.Card
import de.brunokrams.gaunertrio.model.CardMatcher
import de.brunokrams.gaunertrio.model.Deck
import de.brunokrams.gaunertrio.model.Gauner
import de.brunokrams.gaunertrio.model.ScoreCalculator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val cardMatcher: CardMatcher,
    private val scoreCalculator: ScoreCalculator

) : ViewModel() {
    internal val _dealtCards = mutableStateListOf<CardWithHitsViewModel>()
    val dealtCards: List<CardWithHitsViewModel> = _dealtCards

    // TOTAL_NUMBER_OF_DISTINCT_CARDS - 1 because one card is hidden
    val hasCards = derivedStateOf { _dealtCards.size < Card.TOTAL_NUMBER_OF_DISTINCT_CARDS - 1 }
    private var hiddenCard: Card
    private var _deck: Deck
    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()
    private var _guessCounter = 0

    init {
        _dealtCards.clear()
        _deck = Deck.shuffled()
        hiddenCard = _deck.draw()
        draw()
        draw()
        startTimer()
    }

    fun getScore(): Int {
        return scoreCalculator.calculate(timer.value, _guessCounter, dealtCards.size)
    }

    fun check(gauner1: Gauner, gauner2: Gauner, gauner3: Gauner): Boolean {
        return cardMatcher.intersectionSize(Card(gauner1, gauner2, gauner3), hiddenCard) == 3
    }

    fun draw() {
        if (_deck.hasCards()) {
            val card = _deck.draw()
            _dealtCards.add(
                CardWithHitsViewModel(
                    dealtCards.size,
                    card,
                    cardMatcher.intersectionSize(card, hiddenCard)
                )
            )
        }
    }

    fun increaseGuessCounter() {
        _guessCounter++
    }

    private var timerJob: Job? = null

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}