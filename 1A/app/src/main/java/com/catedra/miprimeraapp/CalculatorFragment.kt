package com.catedra.miprimeraapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catedra.miprimeraapp.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonCalculate.setOnClickListener { onCalculateClicked() }
    }

    private fun onCalculateClicked() {
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull()
        val percentage = getSelectedPercentage()

        if (amount == null) {
            binding.editTextAmount.error = "Enter a valid amount"
            return
        }
        if (percentage == null) {
            binding.radioGroupPercentage.requestFocus()
            return
        }

        navigateToResult(amount, percentage)
    }

    private fun navigateToResult(amount: Double, percentage: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ResultFragment.newInstance(amount, percentage))
            .addToBackStack(null)
            .commit()
    }

    private fun getSelectedPercentage(): Int? = when (binding.radioGroupPercentage.checkedRadioButtonId) {
        R.id.radio10 -> 10
        R.id.radio15 -> 15
        R.id.radio20 -> 20
        else -> null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}