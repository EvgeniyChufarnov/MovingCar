package com.github.evgeniychufarnov.movingcar.di

import com.github.evgeniychufarnov.movingcar.domain.CarMovementCalculator
import com.github.evgeniychufarnov.movingcar.domain.ICarMovementCalculator
import com.github.evgeniychufarnov.movingcar.ui.MainActivityVm
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<ICarMovementCalculator> { CarMovementCalculator() }
    viewModel { MainActivityVm(get()) }
}