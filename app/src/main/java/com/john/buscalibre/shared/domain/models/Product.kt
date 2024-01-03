package com.john.buscalibre.shared.domain.models

import java.io.Serializable

data class Product(
    val id: String = "",
    val title: String? = "",
    val price: Double? = 0.0,
    val permalink: String? = "",
    val categoryId: String? = "",
    val thumbnail: String? = "",
    val picture: String? = ""
) : Serializable
