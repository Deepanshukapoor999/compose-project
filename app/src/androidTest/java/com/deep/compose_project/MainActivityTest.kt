package com.deep.compose_project

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun helloBro_isDisplayed() {
        composeTestRule
            .onNodeWithText("Hello Bro")
            .assertIsDisplayed()
    }
}
