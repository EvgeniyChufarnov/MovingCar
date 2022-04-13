package com.github.evgeniychufarnov.movingcar.domain

import android.graphics.Point
import kotlin.random.Random

fun generateRandomPath(
    width: Int,
    height: Int,
    carSize: Int,
    dotsNum: Int,
    isReverse: Boolean
): List<Point> {
    return mutableListOf<Point>().apply {
        add(
            if (!isReverse) {
                Point(0, 0)
            } else {
                Point(width - carSize, height - carSize)
            }
        )
        repeat(dotsNum) {
            add(
                Point(
                    Random.nextInt(carSize, width - carSize),
                    Random.nextInt(carSize, height - carSize)
                )
            )
        }
        add(
            if (isReverse) {
                Point(0, 0)
            } else {
                Point(width - carSize, height - carSize)
            }
        )
    }
}