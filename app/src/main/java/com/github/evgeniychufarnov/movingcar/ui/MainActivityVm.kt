package com.github.evgeniychufarnov.movingcar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.evgeniychufarnov.movingcar.domain.CarDestinationInfo
import com.github.evgeniychufarnov.movingcar.domain.ICarMovementCalculator
import com.github.evgeniychufarnov.movingcar.domain.generateRandomPath

const val DOTS_NUM = 3

class MainActivityVm(
    private val carMovementCalculator: ICarMovementCalculator
) : ViewModel() {

    val nextDestination: LiveData<CarDestinationInfo?> = carMovementCalculator.nextDestination

    private var isRunning: Boolean = false
    private var isReverse: Boolean = false

    fun onCarClicked(width: Int, height: Int, carSize: Int) {
        if (!isRunning) {
            isRunning = true
            carMovementCalculator.start(
                generateRandomPath(width, height, carSize, DOTS_NUM, isReverse),
                width,
                height
            )

            isReverse = !isReverse
        }
    }

    fun onDestinationReached() {
        carMovementCalculator.onDestinationReached {
            isRunning = false
        }
    }

    fun onDestroy() {
        carMovementCalculator.clear()
        isRunning = false
        isReverse = false
    }
}