package com.goodness.codetadak.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goodness.codetadak.MainActivity
import com.goodness.codetadak.fragments.HomeFragment
import com.goodness.codetadak.fragments.MyVideoFragment
import com.goodness.codetadak.fragments.SearchFragment

class MainViewPagerAdapter(fragment: MainActivity) : FragmentStateAdapter(fragment) {
	private val fragments by lazy {
		listOf(
			HomeFragment(),
			SearchFragment(),
			MyVideoFragment()
		)
	}

	override fun getItemCount(): Int = 3

	override fun createFragment(position: Int): Fragment {
		return fragments[position]
	}
}