package de.brunokrams.gaunertrio.viewmodel

import de.brunokrams.gaunertrio.model.Card
import de.brunokrams.gaunertrio.model.CardMatcher
import de.brunokrams.gaunertrio.model.ScoreCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `view model inits deck and draws two cards upon creation`() {
        // given
        val viewModel = GameViewModel(CardMatcher(), ScoreCalculator())

        // when / then
        assertThat(viewModel.hasCards.value).isTrue
        assertThat(viewModel.dealtCards).hasSize(2)
    }

    @Test
    fun `draw adds card to dealtCards`() {
        // given
        val viewModel = GameViewModel(CardMatcher(), ScoreCalculator())
        // when
        viewModel.draw()

        // then
        assertThat(viewModel.dealtCards).hasSize(3)
    }

    @Test
    fun `has cards is true until all cards are dealt`() {
        // given
        val viewModel = GameViewModel(CardMatcher(), ScoreCalculator())

        // when / then
        repeat(Card.TOTAL_NUMBER_OF_DISTINCT_CARDS - 3) {
            assertThat(viewModel.hasCards.value).isTrue
            viewModel.draw()
        }

        // then
        assertThat(viewModel.hasCards.value).isFalse
    }

    @Test
    fun `check returns true if gauners match`() {
        // given
        val viewModel = GameViewModel(CardMatcher(), ScoreCalculator())
        val hiddenCardProperty =
            GameViewModel::class.declaredMemberProperties.first { it.name == "hiddenCard" }
        hiddenCardProperty.isAccessible = true
        val hiddenCard = hiddenCardProperty.get(viewModel) as Card

        // when
        val result = viewModel.check(hiddenCard.gauner1, hiddenCard.gauner2, hiddenCard.gauner3)

        // then
        assertThat(result).isTrue
    }

    @Test
    fun `check returns false if gauners don't match`() {
        // given
        val viewModel = GameViewModel(CardMatcher(), ScoreCalculator())
        viewModel.draw()
        val someCardWhichIsNotTheHiddenOne = viewModel.dealtCards.get(0).card

        // when
        val result = viewModel.check(
            someCardWhichIsNotTheHiddenOne.gauner1,
            someCardWhichIsNotTheHiddenOne.gauner2,
            someCardWhichIsNotTheHiddenOne.gauner3
        )

        // then
        assertThat(result).isFalse
    }

}