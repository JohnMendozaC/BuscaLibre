package com.john.buscalibre.list_product.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.john.buscalibre.shared.domain.reponse.Status
import com.john.buscalibre.shared.ui.LoaderTag
import com.john.buscalibre.shared.ui.status.StatusUI
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class ProductsViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_UISuccessState_when_Rendering_then_ShowBody() {
        // Given
        val productsViewModel = mockk<ProductsViewModel>(relaxed = true)
        every { productsViewModel.uIStatus } returns MutableStateFlow(StatusUI.Success(emptyList()))

        composeTestRule.setContent {
            ProductsView(productsViewModel, rememberNavController(), ProductsView.query)
        }

        // Then
        composeTestRule.onNodeWithText("Lo que estas buscando").assertIsDisplayed()
    }

    @Test
    fun given_UIErrorState_when_Rendering_then_ShowError() {
        // Given
        val productsViewModel = mockk<ProductsViewModel>(relaxed = true)
        every { productsViewModel.uIStatus } returns MutableStateFlow(StatusUI.Error(Status.NO_INTERNET.code))

        composeTestRule.setContent {
            ProductsView(productsViewModel, rememberNavController(), ProductsView.query)
        }

        // Then
        composeTestRule.onNodeWithText("No tienes internet").assertIsDisplayed()
    }

    @Test
    fun given_UILoadingState_when_Rendering_then_ShowLoading() {
        // Given
        val productsViewModel = mockk<ProductsViewModel>(relaxed = true)
        every { productsViewModel.uIStatus } returns MutableStateFlow(StatusUI.Loading())

        composeTestRule.setContent {
            ProductsView(productsViewModel, rememberNavController(), ProductsView.query)
        }

        // Then
        composeTestRule.onNodeWithTag(LoaderTag).assertIsDisplayed()
    }
}
