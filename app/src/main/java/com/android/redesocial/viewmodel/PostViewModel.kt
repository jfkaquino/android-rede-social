package com.android.redesocial.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.redesocial.data.cloud.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Factory para criar o PostViewModel, injetando o AuthViewModel.
 */
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

    // --- Estados da UI ---
    private val _label = MutableStateFlow("")
    val label: StateFlow<String> = _label.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    private val _postFeedback = MutableStateFlow<String?>(null)
    val postFeedback: StateFlow<String?> = _postFeedback.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val TAG = "PostViewModel"

    // --- Eventos da UI ---
    fun onLabelChanged(newLabel: String) {
        _label.value = newLabel
    }

    fun onImageSelected(newUri: Uri?) {
        _imageUri.value = newUri
    }

    /**
     * Lógica principal para criar e publicar um novo post.
     */
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

            val currentImageUri = _imageUri.value
            if (currentImageUri == null) {
                _postFeedback.value = "Selecione uma imagem para postar."
                _loading.value = false
                return@launch
            }

            // 1. Fazer o upload da imagem
            Log.d(TAG, "Iniciando upload da imagem...")
            val imageUrl = firestoreRepository.uploadPostImage(uid, currentImageUri)

            if (imageUrl != null) {
                // 2. Salvar o Post no Firestore
                Log.d(TAG, "Upload concluído. Salvando post...")
                val success = firestoreRepository.savePost(uid, _label.value, imageUrl)

                if (success) {
                    _postFeedback.value = "Post publicado com sucesso!"
                    // Limpa os campos após o sucesso
                    _label.value = ""
                    _imageUri.value = null
                } else {
                    _postFeedback.value = "Erro ao salvar o post no banco de dados."
                }

            } else {
                _postFeedback.value = "Erro ao fazer upload da imagem."
            }

            _loading.value = false
        }
    }

    /**
     * Função para carregar posts (sem mudanças).
     */
    fun loadUserPosts() {
        // ... (código existente)
    }

    fun clearFeedback() {
        _postFeedback.value = null
    }
}