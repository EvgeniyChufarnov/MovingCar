package com.github.evgeniychufarnov.movingcar.domain

import android.graphics.Point
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.max

const val MAX_DISTANCE_TIME = 2000f

class CarMovementCalculator : ICarMovementCalculator {

    private val _nextDestination = MutableLiveData<CarDestinationInfo?>()
    override val nextDestination: LiveData<CarDestinationInfo?> = _nextDestination

    private var currentPath: Queue<Point>? = null
    private var previousDot: Point? = null

    private var speed: Float? = null

    override fun start(path: List<Point>, width: Int, height: Int) {
        speed = max(width, height) / MAX_DISTANCE_TIME
        currentPath = LinkedList(path)
        previousDot = currentPath?.poll()
        goToNext()
    }

    override fun onDestinationReached(onFinish: () -> Unit) {
        goToNext(onFinish)
    }

    private fun goToNext(onFinish: (() -> Unit)? = null) {
        currentPath?.poll().let {
            it?.let {
                _nextDestination.value = CarDestinationInfo(
                    it.x,
                    it.y,
                    calculateAngle(it),
                    calculateDuration(it)
                )
            }

            previousDot = it

            if (it == null) {
                onFinish?.invoke()
            }
        }
    }

    private fun calculateAngle(next: Point): Float {
        return previousDot?.let { previous ->
            return Math.toDegrees(
                atan2((next.y - previous.y).toDouble(), (next.x - previous.x).toDouble())
            ).toFloat()
        } ?: 0f
    }

    private fun calculateDuration(next: Point): Long {
        return previousDot?.let { previous ->
            speed?.let {
                (hypot(
                    abs((next.x - previous.x)).toDouble(),
                    abs((next.y - previous.y)).toDouble()
                ) / it).toLong()
            } ?: 100L
        } ?: 100L
    }

    override fun clear() {
        _nextDestination.value = null
        currentPath = null
        previousDot = null
        speed = null
    }
}