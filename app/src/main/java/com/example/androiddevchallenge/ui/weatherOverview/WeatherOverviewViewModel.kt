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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Result
import com.example.androiddevchallenge.data.WeatherApi
import com.example.androiddevchallenge.data.WeatherData
import com.example.androiddevchallenge.ui.components.AnimationDelays
import com.example.androiddevchallenge.ui.weatherOverview.states.DateState
import com.example.androiddevchallenge.ui.weatherOverview.states.TemperatureState
import com.example.androiddevchallenge.ui.weatherOverview.states.WeatherState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherOverviewViewModel(
    private val weatherApi: WeatherApi,
    private val animationDelays: AnimationDelays
) : ViewModel() {
    val currentData = MutableLiveData<WeatherData>()
    val weatherText = map(currentData) {
        it.WeatherText
    }
    val weatherIcon = map(currentData) {
        WeatherState.from(it.WeatherIcon)
    }

    val clothingState = map(weatherIcon) {
        ClothingState.from(weatherIcon.value ?: WeatherState.Sun())
    }
    val temperatureState: LiveData<TemperatureState> = map(currentData) {
        currentData.value?.let {
            TemperatureState.from(it)
        }
    }

    val dayState = map(currentData) {
        DateState.from(it.LocalObservationDateTime)
    }

    val error = MutableLiveData("")
    val isLoading = MutableLiveData<Boolean>(false)

    fun refresh() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = weatherApi.getCurrentConditions("16077")
            delay(animationDelays.loadingDelay)
            when (result) {
                is Result.Success -> {
                    currentData.value = result.value
                    error.value = ""
                }
                is Result.Error -> {
                    error.value = result.msg
                }
            }

            isLoading.postValue(false)
        }
    }
}
