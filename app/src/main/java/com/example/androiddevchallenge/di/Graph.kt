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
package com.example.androiddevchallenge.di

import android.content.Context
import com.example.androiddevchallenge.data.WeatherApiImpl
import com.example.androiddevchallenge.ui.components.AnimationDelays
import com.example.androiddevchallenge.ui.weatherOverview.WeatherOverviewViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

interface Graph {
    val weatherOverViewViewModel: WeatherOverviewViewModel
    fun provide(context: Context)
}
object GraphImpl : Graph {
    private lateinit var okHttpClient: OkHttpClient

    private val weatherApi by lazy {
        WeatherApiImpl(okHttpClient)
    }

    override val weatherOverViewViewModel by lazy {
        WeatherOverviewViewModel(weatherApi, AnimationDelays())
    }

    override fun provide(context: Context) {
        okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http_cache"), 20 * 1024 * 1024))
            .apply {
                // if (Build.DEBUG) eventListenerFactory(LoggingEventListener.Factory())
            }
            .build()
    }
}
