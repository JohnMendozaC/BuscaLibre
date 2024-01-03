package com.john.buscalibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.john.buscalibre.home.ui.HomeView
import com.john.buscalibre.home.ui.HomeViewModel
import com.john.buscalibre.home.ui.SplashScreen
import com.john.buscalibre.list_product.ui.ProductsView
import com.john.buscalibre.list_product.ui.ProductsView.query
import com.john.buscalibre.list_product.ui.ProductsViewModel
import com.john.buscalibre.products_details.ui.ProductDetailsView
import com.john.buscalibre.products_details.ui.ProductDetailsView.productId
import com.john.buscalibre.products_details.ui.ProductDetailsViewModel
import com.john.buscalibre.shared.domain.models.Routes
import com.john.buscalibre.ui.theme.BuscaLibreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val productsViewModel: ProductsViewModel by viewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuscaLibreTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = Routes.SplashScreen.route) {
                        composable(Routes.SplashScreen.route) { SplashScreen(navigationController) }
                        composable(Routes.HomeView.route) { HomeView(homeViewModel, navigationController) }
                        composable(
                            Routes.ProductsView.route,
                            arguments = listOf(navArgument(query) { type = NavType.StringType })
                        ) { backStackEntry ->
                            val query = backStackEntry.arguments?.getString(query) ?: ""
                            ProductsView(productsViewModel, navigationController, query)
                        }
                        composable(
                            Routes.ProductDetailsView.route,
                            arguments = listOf(navArgument(productId) { type = NavType.StringType })
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString(productId)
                            ProductDetailsView(productId, productDetailsViewModel, navigationController)
                        }
                    }
                }
            }
        }
    }
}
