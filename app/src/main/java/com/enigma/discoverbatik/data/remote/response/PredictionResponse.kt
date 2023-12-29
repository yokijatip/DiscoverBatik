package com.enigma.discoverbatik.data.remote.response

data class PredictionResponse(
    val data: PredictionData,
    val status: PredictionStatus
)

data class PredictionData(
    val batik_prediction: String,
    val confidence: Double
)

data class PredictionStatus(
    val code: Int,
    val message: String
)