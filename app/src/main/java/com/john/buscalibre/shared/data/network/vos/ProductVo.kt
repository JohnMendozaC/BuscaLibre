package com.john.buscalibre.shared.data.network.vos

import com.google.gson.annotations.SerializedName

data class ProductVo(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("price") val price: Double?,
    @field:SerializedName("permalink") val permalink: String?,
    @field:SerializedName("category_id") val categoryId: String?,
    @field:SerializedName("thumbnail") val thumbnail: String?,
    @field:SerializedName("pictures") val picture: List<PictureVo>?
)
