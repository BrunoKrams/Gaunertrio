package de.brunokrams.gaunertrio.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DeckTest {

    @Test
    fun `deck has 35 cards`() {
        // given
        val deck = Deck.shuffled()

        // when
        while (deck.hasCards()) {
            deck.draw();
        }

        // then
        assertThat(deck.hasCards()).isFalse
    }
}