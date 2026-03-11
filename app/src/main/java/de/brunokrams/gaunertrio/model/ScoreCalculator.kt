package de.brunokrams.gaunertrio.model

import javax.inject.Inject
import kotlin.math.exp

class ScoreCalculator @Inject constructor() {

    /**
     * The formula for calcluating the Highscore is floor(MAX*e^{-\RELAXATION_FACTOR(timeEllapsed/2 + 100(nrOfGuesses - 1) + 100(nrOfDrawnCards - 2)^+))}
     * where x^+ is defined to be max(0, x).
     */
    private val MAX = 1000000f

    private val PENALTY_PER_GUESS = 1000f
    private val PENALTY_PER_CARD = 1000f
    private val PENALTY_PER_SECOND = 0.5f

    private val RELAXATION_FACTOR = 0.00005f

    fun calculate(timeElapsed: Int, nrOfGuesses: Int, nrOfDrawnCards: Int): Int {
        require(!(timeElapsed < 0 || nrOfGuesses < 0 || nrOfDrawnCards < 0)) {
            "All values must be non negative"
        }
        val arg = RELAXATION_FACTOR.toDouble() * (
                PENALTY_PER_SECOND.toDouble() * timeElapsed +
                        PENALTY_PER_GUESS.toDouble() * (nrOfGuesses - 1) +
                        PENALTY_PER_CARD.toDouble() * (nrOfDrawnCards - 2).coerceAtLeast(0)
                )
        val raw = MAX.toDouble() * exp(-arg)
        return raw.coerceIn(0.0, MAX.toDouble()).toInt()
    }

}