package com.github.evgeniychufarnov.movingcar.domain

import android.graphics.Point
import androidx.lifecycle.LiveData

interface ICarMovementCalculator {

    val nextDestination: LiveData<CarDestinationInfo?>

    fun start(path: List<Point>, width: Int, height: Int)
    fun onDestinationReached(onFinish: () -> Unit)
    fun clear()
}