package com.example.tolbhav.tol_screen

data class TolState(
    val totalWeight: Double = 0.0,
    val totalAmount: Double = 0.0,
    val weightEntries: List<Double> = emptyList(),
    val currentWeight: Double = 0.0,
    val price: Double = 0.0
)
