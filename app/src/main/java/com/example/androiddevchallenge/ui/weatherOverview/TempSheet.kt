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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.secondary
import com.example.androiddevchallenge.ui.weatherOverview.states.TemperatureState
import com.example.owl.ui.utils.lerp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

@Preview("TempSheet")
@Composable
fun TempSheetPreview() {
    MyTheme() {
        TempSheet(
            TemperatureState("24", "15", "27", "Low"),
            openFraction = 1f,
            height = 400.dp.value
        ) {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TempSheet(
    temperatureState: TemperatureState,
    openFraction: Float,
    height: Float,
    toggleOpenClose: () -> Unit
) {
    val sheetSize = with(LocalDensity.current) { TempSheepPeek.toPx() }
    val offsetY = lerp(height - sheetSize, 0f, openFraction)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = height.toInt().dp)
            .graphicsLayer {
                translationX = 0f
                translationY = offsetY
            },
        shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        backgroundColor = secondary

    ) {

        Column(
            modifier = Modifier
                .width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                val iconSheet = if (openFraction == 1f) {
                    R.drawable.ic_down
                } else {
                    R.drawable.ic_up
                }
                val descriptionSheetToggle = if (openFraction == 1f) {
                    "Swipe down (or click) for to hide details"
                } else {
                    "Swipe up (or click) for more temp details"
                }
                Image(
                    painter = painterResource(id = iconSheet),
                    contentDescription = descriptionSheetToggle,
                    Modifier
                        .size(60.dp)
                        .clickable {
                            toggleOpenClose()
                        }
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Image(
                    painter = painterResource(id = R.drawable.ic_thermometer),
                    contentDescription = "Temperature is",
                    Modifier
                        .size(50.dp)
                        .padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        temperatureState.temperature,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                    )
                    Text(
                        " ${temperatureState.lowTemperature} to ${temperatureState.lowTemperature}",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
            }

            Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sun_cream),
                    contentDescription = "UV is",
                    Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    temperatureState.uv,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Row(Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Image(
                    painter = painterResource(id = R.drawable.ic_wind),
                    contentDescription = "Wind is",
                    Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    temperatureState.windSpeed,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

fun LazyListState.disableScrolling(scope: CoroutineScope) {
    scope.launch {
        scroll(scrollPriority = MutatePriority.PreventUserInput) {
            // Await indefinitely, blocking scrolls
            awaitCancellation()
        }
    }
}

fun LazyListState.reenableScrolling(scope: CoroutineScope) {
    scope.launch {
        scroll(scrollPriority = MutatePriority.PreventUserInput) {
            // Do nothing, just cancel the previous indefinite "scroll"
        }
    }
}
