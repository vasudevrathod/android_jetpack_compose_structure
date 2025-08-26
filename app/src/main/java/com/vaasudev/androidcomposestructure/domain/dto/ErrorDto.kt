package com.vaasudev.androidcomposestructure.domain.dto

data class ErrorDto(
    val statusCode: Int = 500,
    val message: String = "Something went wrong",
)
