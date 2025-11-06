/*
package com.android.redesocial.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.redesocial.data.cloud.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    // Recebe o AuthViewModel e o Repository como dependências
    private val authViewModel: AuthViewModel,
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
    // Nota: Em um projeto real, você usaria Injeção de Dependência (ex: Hilt)
    // para fornecer essas instâncias.
) : ViewModel() {

    private val _postFeedback = MutableStateFlow<String?>(null)
    val postFeedback: StateFlow<String?> = _postFeedback

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val TAG = "PostViewModel"

    /**
     * Lógica principal para criar e publicar um novo post.
     * Envolve 1) Upload da Imagem, e 2) Salvamento dos Dados no Firestore.
     */
    fun publishNewPost(label: String, imageUri: Uri?) {
        // Inicia uma corrotina para operações assíncronas
        viewModelScope.launch {

            _loading.value = true
            _postFeedback.value = null

            // 1. Obter o UID do usuário do AuthViewModel
            val uid = authViewModel.getUidDoUsuario()

            if (uid == null) {
                _postFeedback.value = "Erro: Usuário não autenticado."
                _loading.value = false
                return@launch
            }

            // Verificação básica da imagem
            if (imageUri == null) {
                _postFeedback.value = "Selecione uma imagem para postar."
                _loading.value = false
                return@launch
            }


            // 2. Fazer o upload da imagem para o Firebase Storage
            Log.d(TAG, "Iniciando upload da imagem...")
            val imageUrl = firestoreRepository.uploadPostImage(uid, imageUri)

            if (imageUrl != null) {
                // 3. Salvar o Post no Firestore com a URL da imagem
                Log.d(TAG, "Upload concluído. Salvando post no Firestore...")

                // Note que 'savePost' no Repository usa addOnSuccessListener,
                // idealmente você usaria uma função 'suspend' aqui também para await o resultado.

                // Exemplo de chamada que não bloqueia a UI:
                firestoreRepository.savePost(uid, label, imageUrl)

                _postFeedback.value = "Post publicado com sucesso!"

            } else {
                _postFeedback.value = "Erro ao fazer upload da imagem."
            }

            _loading.value = false
        }
    }

    /**
     * Função para carregar posts do usuário logado.
     */
    fun loadUserPosts() {
        val uid = authViewModel.getUidDoUsuario()
        if (uid != null) {
            // O repositório fará a leitura e logará, como configurado
            firestoreRepository.readUserPosts(uid)
        } else {
            Log.e(TAG, "Não é possível carregar posts: Usuário não logado.")
        }
    }

    fun clearFeedback() {
        _postFeedback.value = null
    }

}
*/