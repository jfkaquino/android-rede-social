package com.android.redesocial.data.cloud

// Removida a importação de android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
// Removida a importação de java.util.UUID
import kotlin.coroutines.cancellation.CancellationException

// --- Estrutura de Dados ---

data class Post(
    // Renomeado 'label' para 'text' para maior clareza
    val text: String? = null,
    val ownerId: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// --- Repositório de Interação com o Firebase ---

class FirestoreRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // O storage não é mais necessário se não houver upload de imagens
    // private val storage: FirebaseStorage = Firebase.storage
    private val TAG = "FirestoreRepository"

    /**
     * Salva um novo Post (apenas texto) no Firestore.
     * @param uid O ID do usuário logado.
     * @param text O conteúdo do post.
     */
    suspend fun savePost(uid: String, text: String): Boolean {
        if (uid.isEmpty()) {
            Log.e(TAG, "UID é vazio. Falha ao salvar Post.")
            return false
        }

        // Criamos o post apenas com texto
        val newPost = Post(
            text = text,
            ownerId = uid
            // timestamp é definido por padrão
        )

        return try {
            // 1. Salvar o Post na coleção de posts do usuário
            db.collection("usuarios")
                .document(uid)
                .collection("meus-posts")
                .add(newPost)
                .await()

            Log.d(TAG, "Post salvo na coleção do usuário.")

            // 2. Salvar o Post em uma coleção global de 'posts'
            db.collection("posts")
                .add(newPost)
                .await()

            Log.d(TAG, "Post salvo no feed global.")
            true // Sucesso

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e(TAG, "Erro ao salvar Post no Firestore", e)
            false // Falha
        }
    }

    /**
     * Função de upload de imagem REMOVIDA.
     */
    // suspend fun uploadPostImage(...) { ... }

    /**
     * Lê todos os Posts da coleção 'posts' (feed global).
     * Retorna uma lista de Posts.
     */
    suspend fun readGlobalFeed(): List<Post> {
        return try {
            val result = db.collection("posts")
                // Ordena pelos mais recentes primeiro
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val posts = result.toObjects(Post::class.java)
            Log.d(TAG, "Total de Posts lidos do feed global: ${posts.size}")
            posts
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e(TAG, "Erro ao buscar feed global", e)
            emptyList() // Retorna lista vazia em caso de falha
        }
    }

    /**
     * Lê todos os Posts da coleção 'meus-posts' do usuário.
     * @param uid O ID do usuário logado.
     */
    suspend fun readUserPosts(uid: String): List<Post> {
        if (uid.isEmpty()) {
            Log.e(TAG, "UID é vazio. Falha ao ler Posts.")
            return emptyList()
        }

        return try {
            val result = db.collection("usuarios")
                .document(uid)
                .collection("meus-posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val posts = result.toObjects(Post::class.java)
            Log.d(TAG, "Total de Posts lidos do usuário: ${posts.size}")
            posts
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e(TAG, "Erro ao buscar Posts do usuário", e)
            emptyList()
        }
    }
}