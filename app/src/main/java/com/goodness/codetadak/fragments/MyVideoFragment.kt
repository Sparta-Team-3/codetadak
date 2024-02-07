package com.goodness.codetadak.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.goodness.codetadak.R
import com.goodness.codetadak.sharedpreferences.UserInfo
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
					override fun onClick(userInfo: UserInfo) {
						if(userInfo.profileImage == null) {
							ivMyvideoProfile.setImageResource(R.drawable.ic_default_profile)
						}else {
							Glide.with(binding.root).load(userInfo.profileImage).into(ivMyvideoProfile)
						}
						tvMyvideoName.setText(userInfo.name)
						tvMyvideoInfo.setText(userInfo.info)
						App.prefs.saveUserProfile(userInfo) // 프로필 저장
					}
				}
				editMyPageDialog.show(requireActivity().supportFragmentManager,"EditMyProfilDialog")
			}
		}
	}

	override fun onResume() {
		super.onResume()
		val userInfo = App.prefs.loadUserProfile() // 프로필 불러오기
		with(binding) {
			if(userInfo.profileImage == null) {
				ivMyvideoProfile.setImageResource(R.drawable.ic_default_profile)
			}else {
				Glide.with(binding.root).load(userInfo.profileImage).into(ivMyvideoProfile)
			}
			tvMyvideoName.setText(userInfo.name)
			tvMyvideoInfo.setText(userInfo.info)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}