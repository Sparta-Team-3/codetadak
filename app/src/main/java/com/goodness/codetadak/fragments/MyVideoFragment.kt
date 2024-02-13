package com.goodness.codetadak.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.goodness.codetadak.CircleProgressDialog
import com.goodness.codetadak.MainActivity
import com.goodness.codetadak.R
import com.goodness.codetadak.adapters.MyFavoriteVideoAdapter
import com.goodness.codetadak.adapters.SwipeHelperCallback
import com.goodness.codetadak.databinding.FragmentMyVideoBinding
import com.goodness.codetadak.edtitprofiledialog.EditMyProfileDialog
import com.goodness.codetadak.edtitprofiledialog.OkClick
import com.goodness.codetadak.sharedpreferences.App
import com.goodness.codetadak.sharedpreferences.UserInfo
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyVideoFragment : Fragment() {
	private var _binding: FragmentMyVideoBinding? = null
	private val binding get() = _binding!!
	private val youtubeViewModel by lazy { ViewModelProvider(requireActivity())[YoutubeViewModel::class.java] }
	private val myFavoriteVideoAdapter by lazy { MyFavoriteVideoAdapter(youtubeViewModel) }
	private var loadingDialog = CircleProgressDialog()
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

		myFavoriteVideoAdapter.setData(App.prefs.loadMyFavorite())

		initList()
		initProfileEdit()
		itemSwipeDelete()

	}

	private fun initList() { // RecyclerView 띄우기
		with(binding) {
			with(rvMyvideo) {
				layoutManager = GridLayoutManager(requireActivity(),2)
				setHasFixedSize(true)
				adapter = myFavoriteVideoAdapter.apply {
					myVideoItemClick = object : MyFavoriteVideoAdapter.MyVideoItemClick{
						override fun onClick(position: Int) {
							showLoading()
							(activity as? MainActivity)?.replace()
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
		val swipeHelperCallback = SwipeHelperCallback(myFavoriteVideoAdapter, requireActivity())
		ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvMyvideo)
	}

	private fun showLoading() {
		CoroutineScope(Dispatchers.Main).launch {
			loadingDialog.show(parentFragmentManager, loadingDialog.tag)
			withContext(Dispatchers.Default) { delay(1500) }
			loadingDialog.dismiss()
		}
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
		myFavoriteVideoAdapter.notifyDataSetChanged()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}