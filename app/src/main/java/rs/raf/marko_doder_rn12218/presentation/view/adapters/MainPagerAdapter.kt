package rs.raf.marko_doder_rn12218.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.presentation.view.fragments.MemoriesFragment
import rs.raf.marko_doder_rn12218.presentation.view.fragments.InputMapFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_MAP = 0
        const val FRAGMENT_LOCATIONS = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_MAP -> InputMapFragment()
            else -> MemoriesFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_MAP -> context.getString(R.string.map)
            else -> context.getString(R.string.locations)
        }
    }

}