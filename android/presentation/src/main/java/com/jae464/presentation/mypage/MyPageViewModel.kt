package com.jae464.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    init {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            val userInfo = userRepository.getUserInfo()
            _uiState.update { state ->
                state.copy(
                    userInfo = userInfo
                )
            }
        }
    }


}