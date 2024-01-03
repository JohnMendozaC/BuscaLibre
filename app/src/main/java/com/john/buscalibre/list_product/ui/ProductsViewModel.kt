package com.john.buscalibre.list_product.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.buscalibre.list_product.domain.ProductUseCase
import com.john.buscalibre.shared.domain.models.Product
import com.john.buscalibre.shared.domain.models.ProductInquiry
import com.john.buscalibre.shared.domain.reponse.ProductsResolverResult
import com.john.buscalibre.shared.ui.status.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _uIStatus: MutableStateFlow<StatusUI<List<Product>>> = MutableStateFlow(StatusUI.Loading())
    val uIStatus: StateFlow<StatusUI<List<Product>>> get() = _uIStatus

    fun loadProducts(query: String) {
        _uIStatus.value = StatusUI.Loading()
        viewModelScope.launch {
            val productInquiry = productUseCase.searchProduct(query)
            _uIStatus.value = validateSearchStatus(productInquiry)
        }
    }

    private fun validateSearchStatus(result: ProductsResolverResult<ProductInquiry>): StatusUI<List<Product>> {
        return when (result) {
            is ProductsResolverResult.Success -> StatusUI.Success(result.result.products)
            is ProductsResolverResult.Error -> StatusUI.Error(result.errorCode)
        }
    }
}
