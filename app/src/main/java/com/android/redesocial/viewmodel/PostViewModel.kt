package com.android.redesocial.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.redesocial.data.cloud.FirestoreRepository
import com.android.redesocial.data.cloud.Post // Importe seu modelo de dados Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModelFactory(
    private val authViewModel: AuthViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(authViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class PostViewModel(
    private val authViewModel: AuthViewModel,
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _postsList = MutableStateFlow<List<Post>>(emptyList())
    val postsList: StateFlow<List<Post>> = _postsList.asStateFlow()

    private val _postFeedback = MutableStateFlow<String?>(null)
    val postFeedback: StateFlow<String?> = _postFeedback.asStateFlow()

    private val _loading = MutableStateFlow(false) // Loading do Feed/Postagem
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val TAG = "PostViewModel"

    private val _userProfilePosts = MutableStateFlow<List<Post>>(emptyList())
    val userProfilePosts: StateFlow<List<Post>> = _userProfilePosts.asStateFlow()

    private val _userPostCount = MutableStateFlow(0)
    val userPostCount: StateFlow<Int> = _userPostCount.asStateFlow()

    private val _userLastPostDate = MutableStateFlow<Long?>(null)
    val userLastPostDate: StateFlow<Long?> = _userLastPostDate.asStateFlow()

    private val _profileLoading = MutableStateFlow(false)


    fun onTextChanged(newText: String) {
        _text.value = newText
    }

    fun publishNewPost() {
        viewModelScope.launch {
            _loading.value = true
            _postFeedback.value = null

            val uid = authViewModel.getUidDoUsuario()
            if (uid == null) {
                _postFeedback.value = "Erro: Usuário não autenticado."
                _loading.value = false
                return@launch
            }

            val currentText = _text.value
            if (currentText.isBlank()) {
                _postFeedback.value = "Escreva algo para postar."
                _loading.value = false
                return@launch
            }

            val userName = authViewModel.getProfileName()
            if (userName == null) {
                _postFeedback.value = "Erro ao obter nome do usuário."
                _loading.value = false
                return@launch
            }
            Log.d(TAG, "Salvando post de texto...")

            val success = firestoreRepository.savePost(uid, currentText, userName) // <<< MODIFICADO

            if (success) {
                _postFeedback.value = "Post publicado com sucesso!"
                _text.value = ""

            } else {
                _postFeedback.value = "Erro ao salvar o post no banco de dados."
            }

            _loading.value = false
        }
    }

    fun loadGlobalFeed() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _postsList.value = firestoreRepository.readGlobalFeed()
            } catch (e: Exception) {
                _postFeedback.value = "Erro ao carregar o feed."
            }
            _loading.value = false
        }
    }

    fun loadProfileForUser(userId: String) {
        viewModelScope.launch {
            if (userId.isBlank()) {
                Log.w(TAG, "ID de usuário inválido para carregar perfil.")
                return@launch
            }

            _profileLoading.value = true

            _userProfilePosts.value = emptyList()
            _userPostCount.value = 0
            _userLastPostDate.value = null

            try {

                val posts = firestoreRepository.readUserPosts(userId)

                _userProfilePosts.value = posts

                _userPostCount.value = posts.size

                if (posts.isNotEmpty()) {
                    _userLastPostDate.value = posts.first().timestamp
                }

            } catch (e: Exception) {
                Log.e(TAG, "Erro ao carregar perfil do usuário $userId", e)
                _postFeedback.value = "Erro ao carregar perfil."
            }
            _profileLoading.value = false
        }
    }


    fun clearFeedback() {
        _postFeedback.value = null
    }
}