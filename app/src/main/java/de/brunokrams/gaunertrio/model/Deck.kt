package de.brunokrams.gaunertrio.model

import java.util.Stack

class Deck private constructor(private val cards: Stack<Card>) {

    fun hasCards(): Boolean {
        return cards.isNotEmpty()
    }

    fun draw(): Card {
        return cards.pop()
    }

    companion object {
        fun shuffled(): Deck {
            val stack = Stack<Card>()
            stack.addAll(Card.ALL.shuffled())
            return Deck(stack)
        }

    }
}

