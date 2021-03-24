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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview("Clothing")
@Composable
fun ClothingPreview() {
    Clothing(clothingState = ClothingState.default())
}

@Composable
fun Clothing(clothingState: ClothingState) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 10.dp, top = 10.dp)
            .semantics(true) { contentDescription = clothingState.description },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                id = clothingState.head.resource
            ),
            contentDescription = null,
            modifier = Modifier.weight(0.2f)
        )
        Image(
            painter = painterResource(
                id = clothingState.face.resource
            ),
            contentDescription = null,
            modifier = Modifier.weight(0.2f)
        )
        Image(
            painter = painterResource(
                id = clothingState.body.resource
            ),
            contentDescription = null,
            modifier = Modifier.weight(0.4f)
        )
        Image(
            painter = painterResource(
                id = clothingState.bottom.resource
            ),
            contentDescription = null,
            Modifier.weight(0.4f)
        )
    }
}
