package br.com.elitma.remote

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    val code: String,
    val name: String,
    val precision: Int,
    @SerializedName("_type") val type: String
)