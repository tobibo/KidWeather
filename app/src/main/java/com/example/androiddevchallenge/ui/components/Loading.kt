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
package com.example.androiddevchallenge.ui.components

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.gradient
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Preview("LOading")
@Composable
fun LoadingPreview() {
    MyTheme() {
        Loading(isLoading = false, modifier = Modifier.fillMaxSize())
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun Loading(isLoading: Boolean, modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    val sun = LocalContext.current.getDrawable(R.drawable.ic_sun)
    val cloudy = LocalContext.current.getDrawable(R.drawable.ic_cloudy)
    val rain = LocalContext.current.getDrawable(R.drawable.ic_rain)
    val snow = LocalContext.current.getDrawable(R.drawable.ic_snow)
    val progressValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    if (isLoading) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = gradient)),
            onDraw = {

                val imageSize = Size(60.dp.toPx(), 60.dp.toPx())

                drawDrawableOnCirclePath(progressValue, sun, imageSize, 1)
                drawDrawableOnCirclePath(progressValue, cloudy, imageSize, 2)
                drawDrawableOnCirclePath(progressValue, rain, imageSize, 3)
                drawDrawableOnCirclePath(progressValue, snow, imageSize, 4)
            }

        )
    }
}

fun DrawScope.drawDrawableOnCirclePath(
    progress: Float,
    drawable: Drawable?,
    imageSize: Size,
    inQuadrants: Int
) {
    val degrees = (progress.times(360) - 180 + (90 * inQuadrants)).toDouble()

    val progressImageSize = Size(
        (imageSize.width + (imageSize.width * 4)).toFloat(),
        (imageSize.width + (imageSize.height * 4)).toFloat()
    )

    val radian = Math.toRadians(degrees)
    val x = center.x + (size.width - imageSize.width * 3) / 2 * cos(radian)
    val y = center.y + (size.width - imageSize.height * 3) / 2 * sin(radian)
    drawable?.let {
        drawDrawable(
            it,
            Offset(x.toFloat() - imageSize.width, y.toFloat() - imageSize.height),
            progressImageSize
        )
    }
}

fun DrawScope.drawDrawable(drawable: Drawable, offset: Offset, size: Size) {
    translate(offset.x - size.width / 2, offset.y - size.height / 2) {
        drawIntoCanvas { canvas ->
            drawable.let {
                it.setBounds(
                    (size.width / 2).roundToInt(),
                    (size.height / 2).roundToInt(),
                    size.width.roundToInt(),
                    size.height.roundToInt()
                )
                it.draw(canvas.nativeCanvas)
            }
        }
    }
}
