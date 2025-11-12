package com.android.redesocial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

data class GroupData(
    val nome: String,
    val imageResId: Int,
    var participantes: Int = 0,
    var entrou: Boolean = false
)

class GroupsViewModel : ViewModel() {

    var grupos = mutableStateListOf<GroupData>()
        private set

    init {
        grupos.addAll(
            listOf(
                GroupData("Cinema", com.android.redesocial.R.drawable.cinema, 0),
                GroupData("Música", com.android.redesocial.R.drawable.musica, 0),
                GroupData("Esportes", com.android.redesocial.R.drawable.bola, 0)
            )
        )
    }

    fun entrarNoGrupo(index: Int) {
        val grupo = grupos[index]

        if (!grupo.entrou) {
            grupos[index] = grupo.copy(
                participantes = grupo.participantes + 1,
                entrou = true
            )
        } else {
            grupos[index] = grupo.copy(
                participantes = grupo.participantes - 1,
                entrou = false
            )
        }
    }
}