package jp.ac.it_college.std.s22013.navigation_sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s22013.navigation_sample.databinding.FragmentThradBinding


class ThradFragment : Fragment() {
    private var _binding: FragmentThradBinding? = null
    private val binding get() = _binding!!

    private val args: ThradFragmentArgs  by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThradBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.display.text = "<${args.choice}>"
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
