package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.rafaelleal.android.turmasfirebaseproject.R
import com.rafaelleal.android.turmasfirebaseproject.databinding.FragmentTurmasBinding
import com.rafaelleal.android.turmasfirebaseproject.utils.nav
import com.rafaelleal.android.turmasfirebaseproject.utils.toast

class TurmasFragment : Fragment() {

    val viewModel : MainViewModel by activityViewModels()

    private var _binding: FragmentTurmasBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTurmasBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
        getTurmas()
    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            nav(R.id.action_turmasFragment_to_cadastrarTurmaFragment)
        }
    }

    private fun setupViews() {
        activity?.setTitle("Turmas")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getTurmas() {
        val lista = viewModel.getTurmas()
        toast("Tamanho da lista: ${lista.size}")
    }
}