package com.android.redesocial.viewmodel

// Removida a importação de android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.redesocial.data.cloud.FirestoreRepository
import com.android.redesocial.data.cloud.Post // Importe seu modelo de dados Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ... (Factory permanece a mesma)
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

    // Renomeado de 'label' para 'text'
    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    // Estado de 'imageUri' REMOVIDO
    // private val _imageUri = MutableStateFlow<Uri?>(null)
    // val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    // Estado para armazenar a lista de posts lidos
    private val _postsList = MutableStateFlow<List<Post>>(emptyList())
    val postsList: StateFlow<List<Post>> = _postsList.asStateFlow()

    private val _postFeedback = MutableStateFlow<String?>(null)
    val postFeedback: StateFlow<String?> = _postFeedback.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val TAG = "PostViewModel"

    // --- Eventos da UI ---

    // Renomeado de 'onLabelChanged' para 'onTextChanged'
    fun onTextChanged(newText: String) {
        _text.value = newText
    }

    // Função 'onImageSelected' REMOVIDA
    // fun onImageSelected(newUri: Uri?) { ... }

    /**
     * Lógica principal para criar e publicar um novo post (apenas texto).
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

            // Validação: checa se o texto não está vazio
            val currentText = _text.value
            if (currentText.isBlank()) {
                _postFeedback.value = "Escreva algo para postar."
                _loading.value = false
                return@launch
            }

            // 1. Salvar o Post no Firestore (sem imagem)
            Log.d(TAG, "Salvando post de texto...")
            val success = firestoreRepository.savePost(uid, currentText)

            if (success) {
                _postFeedback.value = "Post publicado com sucesso!"
                _text.value = "" // Limpa o campo após o sucesso
                loadGlobalFeed() // Atualiza o feed após postar
            } else {
                _postFeedback.value = "Erro ao salvar o post no banco de dados."
            }

            _loading.value = false
        }
    }

    /**
     * Função para carregar o feed global de posts.
     */
    fun loadGlobalFeed() {
        viewModelScope.launch {
            _loading.value = true // Usamos o loading principal por enquanto
            try {
                _postsList.value = firestoreRepository.readGlobalFeed()
            } catch (e: Exception) {
                _postFeedback.value = "Erro ao carregar o feed."
            }
            _loading.value = false
        }
    }

    /**
     * Função para carregar posts do usuário (exemplo).
     */
    fun loadUserPosts() {
        viewModelScope.launch {
            val uid = authViewModel.getUidDoUsuario()
            if (uid != null) {
                _loading.value = true
                try {
                    _postsList.value = firestoreRepository.readUserPosts(uid)
                } catch (e: Exception) {
                    _postFeedback.value = "Erro ao carregar seus posts."
                }
                _loading.value = false
            }
        }
    }

    fun clearFeedback() {
        _postFeedback.value = null
    }
}