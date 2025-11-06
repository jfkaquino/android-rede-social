package com.android.redesocial.data.cloud

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID

// --- Estrutura de Dados ---

data class Post(
    // A 'label' pode ser o texto/descrição do post
    val label: String? = null,
    val imageUrl: String? = null,
    // Garante que o Post sempre saiba a quem pertence
    val ownerId: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// --- Repositório de Interação com o Firebase ---

class FirestoreRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = Firebase.storage
    private val TAG = "FirestoreRepository"

    /**
     * Salva um novo Post na coleção principal de posts e na coleção do usuário.
     * @param uid O ID do usuário logado (obtido do AuthViewModel).
     */
    fun savePost(uid: String, label: String, imageUrl: String) {
        if (uid.isEmpty()) {
            Log.e(TAG, "UID é vazio. Falha ao salvar Post.")
            return
        }

        val newPost = Post(
            label = label,
            imageUrl = imageUrl,
            ownerId = uid
        )

        // 1. Salvar o Post na coleção de posts do usuário (para listagem particular)
        db.collection("usuarios")
            .document(uid)
            .collection("meus-posts")
            .add(newPost)
            .addOnSuccessListener { Log.d(TAG, "Post salvo na coleção do usuário com ID: ${it.id}") }
            .addOnFailureListener { e -> Log.e(TAG, "Erro ao salvar Post na coleção do usuário", e) }

        // 2. Salvar o Post em uma coleção global de 'posts' (para Feed de Notícias)
        db.collection("posts")
            .add(newPost)
            .addOnSuccessListener { Log.d(TAG, "Post salvo no feed global com ID: ${it.id}") }
            .addOnFailureListener { e -> Log.e(TAG, "Erro ao salvar Post no feed global", e) }
    }


    /**
     * Função de exemplo para Upload de Imagem.
     * Retorna a URL da imagem no Storage após o upload.
     * @param uid O ID do usuário.
     * @param imageUri A URI local da imagem a ser enviada.
     */
    suspend fun uploadPostImage(uid: String, imageUri: Uri): String? {
        val fileName = "${UUID.randomUUID()}.jpg"
        val storageRef = storage.reference
            .child("users/$uid/posts/$fileName") // Caminho: users/{UID}/posts/{UUID}.jpg

        return try {
            // Fazer o upload do arquivo
            val uploadTask = storageRef.putFile(imageUri).await()

            // Obter a URL de download
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            Log.e(TAG, "Erro durante o upload da imagem", e)
            null
        }
    }


    /**
     * Lê todos os Posts da coleção 'meus-posts' do usuário.
     * @param uid O ID do usuário logado.
     */
    fun readUserPosts(uid: String) {
        if (uid.isEmpty()) {
            Log.e(TAG, "UID é vazio. Falha ao ler Posts.")
            return
        }

        db.collection("usuarios")
            .document(uid)
            .collection("meus-posts")
            .get()
            .addOnSuccessListener { result ->
                val posts = result.toObjects(Post::class.java)
                Log.d(TAG, "Total de Posts lidos: ${posts.size}")
                // TODO: Repassar a lista 'posts' para um LiveData/StateFlow no ViewModel
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Erro ao buscar Posts", exception)
            }
    }
}