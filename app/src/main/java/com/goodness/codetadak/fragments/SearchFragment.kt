package com.goodness.codetadak.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
<<<<<<< HEAD
import com.goodness.codetadak.MainActivity
=======
import com.goodness.codetadak.R
import com.goodness.codetadak.adapters.LanguageListAdapter
>>>>>>> dev
import com.goodness.codetadak.adapters.SearchListListAdapter
import com.goodness.codetadak.databinding.FragmentSearchBinding
import com.goodness.codetadak.viewmodels.LanguageViewModel
import com.goodness.codetadak.viewmodels.YoutubeViewModel
<<<<<<< HEAD
import kotlinx.coroutines.launch
=======
>>>>>>> dev

class SearchFragment : Fragment() {
	private lateinit var binding: FragmentSearchBinding

	private val languageViewModel by lazy { ViewModelProvider(requireActivity())[LanguageViewModel::class.java] }
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }

	private val languageListAdapter by lazy {
		LanguageListAdapter(requireActivity()) {
			languageViewModel.updateCurLanguage(it)
			binding.etSearch.setText(it.value)
			youtubeViewModel.getSearchByKeyword(
				q = it.value,
				maxResults = 12
			)
		}
	}
	private val searchListAdapter by lazy { SearchListListAdapter(youtubeViewModel) }

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initList()
		initViewModel()
		initHandler()
		adapter.setOnItemClickListener(object : SearchListListAdapter.OnItemClickListener {
			override fun onItemClick(position: Int) {
				Log.d("asd","asd: $position")
				(requireActivity() as MainActivity).replace()
			}
		})
	}

	private fun initList() = with(binding) {
		rvSearchList.layoutManager = LinearLayoutManager(requireActivity())
		rvSearchList.adapter = searchListAdapter

		rvLanguageSelect.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
		rvLanguageSelect.adapter = languageListAdapter
	}

	private fun initHandler() = with(binding) {
		etSearch.setOnKeyListener { _, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
				val q = etSearch.text.toString()

				youtubeViewModel.getSearchByKeyword(
					q = q,
					maxResults = 12
				)

				hideKeyboard()
				return@setOnKeyListener true
			}
			false
		}
	}

	private fun initViewModel() {
		youtubeViewModel.searchVideoState.observe(viewLifecycleOwner) { dataState ->
			with(binding.tvError) {
				visibility = if (dataState.isError) View.VISIBLE else View.GONE
				text = when (dataState.errorCode) {
					403 -> getString(R.string.quota_exceeded)
					else -> ""
				}
			}

			with(binding.sflShimmerContainer) {
				visibility = if (dataState.isLoading) View.VISIBLE else View.GONE
			}

			searchListAdapter.submitList(dataState.dataList)
		}

		languageViewModel.languageList.observe(viewLifecycleOwner) {
			languageListAdapter.submitList(it)
		}
	}

	private fun hideKeyboard() {
		val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
	}
}