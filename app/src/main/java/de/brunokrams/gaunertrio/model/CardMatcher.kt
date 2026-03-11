package de.brunokrams.gaunertrio.model

import javax.inject.Inject

class CardMatcher @Inject constructor() {
    fun intersectionSize(card1: Card, card2: Card): Int {
        val gaunerFirstCard  = setOf(card1.gauner1, card1.gauner2, card1.gauner3)
        val gaunerSecondCard = setOf(card2.gauner1, card2.gauner2, card2.gauner3)
        return gaunerFirstCard.intersect(gaunerSecondCard).size
    }

}