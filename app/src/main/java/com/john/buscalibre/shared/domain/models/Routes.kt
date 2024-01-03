package com.john.buscalibre.shared.domain.models


import com.john.buscalibre.list_product.ui.ProductsView.query
import com.john.buscalibre.products_details.ui.ProductDetailsView.productId

sealed class Routes(val route: String) {
    object SplashScreen : Routes("SplashScreen")
    object HomeView : Routes("HomeView")
    object ProductsView : Routes("ProductsView/{$query}") {
        fun createRoute(query: String) = "ProductsView/$query"
    }

    object ProductDetailsView : Routes("ProductDetailsView/{$productId}") {
        fun createRoute(productId: String) = "ProductDetailsView/$productId"
    }
}
