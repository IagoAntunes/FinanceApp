package com.iagoaf.appfinancas.src.features.home.domain.model

data class ReleaseModel(
    var id: String = "",
    val date: String = "",
    val positive: Boolean = false,
    val title: String = "",
    val value: String = ""
)