package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelleal.android.turmasfirebaseproject.R
import com.rafaelleal.android.turmasfirebaseproject.databinding.FragmentAlunosBinding
import com.rafaelleal.android.turmasfirebaseproject.main.ui.adapters.AlunoComIdAdapter
import com.rafaelleal.android.turmasfirebaseproject.main.ui.adapters.AlunoComIdListener
import com.rafaelleal.android.turmasfirebaseproject.models.AlunoComId
import com.rafaelleal.android.turmasfirebaseproject.utils.nav

class AlunosFragment : Fragment() {

    val TAG = "AlunosFragment"

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentAlunosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlunosBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    val adapter = AlunoComIdAdapter(
        object : AlunoComIdListener {
            override fun onEditClick(aluno: AlunoComId) {
                viewModel.setSelectedAlunoComId(aluno)
                nav(R.id.action_alunosFragment_to_editarAlunoFragment)
            }

            override fun onDeleteClick(aluno: AlunoComId) {
                viewModel.deleteAluno(aluno)
            }
        }
    )

    private fun setup() {
        setupViews()

        setupClickListeners()

        setupRecyclerView()

        setupObservers()

    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            nav(R.id.action_alunosFragment_to_cadastrarAlunoFragment)
        }
    }

    private fun setupViews() {
        activity?.setTitle("Alunos")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        // adapter precisa ser uma variável global para ser acessada por todos os métodos
        binding.rvAlunos.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservers() {

        viewModel.alunosComId.observe(viewLifecycleOwner) {
            atualizaRecyclerView(it)
        }
    }

    fun atualizaRecyclerView(lista: List<AlunoComId>) {
        adapter.submitList(lista)
        binding.rvAlunos.adapter = adapter
    }


}