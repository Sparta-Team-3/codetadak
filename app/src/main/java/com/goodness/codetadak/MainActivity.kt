package com.goodness.codetadak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goodness.codetadak.adapters.MainViewPagerAdapter
import com.goodness.codetadak.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
	private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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
		}.attach()
	}
}