package com.jae464.presentation.mypage.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<SettingEvent>()
    val event = _event.asSharedFlow()

    fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.SetNotificationEnabled -> {
                viewModelScope.launch {
                    settingRepository.setNotificationSetting(intent.enabled)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            settingRepository.getNotificationSetting().collectLatest { enabled ->
                _uiState.update { state -> state.copy(notificationEnabled = enabled) }
            }
        }
    }

}