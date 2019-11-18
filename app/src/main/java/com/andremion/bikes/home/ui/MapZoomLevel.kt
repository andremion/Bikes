/*
 * Copyright (c) 2019. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andremion.bikes.home.ui

enum class MapZoomLevel(private val level: Float) {

    CONTINENT(5f), CITY(10f), STREETS(15f), BUILDINGS(20f);

    fun inRange(min: Float, max: Float): Float {
        return if (level > min) {
            if (level > max) max else level
        } else min
    }

    fun inRange(level: Float) = level >= this.level
}
