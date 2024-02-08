package com.goodness.codetadak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.goodness.codetadak.adapters.MainViewPagerAdapter
import com.goodness.codetadak.api.responses.VideoItem
import com.goodness.codetadak.databinding.ActivityMainBinding
import com.goodness.codetadak.fragments.VideoDetailFragment
import com.goodness.codetadak.viewmodels.DataState
import com.goodness.codetadak.viewmodels.YoutubeViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	private val detailFragment = VideoDetailFragment()
	private val bundle = Bundle()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		initSwiper()
	}

	private fun initSwiper() = with(binding) {
		vpMain.adapter = MainViewPagerAdapter(this@MainActivity)

		TabLayoutMediator(tlNavigator, vpMain) { tab, position ->
			tab.text = when (position) {
				0 -> getString(R.string.fragment_1)
				1 -> getString(R.string.fragment_2)
				2 -> getString(R.string.fragment_3)
				else -> ""
			}
			when (position) {
				0 -> tab.setIcon(R.drawable.ic_home)
				1 -> tab.setIcon(R.drawable.ic_search)
				2 -> tab.setIcon(R.drawable.ic_mypage)
			}
		}.attach()
		vpMain.setPageTransformer(DepthPageTransformer())
	}

	fun replace() {
		val fragmentManager = supportFragmentManager
		val fragmentTransaction = fragmentManager.beginTransaction()
		fragmentTransaction.setCustomAnimations(R.anim.to_bottom, R.anim.from_top)
			.replace(R.id.video_detail_container, detailFragment).commit()
	}


}