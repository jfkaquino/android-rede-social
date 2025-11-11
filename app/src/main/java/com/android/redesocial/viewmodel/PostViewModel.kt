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

    // --- Estados da UI (Feed) ---

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    // Estado para armazenar a lista de posts lidos (Feed Global)
    private val _postsList = MutableStateFlow<List<Post>>(emptyList())
    val postsList: StateFlow<List<Post>> = _postsList.asStateFlow()

    private val _postFeedback = MutableStateFlow<String?>(null)
    val postFeedback: StateFlow<String?> = _postFeedback.asStateFlow()

    private val _loading = MutableStateFlow(false) // Loading do Feed/Postagem
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val TAG = "PostViewModel"

    // --- (NOVO) Estados da UI (Perfil de Usuário) ---

    // Armazena os posts de um perfil de usuário específico
    private val _userProfilePosts = MutableStateFlow<List<Post>>(emptyList())
    val userProfilePosts: StateFlow<List<Post>> = _userProfilePosts.asStateFlow()

    // Armazena a contagem de posts do perfil
    private val _userPostCount = MutableStateFlow(0)
    val userPostCount: StateFlow<Int> = _userPostCount.asStateFlow()

    // Armazena o timestamp (Long) do último post
    private val _userLastPostDate = MutableStateFlow<Long?>(null)
    val userLastPostDate: StateFlow<Long?> = _userLastPostDate.asStateFlow()

    // Loading específico para a tela de perfil
    private val _profileLoading = MutableStateFlow(false)
    val profileLoading: StateFlow<Boolean> = _profileLoading.asStateFlow()


    // --- Eventos da UI ---

    fun onTextChanged(newText: String) {
        _text.value = newText
    }

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

            val currentText = _text.value
            if (currentText.isBlank()) {
                _postFeedback.value = "Escreva algo para postar."
                _loading.value = false
                return@launch
            }

            // --- (NOVO) Obter o nome do usuário ---
            val userName = authViewModel.getProfileName()
            if (userName == null) {
                _postFeedback.value = "Erro ao obter nome do usuário."
                _loading.value = false
                return@launch
            }
            // ------------------------------------------

            // 1. Salvar o Post no Firestore (com o nome)
            Log.d(TAG, "Salvando post de texto...")
            // Passe o nome do usuário para o repositório
            val success = firestoreRepository.savePost(uid, currentText, userName) // <<< MODIFICADO

            if (success) {
                _postFeedback.value = "Post publicado com sucesso!"
                _text.value = "" // Limpa o campo após o sucesso
                // ...
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
            _loading.value = true
            try {
                _postsList.value = firestoreRepository.readGlobalFeed()
            } catch (e: Exception) {
                _postFeedback.value = "Erro ao carregar o feed."
            }
            _loading.value = false
        }
    }

    /**
     * (Função antiga 'loadUserPosts' renomeada para clareza)
     * Carrega os posts DO PRÓPRIO usuário logado na lista principal.
     * Útil para uma tela "Meus Posts" que reutiliza a lista do feed.
     */
    fun loadMyPostsIntoFeedList() {
        viewModelScope.launch {
            val uid = authViewModel.getUidDoUsuario()
            if (uid != null) {
                _loading.value = true
                try {
                    // Carrega na lista principal _postsList
                    _postsList.value = firestoreRepository.readUserPosts(uid)
                } catch (e: Exception) {
                    _postFeedback.value = "Erro ao carregar seus posts."
                }
                _loading.value = false
            }
        }
    }

    // --- (NOVAS FUNÇÕES ADICIONADAS) ---

    /**
     * Carrega todos os dados de perfil para um ID de usuário específico.
     * Isso preenche 'userProfilePosts', 'userPostCount' e 'userLastPostDate'.
     *
     * @param userId O ID do usuário cujo perfil queremos ver.
     */
    fun loadProfileForUser(userId: String) {
        viewModelScope.launch {
            if (userId.isBlank()) {
                Log.w(TAG, "ID de usuário inválido para carregar perfil.")
                return@launch
            }

            _profileLoading.value = true
            // Limpa dados antigos antes de carregar
            _userProfilePosts.value = emptyList()
            _userPostCount.value = 0
            _userLastPostDate.value = null

            try {
                // 1. Busca todos os posts desse usuário
                // (Isso cumpre a "funcao para retornar todos os posts de um usuario")
                val posts = firestoreRepository.readUserPosts(userId)

                // 2. Atualiza o StateFlow com a lista de posts
                _userProfilePosts.value = posts

                // 3. Atualiza a contagem de posts
                // (Isso cumpre a "funcao para retornar o numero de post de um usuario")
                _userPostCount.value = posts.size

                // 4. Pega a data do último post
                // (Isso cumpre a "data do ultimo post")
                // Assumindo que readUserPosts() retorna a lista ordenada por timestamp DESC
                if (posts.isNotEmpty()) {
                    // O post mais recente é o primeiro da lista (index 0)
                    _userLastPostDate.value = posts.first().timestamp
                }

            } catch (e: Exception) {
                Log.e(TAG, "Erro ao carregar perfil do usuário $userId", e)
                _postFeedback.value = "Erro ao carregar perfil." // Feedback de erro
            }
            _profileLoading.value = false
        }
    }


    fun clearFeedback() {
        _postFeedback.value = null
    }
}