package com.goodness.codetadak.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodness.codetadak.R
import com.goodness.codetadak.databinding.FragmentVideoDetailBinding

class VideoDetailFragment : Fragment() {
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnDown.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.to_top,R.anim.from_bottom).remove(this).commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}