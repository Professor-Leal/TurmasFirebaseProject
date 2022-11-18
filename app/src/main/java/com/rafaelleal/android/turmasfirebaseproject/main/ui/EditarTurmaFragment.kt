package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.rafaelleal.android.turmasfirebaseproject.R
import com.rafaelleal.android.turmasfirebaseproject.databinding.FragmentEditarTurmaBinding
import com.rafaelleal.android.turmasfirebaseproject.models.Turma
import com.rafaelleal.android.turmasfirebaseproject.utils.getTextInput
import com.rafaelleal.android.turmasfirebaseproject.utils.nav
import com.rafaelleal.android.turmasfirebaseproject.utils.navUp

class EditarTurmaFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditarTurmaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarTurmaBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply{
            btnAtualizar.setOnClickListener {
                onAtualizarClick()
            }

            btnInscreverAlunos.setOnClickListener {
                onIncreverAlunosClick()
            }
        }


    }

    private fun onIncreverAlunosClick() {
        nav(R.id.action_editarTurmaFragment_to_inscreverAlunosFragment)
    }

    private fun onAtualizarClick() {
        val turma = getTurmaFromInputs()
        viewModel.atualizaTurma(turma)
        navUp()
    }

    private fun getTurmaFromInputs(): Turma {
        binding.apply {
            return Turma(
                nomeTurma = getTextInput(inputNomeTurma),
                nomeProfessor = getTextInput(inputNomeProfessor),
                horario = getTextInput(inputHorario)
            )
        }
    }

    private fun setupObservers() {
        viewModel.selectedTurmaComId.observe(viewLifecycleOwner){
            binding.apply {
                inputNomeTurma.setText(it.nomeTurma)
                inputNomeProfessor.setText(it.nomeProfessor)
                inputHorario.setText(it.horario)
            }
        }
    }

    private fun setupViews() {
        activity?.setTitle("Editar")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}