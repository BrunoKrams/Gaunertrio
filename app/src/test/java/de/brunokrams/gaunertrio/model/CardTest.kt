package de.brunokrams.gaunertrio.model

import org.apache.commons.math3.util.Combinations
import org.assertj.core.api.Assertions.*
import org.junit.Test

class CardTest {

    @Test
    fun `ALL contains all possible combinations`() {
        // when
        val allPossibleCombinations = Combinations(7, 3).map { combination ->
            Card(
                Gauner.entries[combination[0]],
                Gauner.entries[combination[1]],
                Gauner.entries[combination[2]]
            )
        }

        // then
        assertThat(Card.ALL).containsExactlyInAnyOrderElementsOf(allPossibleCombinations);
    }

    @Test
    fun `ALL has 35 cards`() {
        // then
        assertThat(Card.ALL).hasSize(35)
    }

    @Test
    fun `ALL contains no duplicates`() {
        // then
        assertThat(Card.ALL).doesNotHaveDuplicates();
    }

}