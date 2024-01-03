package com.john.buscalibre.home.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.john.buscalibre.shared.domain.models.FakeProduct.anyValue
import com.john.buscalibre.shared.domain.models.FakeProduct.getFakeProducts
import com.john.buscalibre.shared.domain.reponse.Status
import com.john.buscalibre.shared.ui.LoaderTag
import com.john.buscalibre.shared.ui.status.StatusUI
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_Loading_State_when_Content_Is_Not_Displayed() {
        // Given
        val homeViewModel = mockk<HomeViewModel>(relaxed = true)
        every { homeViewModel.uIStatus } returns MutableStateFlow(StatusUI.Loading())

        // When
        composeTestRule.setContent {
            HomeView(homeViewModel, rememberNavController())
        }

        // Then
        composeTestRule
            .onNodeWithTag(LoaderTag)
            .assertIsDisplayed()
    }

    @Test
    fun given_Error_State_when_Error_Message_Is_Displayed() {
        // Given
        val errorCode = Status.NO_INTERNET.code
        val homeViewModel = mockk<HomeViewModel>(relaxed = true)
        every { homeViewModel.uIStatus } returns MutableStateFlow(StatusUI.Error(errorCode))

        // When
        composeTestRule.setContent {
            HomeView(homeViewModel, rememberNavController())
        }

        // Then
        composeTestRule
            .onNodeWithText("No tienes internet")
            .assertIsDisplayed()
    }

    @Test
    fun given_Successful_Product_Load_when_Content_Is_Displayed() {
        // Given
        val fakeProducts = getFakeProducts()
        val homeViewModel = mockk<HomeViewModel>(relaxed = true)
        every { homeViewModel.uIStatus } returns MutableStateFlow(StatusUI.Success(fakeProducts))

        // When
        composeTestRule.setContent {
            HomeView(homeViewModel, rememberNavController())
        }

        // Then
        fakeProducts.forEach { product ->
            composeTestRule
                .onNodeWithText(product.title ?: anyValue)
                .assertIsDisplayed()
        }
    }
}
