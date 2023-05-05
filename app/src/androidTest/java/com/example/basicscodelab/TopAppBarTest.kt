package com.example.basicscodelab

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.Accounts
import com.example.compose.rally.rallyTabRowScreens
import com.example.compose.rally.ui.components.RallyTabRow
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = rallyTabRowScreens
        composeTestRule.setContent {
            RallyTabRow(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = Accounts
            )
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")


        composeTestRule
            .onNodeWithContentDescription(Accounts.route)
            .assertIsSelected()

        composeTestRule
            .onNodeWithContentDescription(Accounts.route)
            .assertExists()

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(Accounts.route)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
    }


}