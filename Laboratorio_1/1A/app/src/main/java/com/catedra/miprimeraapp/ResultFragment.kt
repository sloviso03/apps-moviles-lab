package com.catedra.miprimeraapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catedra.miprimeraapp.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayResults()
        binding.buttonBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun displayResults() {
        val amount = arguments?.getDouble(Constants.KEY_AMOUNT) ?: 0.0
        val percentage = arguments?.getInt(Constants.KEY_PERCENTAGE) ?: 0

        val tip = amount * percentage / 100
        val total = amount + tip

        binding.textBillAmount.text = "$ %.2f".format(amount)
        binding.textTipAmount.text  = "$ %.2f".format(tip)
        binding.textTotalAmount.text = "$ %.2f".format(total)
    }

    companion object {
        fun newInstance(amount: Double, percentage: Int): ResultFragment {
            return ResultFragment().apply {
                arguments = Bundle().apply {
                    putDouble(Constants.KEY_AMOUNT, amount)
                    putInt(Constants.KEY_PERCENTAGE, percentage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}