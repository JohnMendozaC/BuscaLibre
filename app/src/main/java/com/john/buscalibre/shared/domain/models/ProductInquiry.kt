package com.john.buscalibre.shared.domain.models

import java.io.Serializable

data class ProductInquiry(
    val products: List<Product>
) : Serializable
