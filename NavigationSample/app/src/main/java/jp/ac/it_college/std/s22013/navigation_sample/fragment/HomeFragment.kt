package jp.ac.it_college.std.s22013.navigation_sample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import jp.ac.it_college.std.s22013.navigation_sample.R
import jp.ac.it_college.std.s22013.navigation_sample.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeToNext.setOnClickListener { toNext() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    private fun toNext() {
        val action = HomeFragmentDirections.actionHomeFragmentToSecondFragment()
        findNavController().navigate(action)
    }
}