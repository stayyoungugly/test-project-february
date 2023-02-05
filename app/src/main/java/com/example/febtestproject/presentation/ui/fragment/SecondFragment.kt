package com.example.febtestproject.presentation.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.febtestproject.R
import com.example.febtestproject.databinding.FragmentSecondBinding
import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.presentation.ui.rv.ReviewAdapter
import com.example.febtestproject.presentation.viewmodel.SecondFragmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.random.Random

private const val INTERVAL_MS = 100L
private const val TIMER_DURATION_MS = 3600200L

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private var thread: Thread? = null

    private val glide by lazy {
        Glide.with(this)
    }

    private val reviewAdapter by lazy {
        ReviewAdapter(glide)
    }

    private lateinit var firstCountDownTimer: CountDownTimer
    private lateinit var secondCountDownTimer: CountDownTimer

    private var firstDuration = 0L
    private var secondDuration = 0L

    private var timerDuration = TIMER_DURATION_MS

    private var firstStepSize = 0
    private var secondStepSize = 0

    private val firstProgressBar by lazy {
        binding.pbFirst
    }
    private val secondProgressBar by lazy {
        binding.pbSecond
    }
    private val loadingProgressBar get() = binding.pbLoadData

    private val firstProgress by lazy {
        binding.tvFirstProgress
    }
    private val secondProgress by lazy {
        binding.tvSecondProgress
    }

    private val loadProgress get() = binding.tvLoadProgress

    private val tvHour by lazy {
        binding.tvHourValue
    }

    private val tvMinute by lazy {
        binding.tvMinuteValue
    }

    private val tvSeconds by lazy {
        binding.tvSecondsValue
    }

    private var isFirstEnded = false
    private var isSecondEnded = false
    private var isLoadEnded = false
    private var wasFailed = false


    private val viewModel: SecondFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomizeValues()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRatings.adapter = reviewAdapter
        initObservers()
        getLocalReviews()
        asyncLoadingAnimation()
        loadReviews()
        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        startTimer(timerDuration)

        binding.btnRandom.setOnClickListener {
            if (!isFirstEnded) firstCountDownTimer.cancel()
            if (!isSecondEnded) secondCountDownTimer.cancel()
            randomizeValues()
            startProgress(firstProgressBar, firstProgress, firstDuration, 1)
            startProgress(secondProgressBar, secondProgress, secondDuration, 2)
            isFirstEnded = false
            isSecondEnded = false
        }
    }

    private fun asyncLoadingAnimation() {
        lifecycleScope.launch {
            startLoading()
        }
    }

    private suspend fun startLoading() {
        isLoadEnded = false
        var a = 0
        loadingProgressBar.progress = 0
        loadProgress.text = getString(R.string.null_per)
        while (a < 100 && !isLoadEnded) {
            delay(1000L)
            a += 10
            loadingProgressBar.progress = a
            loadProgress.text = "$a%"
        }
        if (!wasFailed) {
            loadingProgressBar.progress = 100
            loadProgress.text = getString(R.string.done)
            binding.tvLoad.text = getString(R.string.finished)
        }
        if (wasFailed) (startLoading())
    }

    private fun startTimer(timerDuration: Long) {
        val endTime = getString(R.string.time_end)
        object : CountDownTimer(timerDuration, 1000) {

            override fun onTick(millisUntilFinishedValue: Long) {
                var millisUntilFinished = millisUntilFinishedValue
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val elapsedHours = millisUntilFinished / hoursInMilli
                millisUntilFinished %= hoursInMilli
                val elapsedMinutes = millisUntilFinished / minutesInMilli
                millisUntilFinished %= minutesInMilli
                val elapsedSeconds = millisUntilFinished / secondsInMilli

                val hour = String.format("%02d", elapsedHours)
                val minute = String.format("%02d", elapsedMinutes)
                val seconds = String.format("%02d", elapsedSeconds)

                tvHour.text = hour
                tvMinute.text = minute
                tvSeconds.text = seconds
            }

            override fun onFinish() {
                tvHour.text = endTime
                tvMinute.text = endTime
                tvSeconds.text = endTime
            }
        }.start()
    }

    private fun randomizeValues() {
        val rand = Random(System.nanoTime())
        val firstValue = (5..25).random(rand)
        val secondValue = (5..25).random(rand)
        firstDuration = (firstValue * 1000).toLong()
        secondDuration = (secondValue * 1000).toLong()
        firstStepSize = 0
        secondStepSize = 0
    }

    private fun startProgress(
        progressBar: ProgressBar, progress: TextView, duration: Long, number: Int
    ) {

        var step: Int
        val stepValue = duration / INTERVAL_MS
        var i: Int = when (number) {
            1 -> firstStepSize
            else -> secondStepSize
        }
        val countDownTimer = object : CountDownTimer(duration, INTERVAL_MS) {
            override fun onTick(millisUntilFinished: Long) {
                i++
                when (number) {
                    1 -> {
                        firstDuration = millisUntilFinished
                        firstStepSize = i
                    }
                    2 -> {
                        secondDuration = millisUntilFinished
                        secondStepSize = i
                    }
                }
                step = (i * 100 / stepValue).toInt()
                if (step <= 100) {
                    progressBar.progress = step
                    progress.text = "$step%"
                }
            }

            override fun onFinish() {
                i++
                progressBar.progress = 100
                progress.text = getString(R.string.done)
                when (number) {
                    1 -> isFirstEnded = true
                    2 -> isSecondEnded = true
                }
            }
        }
        when (number) {
            1 -> {
                firstCountDownTimer = countDownTimer
            }
            2 -> {
                secondCountDownTimer = countDownTimer
            }
        }
        countDownTimer.start()
    }

    private fun onFail() {
        wasFailed = true
        binding.tvLoad.text = getString(R.string.try_again)
        loadReviews()
    }

    private fun initObservers() {
        viewModel.localReviews.observe(viewLifecycleOwner) { list ->
            list.fold(onSuccess = {
                setReviewInfo(it, false)
            }, onFailure = {
                Timber.e(it)
            })
        }
        viewModel.remoteReviews.observe(viewLifecycleOwner) { list ->
            list.fold(onSuccess = {
                setReviewInfo(it, true)
                viewModel.saveLocalReviews(it)
            }, onFailure = {
                onFail()
            })
        }
        viewModel.error.observe(viewLifecycleOwner) {
            println(it)
            Timber.e(it)
        }
    }

    private fun setReviewInfo(list: List<Review>, isRemote: Boolean) {
        if (list.isNotEmpty()) {
            with(binding) {
                if (tvEmpty.visibility == View.VISIBLE) {
                    tvEmpty.visibility = View.GONE
                    rvRatings.visibility = View.VISIBLE
                }
            }
            reviewAdapter.submitList(list.toMutableList())
        } else {
            with(binding) {
                tvEmpty.visibility = View.VISIBLE
                rvRatings.visibility = View.GONE
            }
        }

        if (isRemote) {
            isLoadEnded = true
            wasFailed = false
        }
    }

    private fun loadReviews() {
        viewModel.getRemoteReviews()
    }

    private fun getLocalReviews() {
        viewModel.getLocalReviews()
    }

    override fun onPause() {
        super.onPause()
        if (!isFirstEnded) firstCountDownTimer.cancel()
        if (!isSecondEnded) secondCountDownTimer.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstEnded) startProgress(firstProgressBar, firstProgress, firstDuration, 1)
        if (!isSecondEnded) startProgress(secondProgressBar, secondProgress, secondDuration, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        thread = null
    }
}
