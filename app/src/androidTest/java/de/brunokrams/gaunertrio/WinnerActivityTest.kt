package de.brunokrams.gaunertrio

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
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
class WinnerActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<WinnerActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun tappingOnScreenLaunchesStartActivity() {
        // when
        composeTestRule.onRoot().performClick()
        composeTestRule.waitForIdle()

        // then
        intended(hasComponent(StartActivity::class.java.name))
    }
}
