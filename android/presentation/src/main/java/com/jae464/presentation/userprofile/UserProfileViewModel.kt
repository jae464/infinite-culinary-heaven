package com.jae464.presentation.userprofile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jae464.domain.model.FollowStatus
import com.jae464.domain.repository.UserRepository
import com.jae464.presentation.main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<Route.UserProfile>().userId

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<UserProfileEvent>()
    val event = _event.asSharedFlow()

    init {
        getMyUserId()
        fetchUserInfo()
        getFollowStatus()
    }

    fun handleIntent(intent: UserProfileIntent) {
        when (intent) {
            is UserProfileIntent.FollowUser -> followUser()
            is UserProfileIntent.UnfollowUser -> unfollowUser()
        }
    }

    private fun getMyUserId() {
        viewModelScope.launch {
            userRepository.getMyUserId()
                .onSuccess { myUserId ->
                    Log.d("UserProfileViewModel", "getMyUserId: $myUserId")
                    _uiState.value = _uiState.value.copy(isMe = myUserId == userId.toString())
                }
                .onFailure {

                }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo(userId)
                .onSuccess { userInfo ->
                    _uiState.value = _uiState.value.copy(userInfo = userInfo)
                }
                .onFailure {
                    _event.emit(UserProfileEvent.FetchUserInfoFailed)
                }
        }
    }

    private fun getFollowStatus() {
        viewModelScope.launch {
            userRepository.getFollowStatus(userId)
                .onSuccess { followStatus ->
                    Log.d("UserProfileViewModel", "getFollowStatus: $followStatus")
                    _uiState.value = _uiState.value.copy(isFollowing = followStatus == FollowStatus.FOLLOWING)
                }
                .onFailure {
                    Log.d("UserProfileViewModel", "${it.message}")
                }

        }
    }

    private fun followUser() {
        viewModelScope.launch {
            userRepository.followUser(userId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isFollowing = true)
                }
                .onFailure {

                }
        }
    }

    private fun unfollowUser() {
        viewModelScope.launch {
            userRepository.unfollowUser(userId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isFollowing = false)
                }
                .onFailure {

                }
        }
    }
}