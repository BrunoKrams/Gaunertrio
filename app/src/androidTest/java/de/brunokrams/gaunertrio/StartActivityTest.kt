package de.brunokrams.gaunertrio

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StartActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<StartActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun clickOnNewGameButtonStartsNewGame() {
        // when
        composeTestRule.onNodeWithText("Neues Spiel").performClick()
        composeTestRule.waitForIdle()

        // then
        intended(hasComponent(GameActivity::class.java.name))
    }

    @Test
    fun clickOnHighscoreButtonLaunchesHighscoreActivity() {
        // when
        composeTestRule.onNodeWithText("High Scores").performClick()
        composeTestRule.waitForIdle()

        // then
        intended(hasComponent(HighscoresActivity::class.java.name))
    }
}
