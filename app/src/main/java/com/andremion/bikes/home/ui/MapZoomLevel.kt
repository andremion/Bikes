package com.andremion.bikes.home.ui

enum class MapZoomLevel(private val level: Float) {

    CONTINENT(5f), CITY(10f), STREETS(15f), BUILDINGS(20f);

    fun inRange(min: Float, max: Float): Float {
        return if (level > min) {
            if (level > max) max else level
        } else min
    }
}