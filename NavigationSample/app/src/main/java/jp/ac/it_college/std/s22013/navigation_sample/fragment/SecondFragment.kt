package jp.ac.it_college.std.s22013.navigation_sample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import jp.ac.it_college.std.s22013.navigation_sample.R
import jp.ac.it_college.std.s22013.navigation_sample.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.check1.setOnClickListener { toNext(1)}
        binding.check2.setOnClickListener { toNext(2)}
        binding.check3.setOnClickListener { toNext(3)}
        binding.check4.setOnClickListener { toNext(4)}
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    private fun toNext(choice: Int) {
        val bundle = bundleOf("choice" to choice)
        findNavController().navigate(R.id.action_secondFragment_to_thradFragment, bundle)
    }
}