package com.john.buscalibre.products_details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.buscalibre.products_details.domain.ProductDetailsUseCase
import com.john.buscalibre.products_details.domain.models.DataBody
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.models.ProductInquiry
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.ui.status.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: ProductDetailsUseCase
) : ViewModel() {


    private val _uIStatus: MutableStateFlow<StatusUI<DataBody>> = MutableStateFlow(StatusUI.Loading())
    val uIStatus: StateFlow<StatusUI<DataBody>> get() = _uIStatus

    fun loadProduct(productId: String?) {
        viewModelScope.launch {
            _uIStatus.value = StatusUI.Loading()
            val product = async { productDetailsUseCase.getProductById(productId) }
            validateSearchStatusOfProduct(product.await())
        }
    }

    private fun validateSearchStatusOfProduct(result: ProductsResolverResult<Product>) {
        when (result) {
            is ProductsResolverResult.Success -> {
                loadProductsByCategory(result.result)
            }

            is ProductsResolverResult.Error -> {
                _uIStatus.value = StatusUI.Error(result.errorCode)
            }
        }
    }

    private fun loadProductsByCategory(product: Product) {
        viewModelScope.launch {
            val productsByCategory = async { productDetailsUseCase.getProductsByCategory(product.categoryId) }
            validateSearchStatusOfCategory(product, productsByCategory.await())
        }
    }

    private fun validateSearchStatusOfCategory(product: Product, result: ProductsResolverResult<ProductInquiry>) {
        _uIStatus.value = when (result) {
            is ProductsResolverResult.Success -> {
                StatusUI.Success(DataBody(product, result.result.products))
            }

            is ProductsResolverResult.Error -> {
                StatusUI.Error(result.errorCode)
            }
        }
    }
}
