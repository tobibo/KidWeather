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

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Imperial
import com.example.androiddevchallenge.data.Metric
import com.example.androiddevchallenge.data.Past6HourRange
import com.example.androiddevchallenge.data.Temperature
import com.example.androiddevchallenge.data.TemperatureSummary
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.graph
import com.example.androiddevchallenge.ui.components.Loading
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.gradient
import com.example.androiddevchallenge.ui.weatherOverview.states.TemperatureState
import com.example.androiddevchallenge.ui.weatherOverview.states.WeatherState
import kotlinx.coroutines.launch

val TempSheepPeek = 130.dp

@Preview("WeatherOverview")
@Composable
fun WeatherOverviewScreenPreview() {
    MyTheme {
        WeatherOverviewScreen(
            (
                LocalContext.current.applicationContext as Application
                ).graph().weatherOverViewViewModel.apply {
                this.currentData.value = WeatherData(
                    0, 0, false,
                    1, true,
                    Temperature = Temperature(
                        Imperial("", 1, 85), Metric("", 2, 25.0)
                    ),
                    TemperatureSummary = TemperatureSummary(Past6HourRange())
                )
                isLoading.postValue(false)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherOverviewScreen(weatherOverviewViewModel: WeatherOverviewViewModel) {
    val weatherIcon by weatherOverviewViewModel.weatherIcon.observeAsState()
    val temperatureState by weatherOverviewViewModel.temperatureState.observeAsState(
        TemperatureState()
    )
    val weatherText by weatherOverviewViewModel.weatherText.observeAsState()
    val loading by weatherOverviewViewModel.isLoading.observeAsState(initial = true)
    val error by weatherOverviewViewModel.error.observeAsState()
    val clothingState by weatherOverviewViewModel.clothingState.observeAsState()
    val dayState by weatherOverviewViewModel.dayState.observeAsState()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(gradient))
                .alpha(
                    if (loading) {
                        0f
                    } else {
                        1f
                    }
                )
        ) {
            val sheetState = rememberSwipeableState(0)
            val initialSize = with(LocalDensity.current) { TempSheepPeek.toPx() }
            val dragRange = constraints.maxHeight - initialSize
            val scope = rememberCoroutineScope()
            Box(
                Modifier.swipeable(
                    state = sheetState,
                    anchors = mapOf(
                        0f to 0,
                        -dragRange to 1
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Vertical
                )
            ) {
                val openFraction = if (sheetState.offset.value.isNaN()) {
                    0f
                } else {
                    -sheetState.offset.value / dragRange
                }.coerceIn(0f, 1f)

                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxSize()
                        .padding(bottom = TempSheepPeek),
                    verticalArrangement = Arrangement.Top

                ) {

                    weatherIcon.let { weatherIconState ->
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = weatherIconState?.icon ?: WeatherState.Snow().icon
                                ),
                                contentDescription = null
                            )
                            Text(
                                text = weatherText ?: "",
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.onPrimary,
                                modifier = Modifier
                                    .testTag("weatherText")
                                    .semantics {
                                        contentDescription = "Weather forecast $weatherText"
                                    }
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = dayState?.day ?: "",
                                    style = MaterialTheme.typography.h5,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colors.onPrimary,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = dayState?.date ?: "",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colors.onPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Clothing(clothingState ?: ClothingState.default())
                }

                TempSheet(
                    temperatureState,
                    openFraction,
                    this@BoxWithConstraints.constraints.maxHeight.toFloat()
                ) {
                    scope.launch {
                        sheetState.animateTo(if (sheetState.currentValue == 0) 1 else 0)
                    }
                }
            }
        }
    }
    loading.let {
        Loading(isLoading = loading, modifier = Modifier)
    }
    error?.let {
        if (it.isNotBlank()) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.error,
                        shape = MaterialTheme.shapes.large
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    it,
                    Modifier.testTag("error"),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}
