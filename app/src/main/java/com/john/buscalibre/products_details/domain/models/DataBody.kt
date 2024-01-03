package com.john.buscalibre.products_details.domain.models

import com.john.buscalibre.shared.domain.models.Product

data class DataBody(
    val product: Product,
    val products: List<Product>
)
