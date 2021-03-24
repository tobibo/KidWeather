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

import androidx.annotation.DrawableRes
import com.example.androiddevchallenge.R

sealed class WeatherState(@DrawableRes val icon: Int) {
    class Sun : WeatherState(R.drawable.ic_sun)
    class Snow : WeatherState(R.drawable.ic_snow)
    class Cloudy : WeatherState(R.drawable.ic_cloudy)
    class PartlyCloudy : WeatherState(R.drawable.ic_cloudy_sun)
    class Rainy : WeatherState(R.drawable.ic_rain)

    companion object {
        fun from(weatherIcon: Int): WeatherState {
            return when (weatherIcon) {
                1, 2, 3, 4, 33, 34 -> {
                    Sun()
                }
                6, 7, 8, 11, 43, 35, 36, 37, 38 -> {
                    Cloudy()
                }
                12, 13, 14, 15, 16, 17, 18, 39, 41, 42 -> {
                    Rainy()
                }
                22, 44 -> {
                    Snow()
                }
                else -> {
                    Sun()
                }
            }
        }
    }
}
