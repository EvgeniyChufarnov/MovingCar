package com.github.evgeniychufarnov.movingcar.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.evgeniychufarnov.movingcar.databinding.ActivityMainBinding
import com.github.evgeniychufarnov.movingcar.domain.CarDestinationInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainActivityVm by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.nextDestination.observe(this) { info ->
            info?.let {
                animateCar(it)
            }
        }

        binding.ivAuto.setOnClickListener {
            vm.onCarClicked(
                binding.flContainer.width,
                binding.flContainer.height,
                binding.ivAuto.width,
            )
        }
    }

    private fun animateCar(next: CarDestinationInfo) {
        binding.ivAuto.rotation = next.rotation

        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(binding.ivAuto, "translationX", binding.ivAuto.x, next.x.toFloat()),
                ObjectAnimator.ofFloat(binding.ivAuto, "translationY", binding.ivAuto.y, next.y.toFloat()),
            )

            duration = next.duration

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vm.onDestinationReached()
                }
            })

            start()
        }
    }

    override fun onDestroy() {
        vm.onDestroy()
        super.onDestroy()
    }
}