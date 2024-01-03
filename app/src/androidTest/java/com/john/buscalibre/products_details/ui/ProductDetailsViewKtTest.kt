package com.john.buscalibre.products_details.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.john.buscalibre.products_details.domain.models.FakeDataBody.getFakeDataBody
import com.john.buscalibre.shared.domain.reponse.Status
import com.john.buscalibre.shared.ui.LoaderTag
import com.john.buscalibre.shared.ui.status.StatusUI
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailsViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        productDetailsViewModel = mockk(relaxed = true)
    }

    @Test
    fun given_UISuccessState_when_Rendering_then_ShowBody() {
        // Given
        every { productDetailsViewModel.uIStatus } returns MutableStateFlow(StatusUI.Success(getFakeDataBody()))

        // When
        composeTestRule.setContent {
            ProductDetailsView(ProductDetailsView.productId, productDetailsViewModel, rememberNavController())
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Image in product detail")
            .assertIsDisplayed()
    }

    @Test
    fun given_UIErrorState_when_Rendering_then_ShowError() {
        // Given
        every { productDetailsViewModel.uIStatus } returns MutableStateFlow(StatusUI.Error(Status.NO_INTERNET.code))

        // When
        composeTestRule.setContent {
            ProductDetailsView(ProductDetailsView.productId, productDetailsViewModel, rememberNavController())
        }

        // Then
        composeTestRule
            .onNodeWithText("No tienes internet")
            .assertIsDisplayed()
    }

    @Test
    fun given_UILoadingState_when_Rendering_then_ShowLoading() {
        // Given
        every { productDetailsViewModel.uIStatus } returns MutableStateFlow(StatusUI.Loading())

        // When
        composeTestRule.setContent {
            ProductDetailsView(ProductDetailsView.productId, productDetailsViewModel, rememberNavController())
        }

        // Then
        composeTestRule
            .onNodeWithTag(LoaderTag)
            .assertIsDisplayed()
    }
}
