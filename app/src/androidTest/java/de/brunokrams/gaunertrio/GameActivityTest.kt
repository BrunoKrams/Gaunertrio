package de.brunokrams.gaunertrio

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.brunokrams.gaunertrio.model.Card
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GameActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<GameActivity>()

    @Test
    fun clickOnDrawCardButtonAddsCardToHand() {
        // given
        composeTestRule.waitForIdle()
        val initialNrOfCards =
            composeTestRule.onAllNodesWithTag("Displayed Card").fetchSemanticsNodes().size

        // when
        composeTestRule.onNodeWithText("Karte").performClick()
        composeTestRule.waitForIdle()

        // then
        composeTestRule
            .onAllNodesWithTag("Displayed Card")
            .assertCountEquals(initialNrOfCards + 1)
    }

    @Test
    fun clickOnSolveButtonDisplaysSolveDialog() {
        // given
        composeTestRule.waitForIdle()

        // when
        composeTestRule.onNodeWithText("Lösen").performClick()
        composeTestRule.waitForIdle()

        // then
        composeTestRule.onNode(hasText("Ok"))
            .assertIsDisplayed()
    }

    @Test
    fun drawCardButtonIsDisabledWhenAllCardsHaveBeenDrawn() {
        // given
        val drawCardButton = composeTestRule.onNodeWithText("Karte")
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            drawCardButton.isDisplayed()
        }
        drawCardButton.assertIsEnabled()

        // when
        repeat(Card.ALL.size - 1) { drawCardButton.performClick() }

        // then
        drawCardButton.assertIsNotEnabled()
    }
}
