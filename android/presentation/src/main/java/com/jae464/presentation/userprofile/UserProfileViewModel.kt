package com.jae464.presentation.userprofile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jae464.domain.model.FollowStatus
import com.jae464.domain.repository.RecipeRepository
import com.jae464.domain.repository.UserRepository
import com.jae464.presentation.main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<Route.UserProfile>().userId

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<UserProfileEvent>()
    val event = _event.asSharedFlow()

    private var currentPage = 0
    private var isLastPage = false

    init {
        getMyUserId()
        fetchUserInfo()
        getFollowStatus()
        fetchRecipePreviews()
    }

    fun handleIntent(intent: UserProfileIntent) {
        when (intent) {
            is UserProfileIntent.FollowUser -> followUser()
            is UserProfileIntent.UnfollowUser -> unfollowUser()
            is UserProfileIntent.FetchRecipePreviews -> fetchRecipePreviews()
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
                    _uiState.value = _uiState.value.copy(isFollowing = followStatus == FollowStatus.FOLLOWING)
                }
                .onFailure {
                    Log.d("UserProfileViewModel", "${it.message}")
                }

        }
    }

    private fun fetchRecipePreviews() {
        if (isLastPage || uiState.value.isLoading) return
        _uiState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch {
            recipeRepository.getRecipePreviewsByUserId(page = currentPage, userId = userId)
                .onSuccess { recipePreviews ->
                    _uiState.update { state -> state.copy(recipePreviews = state.recipePreviews + recipePreviews, isLoading = false) }
                    if (recipePreviews.isEmpty()) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }
                }
                .onFailure {
                    Log.e("UserProfileViewModel", "${it.message}")
                    _uiState.update { state -> state.copy(isLoading = false) }
                }
        }
    }

    private fun followUser() {
        viewModelScope.launch {
            userRepository.followUser(userId)
                .onSuccess {
                    val followerCount = uiState.value.userInfo?.followerCount ?: 0
                    _uiState.value = _uiState.value.copy(
                        isFollowing = true,
                        userInfo = _uiState.value.userInfo?.copy(followerCount = followerCount + 1)
                    )
                }
                .onFailure {

                }
        }
    }

    private fun unfollowUser() {
        viewModelScope.launch {
            userRepository.unfollowUser(userId)
                .onSuccess {
                    val followerCount = uiState.value.userInfo?.followerCount ?: 0
                    _uiState.value = _uiState.value.copy(isFollowing = false, userInfo = _uiState.value.userInfo?.copy(followerCount = followerCount - 1))
                }
                .onFailure {

                }
        }
    }
}