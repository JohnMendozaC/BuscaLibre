package com.john.buscalibre.list_product.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.john.buscalibre.shared.ui.items.ItemProductVertically
import com.john.buscalibre.shared.ui.status.StatusUI

@Composable
fun ProductsView(
    productsViewModel: ProductsViewModel,
    navController: NavHostController,
    query: String
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uIState by produceState<StatusUI<List<Product>>>(
        initialValue = StatusUI.Loading(),
        key1 = lifecycle,
        key2 = productsViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            productsViewModel.uIStatus.collect { value = it }
        }
    }

    LaunchedEffect(Unit) {
        productsViewModel.loadProducts(query)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderSearchView(true, navController) { query ->
            productsViewModel.loadProducts(query)
        }

        when (uIState) {
            is StatusUI.Error -> {
                val errorCode = (uIState as StatusUI.Error<List<Product>>).errorCode
                Error(errorCode)
            }

            is StatusUI.Loading -> Loader()
            is StatusUI.Success -> {
                val products = (uIState as StatusUI.Success<List<Product>>).result
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
    Text(text = stringResource(R.string.what_you_are_looking_for), modifier = Modifier.padding(10.dp))
    LazyColumn {
        items(products.size) { product ->
            ItemProductVertically(products[product]) {
                navController.navigate(Routes.ProductDetailsView.createRoute(products[product].id))
            }
        }
    }
}

object ProductsView {
    const val query = "query"
}
