package com.example.febtestproject.presentation.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.febtestproject.R
import com.example.febtestproject.databinding.FragmentMainBinding
import com.example.febtestproject.presentation.ui.dialog.CustomAlert

private const val PROGRESS_DURATION_MS = 14000L
private const val INTERVAL_MS = 100L
private const val MAX_PROGRESS = 100

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var countDownTimer: CountDownTimer

    private var duration = PROGRESS_DURATION_MS

    private var stepSize = 0

    private var progressFinished = false

    private var isPaused = false

    private val progressBar get() = binding.pbLoading

    private val progress get() = binding.tvProgress

    private val animationLottie get() = binding.animLottie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            var isHidden = false
            btnStart.setOnClickListener {
                if (!animationLottie.isAnimating) {
                    if (isPaused) {
                        animationLottie.resumeAnimation()
                        isPaused = false
                    } else animationLottie.playAnimation()
                }
            }
            btnStop.setOnClickListener {
                if (animationLottie.isAnimating) {
                    animationLottie.pauseAnimation()
                    isPaused = true
                }
            }
            btnHide.setOnClickListener {
                if (isHidden) {
                    animationLottie.visibility = View.VISIBLE
                    isHidden = false
                } else {
                    animationLottie.visibility = View.GONE
                    isHidden = true
                }
            }
            btnAlert.setOnClickListener {
                showDialog()
            }

            btnNavigate.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
            }
        }
    }

    private fun showDialog() {
        val dialog = CustomAlert(activity = requireActivity())
        dialog.show()
    }

    private fun startProgress() {
        var i = stepSize
        var step: Int
        val stepValue = PROGRESS_DURATION_MS / INTERVAL_MS
        countDownTimer = object : CountDownTimer(
            duration, INTERVAL_MS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                i++
                stepSize = i
                duration = millisUntilFinished
                step = (i * MAX_PROGRESS / stepValue).toInt()
                progressBar.progress = step
                progress.text = "$step%"
            }

            override fun onFinish() {
                i++
                progressBar.progress = MAX_PROGRESS
                progress.text = getString(R.string.done)
                progressFinished = true
            }
        }
        countDownTimer.start()
    }

    override fun onPause() {
        super.onPause()
        if (!progressFinished) countDownTimer.cancel()
        if (animationLottie.isAnimating) {
            animationLottie.pauseAnimation()
            isPaused = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!progressFinished) startProgress()
        if (isPaused) animationLottie.resumeAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
