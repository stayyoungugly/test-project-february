package com.example.febtestproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.febtestproject.domain.entity.Review
import com.example.febtestproject.domain.usecase.GetLocalReviewsUseCase
import com.example.febtestproject.domain.usecase.GetRemoteReviewsUseCase
import com.example.febtestproject.domain.usecase.SaveLocalReviewsUseCase
import kotlinx.coroutines.launch

class SecondFragmentViewModel(
    private val getRemoteReviewsUseCase: GetRemoteReviewsUseCase,
    private val getLocalReviewsUseCase: GetLocalReviewsUseCase,
    private val saveLocalReviewsUseCase: SaveLocalReviewsUseCase,
) :
    ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    private val _remoteReviews: MutableLiveData<Result<List<Review>>> = MutableLiveData()
    val remoteReviews: LiveData<Result<List<Review>>> = _remoteReviews

    private val _localReviews: MutableLiveData<Result<List<Review>>> = MutableLiveData()
    val localReviews: LiveData<Result<List<Review>>> = _localReviews

    fun getRemoteReviews() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val review = getRemoteReviewsUseCase()
                _remoteReviews.value = Result.success(review)
                _isLoading.value = false
            } catch (ex: Exception) {
                _remoteReviews.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getLocalReviews() {
        viewModelScope.launch {
            try {
                val review = getLocalReviewsUseCase()
                _localReviews.value = Result.success(review)
            } catch (ex: Exception) {
                _localReviews.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun saveLocalReviews(list: List<Review>) {
        viewModelScope.launch {
            try {
                saveLocalReviewsUseCase(list)
            } catch (ex: Exception) {
                _error.value = ex
            }
        }
    }
}
