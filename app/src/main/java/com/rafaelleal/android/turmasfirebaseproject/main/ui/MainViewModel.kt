package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.rafaelleal.android.turmasfirebaseproject.models.Turma
import com.rafaelleal.android.turmasfirebaseproject.repository.TurmasRepository

class MainViewModel: ViewModel() {


    val TAG = "ViewModel"
    val repository = TurmasRepository.get()


    fun getCurrentUserEmail(): String {
        return repository.getCurrentUser()?.email ?: "Email não encontrado"
    }


    fun logout() {
        repository.logout()
    }

    fun cadastrarTurma(turma: Turma) : Task<DocumentReference>{
        return repository.cadastrarTurma(turma)
    }

    fun getTurmas(): List<Turma> {

        val lista = mutableListOf<Turma>()

        repository.getTurmas()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val turma = document.toObject<Turma>()
                    lista.add(turma)
                    Log.i(TAG, "document: ${document}")
                    Log.i(TAG, "turma: ${turma}")
                }
                setTurmas(lista)
            }
            .addOnFailureListener { exception ->

            }
        return lista
    }

    private val _turmas = MutableLiveData<List<Turma>>()
    val turmas : LiveData<List<Turma>> = _turmas
    fun setTurmas(value: List<Turma>){
        _turmas.postValue(value)
    }




}