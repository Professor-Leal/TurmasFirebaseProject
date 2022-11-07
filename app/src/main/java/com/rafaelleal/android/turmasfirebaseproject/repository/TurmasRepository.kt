package com.rafaelleal.android.turmasfirebaseproject.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rafaelleal.android.turmasfirebaseproject.models.Turma


const val TAG = "TurmasFirebase"

class TurmasRepository private constructor() {


// ...
// Initialize Firebase Auth

    companion object {

        lateinit var auth: FirebaseAuth

        lateinit var db: FirebaseFirestore

        lateinit var colecaoTurmas : CollectionReference

        private var INSTANCE: TurmasRepository? = null
        fun initialize() {
            if (INSTANCE == null) {
                INSTANCE = TurmasRepository()
            }
            auth = Firebase.auth
            // Banco de dados Firestore
            db = Firebase.firestore

            // Coleção de turmas:
            colecaoTurmas = db.collection("turmas")


        }

        fun get(): TurmasRepository {
            return INSTANCE
                ?: throw IllegalStateException("TurmasRepository deve ser inicializado.")
        }
    }

    // Auth  ///////////////////////////////////////////////////////////////////////////////////////

    fun getCurrentUser() = auth.currentUser

    fun isLoggedIn(): Boolean {

        if(getCurrentUser() != null) {
            return true
        }
        return false
    }

    // Faça o mesmo que foi feito com o Login
    fun cadastrarUsuarioComSenha(
        email: String,
        password: String
    ) : Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(
        email: String,
        password: String
    ) : Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logout(){
        auth.signOut()
    }

    // FireStore ///////////////////////////////////////////////////////////////////////////////////

    fun cadastrarTurma( turma: Turma): Task<DocumentReference> {
        return  colecaoTurmas.add(turma)
    }

    fun getTurmas() : Task<QuerySnapshot> {
        return colecaoTurmas.get()
    }



}