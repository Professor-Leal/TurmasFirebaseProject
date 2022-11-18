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
import com.rafaelleal.android.turmasfirebaseproject.models.Aluno
import com.rafaelleal.android.turmasfirebaseproject.models.AlunoComId
import com.rafaelleal.android.turmasfirebaseproject.models.AlunoNaTurma
import com.rafaelleal.android.turmasfirebaseproject.models.Turma


const val TAG = "TurmasFirebase"

class TurmasRepository private constructor() {


// ...
// Initialize Firebase Auth

    companion object {

        lateinit var auth: FirebaseAuth

        lateinit var db: FirebaseFirestore

        lateinit var colecaoTurmas: CollectionReference

        lateinit var colecaoAlunos: CollectionReference

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

            // Coleção de alunos:
            colecaoAlunos = db.collection("alunos")


        }

        fun get(): TurmasRepository {
            return INSTANCE
                ?: throw IllegalStateException("TurmasRepository deve ser inicializado.")
        }
    }

    // Auth  ///////////////////////////////////////////////////////////////////////////////////////

    fun getCurrentUser() = auth.currentUser

    fun isLoggedIn(): Boolean {

        if (getCurrentUser() != null) {
            return true
        }
        return false
    }

    // Faça o mesmo que foi feito com o Login
    fun cadastrarUsuarioComSenha(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logout() {
        auth.signOut()
    }

    // FireStore ///////////////////////////////////////////////////////////////////////////////////

    // Turmas
    fun cadastrarTurma(turma: Turma): Task<DocumentReference> {
        return colecaoTurmas.add(turma)
    }

    fun getTurmas(): Task<QuerySnapshot> {
        return colecaoTurmas.get()
    }

    fun getTurmasColecao(): CollectionReference {
        return colecaoTurmas
    }

    fun deleteTurma(id: String) {
        colecaoTurmas.document(id).delete()
    }

    fun atualizaTurma(id: String?, turma: Turma) {
        if (id != null) {
            colecaoTurmas.document(id).set(turma)
        }
    }

    // Alunos

    fun getAlunosColecao(): CollectionReference {
        return colecaoAlunos
    }

    fun cadastrarAluno(aluno: Aluno): Task<DocumentReference> {
        return colecaoAlunos.add(aluno)
    }

    fun deleteAluno(id: String): Task<Void> {
        return colecaoAlunos.document(id).delete()
    }

   fun atualizaAluno(id: String?, aluno: Aluno) {
        if (id != null) {
            colecaoAlunos.document(id).set(aluno)
        }
    }

    fun inscreverAlunoNaTurma(idTurma: String, alunoComId: AlunoComId){
        val alunoNaTurma = AlunoNaTurma(
            nomeAluno = alunoComId.nomeAluno,
            matricula = alunoComId.matricula,
        )
        colecaoTurmas
            .document(idTurma)
            .collection("alunos")
            .document(alunoComId.id)
            .set(alunoNaTurma)
    }


}