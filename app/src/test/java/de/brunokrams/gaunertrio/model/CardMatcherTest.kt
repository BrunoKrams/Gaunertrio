package de.brunokrams.gaunertrio.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CardMatcherTest {

    @Test
    fun `intersection size is calculated correctly`() {
        val cardMatcher = CardMatcher()
        testData.forEach { testDataSet ->
            assertThat(
                cardMatcher.intersectionSize(
                    testDataSet.card1,
                    testDataSet.vard2
                )
            ).isEqualTo(testDataSet.expected)
        }
    }

    data class TestDataSet(val card1: Card, val vard2: Card, val expected: Int)

    val testData = listOf(
        TestDataSet(
            Card(Gauner.BLUE, Gauner.RED, Gauner.GREEN),
            Card(Gauner.PURPLE, Gauner.YELLOW, Gauner.BROWN),
            0
        ),
        TestDataSet(
            Card(Gauner.BLUE, Gauner.RED, Gauner.GREEN),
            Card(Gauner.PURPLE, Gauner.YELLOW, Gauner.BLUE),
            1
        ),
        TestDataSet(
            Card(Gauner.BLUE, Gauner.RED, Gauner.GREEN),
            Card(Gauner.YELLOW, Gauner.BLUE, Gauner.RED),
            2
        ),
        TestDataSet(
            Card(Gauner.BLUE, Gauner.RED, Gauner.GREEN),
            Card(Gauner.GREEN, Gauner.BLUE, Gauner.RED),
            3
        )
    )

}