package com.syr.module_custom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by songyaru on 2020/9/27.
 */
class CommonStatePagerAdapter(activity: FragmentActivity, private val pageModels: MutableList<PageModel>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount() = pageModels.size.coerceAtLeast(0)

    override fun createFragment(position: Int): Fragment {
        return PageFragment.newInstance(pageModels[position].sampleLayoutRes, pageModels[position].practiceLayoutRes)
    }
}