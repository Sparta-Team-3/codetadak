package com.goodness.codetadak.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.goodness.codetadak.MainActivity
import com.goodness.codetadak.R
import com.goodness.codetadak.api.YouTubeInstance
import com.goodness.codetadak.databinding.FragmentSearchBinding
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
	private lateinit var binding: FragmentSearchBinding
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.button2.setOnClickListener {
			(requireActivity() as MainActivity).replace()
		}
	}
}