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

import com.example.androiddevchallenge.data.WeatherData

data class TemperatureState(
    val temperature: String = "",
    val lowTemperature: String = "",
    val highTemperature: String = "",
    val uv: String = "",
    val windSpeed: String = ""
) {
    companion object {
        fun from(weatherData: WeatherData): TemperatureState {
            val past6HourRange = weatherData.TemperatureSummary.Past6HourRange
            val lowTemperature =
                "${past6HourRange.Minimum.Metric.Value} ${past6HourRange.Minimum.Metric.Unit}"
            val highTemperature =
                "${past6HourRange.Maximum.Metric.Value} ${past6HourRange.Maximum.Metric.Unit}"
            return TemperatureState(
                "${weatherData.Temperature.Metric.Value} ${weatherData.Temperature.Metric.Unit}",
                lowTemperature,
                highTemperature,
                weatherData.UVIndexText,
                "${weatherData.Wind.Metric.Value} ${weatherData.Wind.Metric.Unit}",
            )
        }
    }
}
