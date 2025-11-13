package com.android.redesocial.data.cloud

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
// Removida a importação de java.util.UUID
import kotlin.coroutines.cancellation.CancellationException

data class Post(
    val text: String? = null,
    val ownerName: String? = null,
    val ownerId: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

class FirestoreRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = "FirestoreRepository"

    suspend fun savePost(uid: String, text: String, ownerName: String): Boolean {
        if (uid.isEmpty()) {
            Log.e(TAG, "UID é vazio. Falha ao salvar Post.")
            return false
        }

        val newPost = Post(
            text = text,
            ownerName = ownerName,
            ownerId = uid
        )

        return try {
            db.collection("usuarios")
                .document(uid)
                .collection("meus-posts")
                .add(newPost)
                .await()

            Log.d(TAG, "Post salvo na coleção do usuário.")

            db.collection("posts")
                .add(newPost)
                .await()

            Log.d(TAG, "Post salvo no feed global.")
            true

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e(TAG, "Erro ao salvar Post no Firestore", e)
            false
        }
    }

    suspend fun readGlobalFeed(): List<Post> {
        return try {
            val result = db.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            val posts = result.toObjects(Post::class.java)
            Log.d(TAG, "Total de Posts lidos do feed global: ${posts.size}")
            posts
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e(TAG, "Erro ao buscar feed global", e)
            emptyList()
        }
    }

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