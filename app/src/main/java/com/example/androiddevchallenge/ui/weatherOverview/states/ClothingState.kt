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
package com.example.androiddevchallenge.ui.weatherOverview

import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.weatherOverview.states.WeatherState

enum class Cloth(val resource: Int) {
    Cap(R.drawable.ic_cap),
    Hat(R.drawable.ic_winter_hat),
    Happy(R.drawable.ic_happy),
    Surprised(R.drawable.ic_surprised),
    Singlet(R.drawable.ic_singlet),
    TShirt(R.drawable.ic_tshirt),
    Sweater(R.drawable.ic_sweatshirt),
    Shorts(R.drawable.ic_shorts),
    Pants(R.drawable.ic_long_pants),
}
data class ClothingState(
    val head: Cloth = Cloth.Cap,
    val face: Cloth = Cloth.Happy,
    val body: Cloth = Cloth.TShirt,
    val bottom: Cloth = Cloth.Shorts,
    val description: String = "",
) {
    companion object {
        fun default() = ClothingState(
            Cloth.Hat,
            Cloth.Happy,
            Cloth.Sweater,
            Cloth.Pants
        )

        fun from(weatherState: WeatherState): ClothingState {
            return ClothingState(
                head = when (weatherState) {
                    is WeatherState.Sun -> {
                        Cloth.Cap
                    }
                    is WeatherState.Snow -> {
                        Cloth.Hat
                    }
                    else -> {
                        Cloth.Cap
                    }
                },
                face = when (weatherState) {
                    is WeatherState.Sun -> {
                        Cloth.Happy
                    }
                    is WeatherState.Snow -> {
                        Cloth.Happy
                    }
                    else -> {
                        Cloth.Surprised
                    }
                },
                body = when (weatherState) {
                    is WeatherState.Sun -> {
                        Cloth.Singlet
                    }
                    is WeatherState.Snow -> {
                        Cloth.Sweater
                    }
                    else -> {
                        Cloth.TShirt
                    }
                },

                bottom = when (weatherState) {
                    is WeatherState.Sun -> {
                        Cloth.Shorts
                    }
                    is WeatherState.Snow -> {
                        Cloth.Pants
                    }
                    else -> {
                        Cloth.Shorts
                    }
                },
                description = when (weatherState) {
                    is WeatherState.Sun -> {
                        "Shorts, T-shirt and don't forget the hat"
                    }
                    is WeatherState.Snow -> {
                        "Long pants, sweater and don't forget the warm beanie"
                    }
                    else -> {
                        "Warm cloth, maybe take your umbrella"
                    }
                }
            )
        }
    }
}
