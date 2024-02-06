package com.goodness.codetadak.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.goodness.codetadak.edtitprofiledialog.EditMyProfileDialog
import com.goodness.codetadak.edtitprofiledialog.OkClick
import com.goodness.codetadak.databinding.FragmentMyVideoBinding
import com.goodness.codetadak.sharedpreferences.App

class MyVideoFragment : Fragment() {
	private var _binding: FragmentMyVideoBinding? = null
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
						Log.d("TAG", "onClick: $profileImage")
						Glide.with(binding.root).load(profileImage).into(cvMyvideoProfile)
						tvMyvideoName.setText(name)
						tvMyvideoInfo.setText(info)
						App.prefs.saveUserProfile(profileImage, name, info) // 저장이 안되고 잇음
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