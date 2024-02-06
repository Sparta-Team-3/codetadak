package com.goodness.codetadak.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.goodness.codetadak.EditMyProfileDialog
import com.goodness.codetadak.OkClick
import com.goodness.codetadak.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
	private var _binding : FragmentMyVideoBinding? = null
	private val binding get() = _binding!!
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentMyVideoBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(binding) {
			ivMyvideoProfilechange.setOnClickListener {
				val editMyPageDialog = EditMyProfileDialog()
				editMyPageDialog.okClick = object : OkClick {
					override fun onClick(profileImage: Drawable, name: String, info: String) {
						TODO("Not yet implemented")
					}
				}
				editMyPageDialog.show(requireActivity().supportFragmentManager,"EditMyProfilDialog")
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}