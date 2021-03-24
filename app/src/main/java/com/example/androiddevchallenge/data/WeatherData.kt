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
package com.example.androiddevchallenge.data

data class WeatherData(
    val CloudCover: Int = 0,
    val EpochTime: Long = 0,
    val HasPrecipitation: Boolean = false,
    val IndoorRelativeHumidity: Int = 0,
    val IsDayTime: Boolean = false,
    val Link: String = "",
    val LocalObservationDateTime: String = "",
    val MobileLink: String = "",
    val Temperature: Temperature = Temperature(),
    val UVIndex: Int = 0,
    val UVIndexText: String = "",
    val RealFeelTemperature: Temperature = Temperature(),
    val RealFeelTemperatureShade: Temperature = Temperature(),
    val TemperatureSummary: TemperatureSummary = TemperatureSummary(
        Past6HourRange = Past6HourRange()
    ),
    val WeatherIcon: Int = 0,
    val WeatherText: String = "",
    val Wind: Speed = Speed()
)

data class Speed(val Metric: Metric = Metric(), val Imperial: Imperial = Imperial())

/**
 * "WindGust": {
"Speed": {
"Metric": {
"Value": 25.9,
"Unit": "km/h",
"UnitType": 7
},
"Imperial": {
"Value": 16.1,
"Unit": "mi/h",
"UnitType": 9
}
}
}
 */
data class TemperatureSummary(
    val Past6HourRange: Past6HourRange
)

data class PressureTendency(
    val Code: String = "",
    val LocalizedText: String = ""
)

data class Temperature(
    val Imperial: Imperial = Imperial(),
    val Metric: Metric = Metric()
)

data class Imperial(
    val Unit: String = "",
    val UnitType: Int = 0,
    val Value: Int = 0
)

data class Metric(
    val Unit: String = "",
    val UnitType: Int = 0,
    val Value: Double = 0.0
)

data class Past24HourRange(
    val Maximum: Maximum = Maximum(),
    val Minimum: Minimum = Minimum()
)

data class Past6HourRange(
    val Maximum: Maximum = Maximum(),
    val Minimum: Minimum = Minimum()
)

data class Maximum(
    val Imperial: Imperial = Imperial(),
    val Metric: Metric = Metric()
)

data class Minimum(
    val Imperial: Imperial = Imperial(),
    val Metric: Metric = Metric()
)
