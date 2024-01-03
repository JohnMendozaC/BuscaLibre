package com.john.buscalibre.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.john.buscalibre.R
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.models.Routes
import com.john.buscalibre.shared.domain.reponse.Status
import com.john.buscalibre.shared.ui.HeaderSearchView
import com.john.buscalibre.shared.ui.ImageOfControlStatus
import com.john.buscalibre.shared.ui.Loader
import com.john.buscalibre.shared.ui.items.ItemProductHorizontally
import com.john.buscalibre.shared.ui.status.StatusUI

@Composable
fun HomeView(
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uIStateProduct by produceState<StatusUI<List<Product>>>(
        initialValue = StatusUI.Loading(),
        key1 = lifecycle,
        key2 = homeViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            homeViewModel.uIStatus.collect { value = it }
        }
    }

    val offer = stringResource(R.string.offers)
    LaunchedEffect(Unit) {
        homeViewModel.loadProducts(offer)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderSearchView(false, navController) { query ->
            navController.navigate(Routes.ProductsView.createRoute(query))
        }

        when (uIStateProduct) {
            is StatusUI.Error -> {
                val errorCode = (uIStateProduct as StatusUI.Error<List<Product>>).errorCode
                Error(errorCode)
            }

            is StatusUI.Loading -> Loader()
            is StatusUI.Success -> {
                val products = (uIStateProduct as StatusUI.Success<List<Product>>).result
                Body(navController, products)
            }
        }
    }
}

@Composable
private fun Error(errorCode: Int) {
    when (errorCode) {
        Status.NO_INTERNET.code -> ImageOfControlStatus(Icons.Filled.Warning, R.string.no_internet)
        Status.EMPTY_LIST.code -> ImageOfControlStatus(Icons.Filled.Info, R.string.no_result)
    }
}

@Composable
private fun Body(navController: NavHostController, products: List<Product>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = stringResource(R.string.offers_of_products), modifier = Modifier.padding(10.dp))
        LazyRow {
            items(products.size) { product ->
                ItemProductHorizontally(products[product]) {
                    navController.navigate(Routes.ProductDetailsView.createRoute(products[product].id))
                }
            }
        }
        ImageOfTheOffer()
    }
}


