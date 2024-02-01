package com.github.diegopap.repetitivetasksapp.domain.util

sealed class TaskOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): TaskOrder(orderType)
    class Created(orderType: OrderType): TaskOrder(orderType)
    class Color(orderType: OrderType): TaskOrder(orderType)

    fun copy(orderType: OrderType): TaskOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Created -> Created(orderType)
            is Color -> Color(orderType)
        }
    }
}
