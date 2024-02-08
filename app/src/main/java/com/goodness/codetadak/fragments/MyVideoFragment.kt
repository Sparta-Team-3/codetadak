package com.goodness.codetadak.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.goodness.codetadak.R
import com.goodness.codetadak.adapters.MyFavoriteVideoAdapter
import com.goodness.codetadak.adapters.SwipeHelperCallback
import com.goodness.codetadak.api.responses.Item
import com.goodness.codetadak.sharedpreferences.UserInfo
import com.goodness.codetadak.edtitprofiledialog.EditMyProfileDialog
import com.goodness.codetadak.edtitprofiledialog.OkClick
import com.goodness.codetadak.databinding.FragmentMyVideoBinding
import com.goodness.codetadak.sharedpreferences.App

class MyVideoFragment : Fragment() {
	private var _binding: FragmentMyVideoBinding? = null
	private val binding get() = _binding!!
	private val myFavoriteVideoAdapter by lazy { MyFavoriteVideoAdapter() }
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

		val dummyList = mutableListOf<Item>(
			Item(R.drawable.img_example,"my_favorite_video", "description")
		)
		myFavoriteVideoAdapter.setData(dummyList)

		initList() // 리스트 띄우기
		initProfileEdit() // 프로필 수정
		itemSwipeDelete() // 스와이프 하여 삭제

	}

	private fun initList() { // RecyclerView 띄우기
		with(binding) {
			with(rvMyvideo) {
				layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
				setHasFixedSize(true)
				adapter = myFavoriteVideoAdapter.apply {
					myVideoItemClick = object : MyFavoriteVideoAdapter.MyVideoItemClick{
						override fun onClick(view: View, position: Int) {
							// 아이템 목록 클릭시 DetailFragment 호출 및 데이터 전달?
						}
					}
				}
			}
//			myFavoriteVideoAdapter.setData() // 저장된 값 불러서 리스트 넣기
		}
	}

	private fun initProfileEdit() { // 프로필 수정 기능
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

	private fun itemSwipeDelete() {
		val swipeHelperCallback = SwipeHelperCallback(myFavoriteVideoAdapter)
		ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvMyvideo)
	}

	override fun onResume() {
		super.onResume()
		val userInfo = App.prefs.loadUserProfile() // 프로필 불러오기
		with(binding) {
			if(userInfo.profileImage == null || userInfo.profileImage.toString() == "null") {
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