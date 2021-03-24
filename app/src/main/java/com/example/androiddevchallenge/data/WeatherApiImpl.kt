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

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherApiImpl(okHttpClient: OkHttpClient) : WeatherApi {
    // The base URL where our API is
    private val BASE_URL = "https://dataservice.accuweather.com/"
    private val apiKey = "5GZTUkzhRhyEIenjWZkMxBpFEsfuX8iV"
/* Moshi Makes it easy to parse JSON into objects
you can use GSON instead if you want*/

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    override suspend fun getCurrentConditions(locationId: String): Result<WeatherData> {
        return try {
            val response = retrofitService.getCurrentConditions(locationId, apiKey)
            val result: WeatherData? = response.body()?.first()
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error("Server error", Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Result.Error("Network Error", e)
        }
    }
}

interface WeatherApi {
    suspend fun getCurrentConditions(locationId: String): Result<WeatherData>
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Error(val msg: String, val cause: Exception? = null) : Result<Nothing>()
}
