package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rafaelleal.android.turmasfirebaseproject.R
import com.rafaelleal.android.turmasfirebaseproject.databinding.FragmentEditarAlunoBinding
import com.rafaelleal.android.turmasfirebaseproject.models.Aluno
import com.rafaelleal.android.turmasfirebaseproject.utils.getIntInput
import com.rafaelleal.android.turmasfirebaseproject.utils.getTextInput
import com.rafaelleal.android.turmasfirebaseproject.utils.navUp

class EditarAlunoFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditarAlunoBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarAlunoBinding.inflate(inflater, container, false)
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
        binding.btnAtualizar.setOnClickListener {
            onAtualizarClick()
        }
    }

    private fun onAtualizarClick() {
        val aluno = getAlunoFromInputs()
        viewModel.atualizaAluno(aluno)
        navUp()
    }

    private fun getAlunoFromInputs(): Aluno {
        binding.apply {
            return Aluno(
                nomeAluno = getTextInput(inputNomeAluno),
                matricula = getTextInput(inputMatricula),
                idade = getIntInput(inputIdade)
            )
        }
    }

    private fun setupObservers() {
        viewModel.selectedAlunoComId.observe(viewLifecycleOwner){
            binding.apply {
                inputNomeAluno.setText(it.nomeAluno)
                inputMatricula.setText(it.matricula)
                inputIdade.setText(it.idade.toString())
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