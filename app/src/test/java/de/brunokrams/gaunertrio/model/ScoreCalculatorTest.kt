package de.brunokrams.gaunertrio.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class ScoreCalculatorTest {

    @Test
    fun `calculate throws exception when any value is negative`() {
        val scoreCalculator = ScoreCalculator()
        throwExceptionTestData.forEach { testDataSet ->
            assertThatThrownBy(
                {
                    scoreCalculator.calculate(
                        testDataSet.timeEllapsed,
                        testDataSet.nrOfGuesses,
                        testDataSet.nrOfDrawnCards
                    )
                })
                .isInstanceOf(IllegalArgumentException::class.java)

        }
    }


    val throwExceptionTestData = listOf(
        TestDataSet(-1, 4, 4, 0),
        TestDataSet(4, -1, 4, 0),
        TestDataSet(4, 4, -1, 0)
    )

    @Test
    fun `calculate works as expected`() {
        val scoreCalculator = ScoreCalculator()
        asExpectedTestData.forEach { testDataSet ->
            assertThat(
                scoreCalculator.calculate(
                    testDataSet.timeEllapsed,
                    testDataSet.nrOfGuesses,
                    testDataSet.nrOfDrawnCards
                )
            ).isEqualTo(testDataSet.expected)
        }
    }

    val asExpectedTestData = listOf(
        TestDataSet(24, 1, 3, 950658),
        TestDataSet(54, 2, 0, 949946),
        TestDataSet(21, 1, 2, 999475)
    )


    data class TestDataSet(
        val timeEllapsed: Int,
        val nrOfGuesses: Int,
        val nrOfDrawnCards: Int,
        val expected: Int
    )

    @Test
    fun `calculate gives MAX when all values are 0`() {
        // given
        val scoreCalculator = ScoreCalculator()

        // when
        val score = scoreCalculator.calculate(0,0,0)

        // then
        assertThat(score).isEqualTo(1000000)
    }
}