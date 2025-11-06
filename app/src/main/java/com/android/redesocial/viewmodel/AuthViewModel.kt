package com.android.redesocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userState = MutableStateFlow(auth.currentUser)
    val userState: StateFlow<FirebaseUser?> = _userState

    private val _authFeedback = MutableStateFlow<String?>(null)
    val authFeedback: StateFlow<String?> = _authFeedback

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun signUp(email: String, senha: String, nome: String){

        viewModelScope.launch {

            _loading.value = true
            _authFeedback.value = null

            try {
                auth.createUserWithEmailAndPassword(email, senha).await()

                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build()

                auth.currentUser?.updateProfile(profileUpdate)?.await()

                _userState.value = auth.currentUser
                _authFeedback.value = "Cadastro realizado com sucesso."

            } catch (e: Exception) {
                _authFeedback.value = e.message ?: "Erro desconhecido no cadastro."
            } finally {
                _loading.value = false
            }

        }

    }

    fun signIn(email: String, senha: String){
        viewModelScope.launch {
            _loading.value = true
            _authFeedback.value = null

            try {
                auth.signInWithEmailAndPassword(email, senha).await()
                _userState.value = auth.currentUser
            } catch (e: Exception) {
                _authFeedback.value = e.message ?: "Erro no login."
            } finally {
                _loading.value = false
            }
        }

    }

    fun getUidDoUsuario(): String? {
        return auth.currentUser?.uid
    }

    fun signOut(){
        auth.signOut()
        _userState.value = null
    }

    fun clearFeedback(){
        _authFeedback.value = null
    }

}