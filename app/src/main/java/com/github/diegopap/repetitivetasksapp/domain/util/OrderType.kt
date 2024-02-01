package com.github.diegopap.repetitivetasksapp.domain.util

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}
