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

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.androiddevchallenge.JsonUtil
import com.example.androiddevchallenge.data.Result
import com.example.androiddevchallenge.data.WeatherApi
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.ui.components.AnimationDelays
import org.junit.Rule
import org.junit.Test

class WeatherOverviewScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun withData() {
        composeTestRule.setContent {
            WeatherOverviewScreen(
                WeatherOverviewViewModel(
                    MockWeatherApi(composeTestRule.activity.applicationContext, false),
                    AnimationDelays(loadingDelay = 0)
                ).apply { refresh() }
            )
        }

        composeTestRule.onNodeWithTag("weatherText").assertTextContains("Cloudy")
        composeTestRule.onNodeWithTag("weatherText")
            .assertContentDescriptionEquals("Weather forecast Cloudy")
    }

    @Test
    fun withError() {
        composeTestRule.setContent {
            WeatherOverviewScreen(
                WeatherOverviewViewModel(
                    MockWeatherApi(composeTestRule.activity.applicationContext, withError = true),
                    AnimationDelays(loadingDelay = 0)
                ).apply { refresh() }
            )
        }

        composeTestRule.onNodeWithTag("error")
            .assertTextContains("Oh no, it looks like bad weather on the server")
    }
}

class MockWeatherApi(private val context: Context, val withError: Boolean) : WeatherApi {
    override suspend fun getCurrentConditions(locationId: String): Result<WeatherData> {
        return if (withError) {
            Result.Error(
                "Oh no, it looks like bad weather on the server",
                Exception("Oh no")
            )
        } else {
            Result.Success(
                JsonUtil.getJson<WeatherData>(
                    context.applicationContext,
                    "weather_data.json"
                )
            )
        }
    }
}
