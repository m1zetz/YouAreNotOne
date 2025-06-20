package com.example.youarenotalone.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.drawTopBorder(color: Color, strokeWidth: Dp = 1.dp): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidthPx = strokeWidth.toPx()
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidthPx
        )
    }
)



fun Modifier.drawBottomBorder(color: Color, strokeWidth: Dp = 1.dp): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidthPx = strokeWidth.toPx()
        val y = size.height - strokeWidthPx / 2
        drawLine(
            color = color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidthPx
        )
    }
)