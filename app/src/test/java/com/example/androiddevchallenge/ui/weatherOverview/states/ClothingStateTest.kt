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

import com.example.androiddevchallenge.ui.weatherOverview.Cloth
import com.example.androiddevchallenge.ui.weatherOverview.ClothingState
import org.junit.Assert
import org.junit.Test

class ClothingStateTest {

    @Test
    fun sunState() {
        val subject = ClothingState.from(WeatherState.Sun())

        Assert.assertEquals(Cloth.Cap, subject.head)
        Assert.assertEquals(Cloth.Happy, subject.face)
        Assert.assertEquals(Cloth.Singlet, subject.body)
        Assert.assertEquals(Cloth.Shorts, subject.bottom)
        Assert.assertEquals("Shorts, T-shirt and don't forget the hat", subject.description)
    }

    @Test
    fun snowState() {
        val subject = ClothingState.from(WeatherState.Snow())

        Assert.assertEquals(Cloth.Hat, subject.head)
        Assert.assertEquals(Cloth.Happy, subject.face)
        Assert.assertEquals(Cloth.Sweater, subject.body)
        Assert.assertEquals(Cloth.Pants, subject.bottom)
        Assert.assertEquals("Long pants, sweater and don't forget the warm beanie", subject.description)
    }

    @Test
    fun rainState() {
        val subject = ClothingState.from(WeatherState.Rainy())

        Assert.assertEquals(Cloth.Cap, subject.head)
        Assert.assertEquals(Cloth.Surprised, subject.face)
        Assert.assertEquals(Cloth.TShirt, subject.body)
        Assert.assertEquals(Cloth.Shorts, subject.bottom)
        Assert.assertEquals("Warm cloth, maybe take your umbrella", subject.description)
    }
}
