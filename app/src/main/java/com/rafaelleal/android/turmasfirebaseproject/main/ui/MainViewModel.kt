package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import com.rafaelleal.android.turmasfirebaseproject.models.Turma
import com.rafaelleal.android.turmasfirebaseproject.models.TurmaComId
import com.rafaelleal.android.turmasfirebaseproject.repository.TurmasRepository

class MainViewModel : ViewModel() {


    val TAG = "ViewModel"
    val repository = TurmasRepository.get()


    fun getCurrentUserEmail(): String {
        return repository.getCurrentUser()?.email ?: "Email não encontrado"
    }


    fun logout() {
        repository.logout()
    }

    fun cadastrarTurma(turma: Turma): Task<DocumentReference> {
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

    fun observeColecaoTurmas() {

        repository.getTurmasColecao()
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }



                val listaInput = mutableListOf<Turma>()

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {

                            val turma = dc.document.toObject<Turma>()
                            val id = dc.document.id
                            val turmaComId = turmaToTurmaComId(turma, id)

                            listaInput.add(dc.document.toObject())
                            Log.d(TAG, "New city: ${dc.document.data}")
                        }
                        DocumentChange.Type.MODIFIED -> Log.d(
                            TAG,
                            "Modified city: ${dc.document.id}"
                        )
                        DocumentChange.Type.REMOVED -> Log.d(
                            TAG,
                            "Removed city: ${dc.document.data}"
                        )
                    }
                }
                addListaToTurmas(listaInput)


            }
    }

    fun addListaToTurmas(listaInput: List<Turma>) {
        val listaAntiga = turmas.value
        val listaNova = mutableListOf<Turma>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setTurmas(listaNova)

    }

    fun addToTurmas(turma: Turma) {
        val lista = turmas.value
        val listaNova = mutableListOf<Turma>()
        lista?.forEach {
            listaNova.add(it)
        }
        listaNova.add(turma)
        setTurmas(listaNova)

    }

    private val _turmas = MutableLiveData<List<Turma>>()
    val turmas: LiveData<List<Turma>> = _turmas
    fun setTurmas(value: List<Turma>) {
        _turmas.postValue(value)
    }

    private val _turmasComId = MutableLiveData<List<TurmaComId>>()
    val turmasComId: LiveData<List<TurmaComId>> = _turmasComId
    fun setTurmasComId(value: List<TurmaComId>) {
        _turmasComId.postValue(value)
    }

    fun turmaToTurmaComId(turma: Turma, id: String) : TurmaComId{
        return TurmaComId(
            nomeProfessor = turma.nomeProfessor,
            nomeTurma = turma.nomeTurma,
            horario = turma.horario,
            id = id
        )
    }


}

/**
 * turmas = vazio
 *
 *
 *
 */