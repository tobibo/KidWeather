/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.weatherOverview.states

import org.junit.Assert
import org.junit.Test

class WeatherStateTest {

    @Test
    fun sunny() {
        listOf(1, 2, 3, 4, 33, 34).forEach {
            val subjet = WeatherState.from(it)
            Assert.assertTrue(subjet is WeatherState.Sun)
        }
    }

    @Test
    fun cloudy() {
        listOf(6, 7, 8, 11, 43, 35, 36, 37, 38).forEach {
            val subjet = WeatherState.from(it)
            Assert.assertTrue(subjet is WeatherState.Cloudy)
        }
    }

    @Test
    fun rainy() {
        listOf(12, 13, 14, 15, 16, 17, 18, 39, 41, 42).forEach {
            val subjet = WeatherState.from(it)
            Assert.assertTrue(subjet is WeatherState.Rainy)
        }
    }

    @Test
    fun snow() {
        listOf(22, 44).forEach {
            val subjet = WeatherState.from(it)
            Assert.assertTrue(subjet is WeatherState.Snow)
        }
    }
}
