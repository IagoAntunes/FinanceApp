package com.iagoaf.appfinancas.src.features.home.domain.model

data class ReleaseModel(
    var id: String = "",
    val category: String = "",
    val date: String = "",
    val isPositive: Boolean = false,
    val title: String = "",
    val value: String = ""
)