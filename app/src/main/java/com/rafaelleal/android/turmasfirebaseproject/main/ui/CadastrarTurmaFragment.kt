package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rafaelleal.android.turmasfirebaseproject.R
import com.rafaelleal.android.turmasfirebaseproject.databinding.FragmentCadastrarTurmaBinding
import com.rafaelleal.android.turmasfirebaseproject.models.Turma
import com.rafaelleal.android.turmasfirebaseproject.repository.TurmasRepository
import com.rafaelleal.android.turmasfirebaseproject.utils.toast

class CadastrarTurmaFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentCadastrarTurmaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastrarTurmaBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            onCadastrarClick()
        }
    }

    private fun onCadastrarClick() {
        val turma = getTurmaFromInputs()

        viewModel.cadastrarTurma(turma)
            .addOnSuccessListener { documentReference ->
                toast("Cadastrado com sucesso com id: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                toast("Falha ao cadastrar")
            }
    }

    private fun getTurmaFromInputs(): Turma {
        binding.apply {
            return Turma(
                nomeTurma = inputNomeTurma.text.toString(),
                nomeProfessor = inputNomeProfessor.text.toString(),
                horario = inputHorario.text.toString()
            )
        }


    }

    private fun setupViews() {
        activity?.setTitle("Cadastrar Turma")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}