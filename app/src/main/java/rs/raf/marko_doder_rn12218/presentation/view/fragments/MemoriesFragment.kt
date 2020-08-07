package rs.raf.marko_doder_rn12218.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_memories.*
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.presentation.view.adapters.TabPagerAdapter

class MemoriesFragment : Fragment(R.layout.fragment_memories) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initTabs()
    }

    private fun initTabs() {
        tabViewPager.adapter = context?.let { TabPagerAdapter(childFragmentManager, it) }
        tabLayout.setupWithViewPager(tabViewPager)
    }

}