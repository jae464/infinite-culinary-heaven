package com.jae464.presentation.userprofile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jae464.domain.repository.UserRepository
import com.jae464.presentation.main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId = savedStateHandle.toRoute<Route.UserProfile>().userId

    init {
        viewModelScope.launch {
            val userInfo = userRepository.getUserInfo(userId)
            Log.d("UserProfileViewModel", "$userInfo")
        }
    }
}