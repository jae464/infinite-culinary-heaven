package com.jae464.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jae464.domain.repository.BookMarkRepository
import com.jae464.domain.repository.CommentRepository
import com.jae464.domain.repository.RecipeRepository
import com.jae464.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val bookMarkRepository: BookMarkRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RecipeDetailEvent>()
    val event: SharedFlow<RecipeDetailEvent> = _event.asSharedFlow()

    private var currentEditCommentId: Long? = null

    init {
        getMyInfo()
    }

    fun handleIntent(intent: RecipeDetailIntent) {
        when (intent) {
            is RecipeDetailIntent.FetchRecipe -> fetchRecipe(intent.recipeId)
            is RecipeDetailIntent.DeleteRecipe -> deleteRecipe(intent.recipeId)
            is RecipeDetailIntent.AddBookMark -> addBookMark(intent.recipeId)
            is RecipeDetailIntent.DeleteBookMark -> deleteBookMark(intent.recipeId)
            is RecipeDetailIntent.LikeRecipe -> likeRecipe(intent.recipeId)
            is RecipeDetailIntent.UnlikeRecipe -> unlikeRecipe(intent.recipeId)
            is RecipeDetailIntent.AddComment -> addComment(intent.recipeId, intent.content)
            is RecipeDetailIntent.DeleteComment -> deleteComment(intent.recipeId, intent.commentId)
            is RecipeDetailIntent.UpdateCommentInput -> updateCommentInput(intent.content)
            is RecipeDetailIntent.FetchComments -> fetchComments(intent.recipeId)
            is RecipeDetailIntent.UpdateComment -> updateComment(intent.recipeId, intent.commentId, intent.content)
            is RecipeDetailIntent.SetCommentEditMode -> {
                currentEditCommentId = intent.commentId
                _uiState.update { state -> state.copy(commentEditMode = true) }
            }
            is RecipeDetailIntent.ClearCommentEditMode -> {
                currentEditCommentId = null
                _uiState.update { state -> state.copy(commentEditMode = false, commentInput = "") }
            }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess {
                    _uiState.update { state -> state.copy(myInfo = it) }
                }
                .onFailure {

                }
        }
    }

    private fun fetchRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.getRecipeById(recipeId)
                .onSuccess { recipe ->
                    Log.d("RecipeDetailViewModel", "Recipe: $recipe")
                    _uiState.update { state -> state.copy(recipe = recipe) }
                }
        }
    }

    private fun deleteRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeById(recipeId)
                .onSuccess {
                    _event.emit(RecipeDetailEvent.DeleteSuccess)
                }
        }
    }

    private fun addBookMark(recipeId: Long) {
        viewModelScope.launch {
            bookMarkRepository.addBookMark(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(recipe = state.recipe?.copy(isBookMarked = true)) }
                    _event.emit(RecipeDetailEvent.AddBookMarkSuccess)
                }
                .onFailure {

                }
        }
    }

    private fun deleteBookMark(recipeId: Long) {
        viewModelScope.launch {
            bookMarkRepository.deleteBookMark(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(recipe = state.recipe?.copy(isBookMarked = false)) }
                    _event.emit(RecipeDetailEvent.DeleteBookMarkSuccess)
                }
                .onFailure {

                }
        }

    }

    private fun likeRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.likeRecipe(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(recipe = state.recipe?.copy(isLiked = true)) }
                    _event.emit(RecipeDetailEvent.LikeSuccess)
                }
        }
    }

    private fun unlikeRecipe(recipeId: Long) {
        viewModelScope.launch {
            recipeRepository.unlikeRecipe(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(recipe = state.recipe?.copy(isLiked = false)) }
                    _event.emit(RecipeDetailEvent.UnlikeSuccess)
                }
        }
    }

    private fun fetchComments(recipeId: Long) {
        viewModelScope.launch {
            commentRepository.getCommentsByRecipeId(recipeId)
                .onSuccess {
                    _uiState.update { state -> state.copy(comments = it) }
                }
        }
    }

    private fun addComment(recipeId: Long, content: String) {
        currentEditCommentId?.let {
            updateComment(recipeId, it, content)
            return
        }

        viewModelScope.launch {
            commentRepository.addComment(recipeId, content)
                .onSuccess {
                    _uiState.update { state -> state.copy(commentInput = "") }
                    fetchComments(recipeId)
                }
        }
    }

    private fun updateCommentInput(content: String) {
        _uiState.update { state -> state.copy(commentInput = content) }
    }

    private fun updateComment(recipeId: Long, commentId: Long, content: String) {
        viewModelScope.launch {
            commentRepository.updateComment(commentId, content)
                .onSuccess {
                    currentEditCommentId = null
                    _uiState.update { state -> state.copy(commentInput = "", commentEditMode = false) }
                    fetchComments(recipeId)
                }
        }
    }

    private fun deleteComment(recipeId: Long, commentId: Long) {
        viewModelScope.launch {
            commentRepository.deleteComment(commentId)
                .onSuccess {
                    fetchComments(recipeId)
                }
        }
    }



}