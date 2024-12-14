package com.jae464.presentation.mypage

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEditEvent>()
    val event: SharedFlow<ProfileEditEvent> = _event.asSharedFlow()

    fun handleIntent(intent: ProfileEditIntent) {
        when (intent) {
            is ProfileEditIntent.UpdateNickname -> {
                _uiState.update { state -> state.copy(nickname = intent.nickname, isNicknameChanged = true) }
            }

            is ProfileEditIntent.UpdateProfileImage -> {
                _uiState.update { state -> state.copy(profileImageUrl = intent.profileImageUrl, isProfileImageChanged = true) }
            }

            is ProfileEditIntent.UpdateProfile -> {
                if (uiState.value.isNicknameChanged || uiState.value.isProfileImageChanged) {
                    viewModelScope.launch {
                        val file: File? = if (uiState.value.profileImageUrl != null) convertToFile(Uri.parse(uiState.value.profileImageUrl)) else null
                        userRepository.updateProfile(uiState.value.nickname, file)
                            .onSuccess {
                                _event.emit(ProfileEditEvent.UpdateProfileSuccess)
                            }
                            .onFailure {
                                Log.d("ProfileEditViewModel", "updateProfile: ${it.message}")
                            }
                    }
                }

            }
        }

    }


    init {
        val nickname = savedStateHandle.get<String>("nickname") ?: ""
        val profileImageUrl = savedStateHandle.get<String>("profileImageUrl")

        Log.d("ProfileEditViewModel", "nickname: $nickname profileImageUrl: $profileImageUrl")

        _uiState.update { state -> state.copy(nickname = nickname, profileImageUrl = profileImageUrl) }
    }

    private fun convertToFile(uri: Uri): File? {
        val fileName = getFileName(uri) ?: return null
        val file = File(context.cacheDir, fileName)
        file.createNewFile()

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file
    }

    private fun getFileName(uri: Uri): String? {
        val contentResolver = context.contentResolver
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            return cursor.getString(nameIndex)
        }
        return null
    }

}