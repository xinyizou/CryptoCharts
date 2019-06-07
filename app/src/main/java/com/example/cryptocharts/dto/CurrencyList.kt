package com.example.cryptocharts.dto

import com.example.cryptocharts.dto.CurrencyNameDTO
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.jvm.internal.MagicApiIntrinsics

data class CurrencyList(val Data: Map<String, CurrencyNameDTO>)

data class CurrencyValue(val cad: Double)
