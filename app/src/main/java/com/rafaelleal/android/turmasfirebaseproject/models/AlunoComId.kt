package com.rafaelleal.android.turmasfirebaseproject.models

data class AlunoComId (
    val nomeAluno: String = "",
    val matricula: String  ="",
    val idade: Int = 0,
    val id: String = ""
)
//)
//    constructor(aluno: Aluno, id: String){
//        nomeAluno = aluno.nomeAluno
//        matricula = aluno.matricula
//        idade = aluno.idade
//        this.id = id
//    }
//
//    fun toAluno(): Aluno {
//        return Aluno(
//            nomeAluno = nomeAluno,
//            matricula = matricula,
//            idade = idade
//        )
//    }
//
//}