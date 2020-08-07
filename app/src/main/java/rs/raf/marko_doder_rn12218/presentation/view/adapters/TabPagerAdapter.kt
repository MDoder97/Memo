package rs.raf.marko_doder_rn12218.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.presentation.view.fragments.SavedMemoriesMapFragment
import rs.raf.marko_doder_rn12218.presentation.view.fragments.SavedMemoriesListFragment

class TabPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_MAP_SAVED = 0
        const val FRAGMENT_LIST_SAVED = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_MAP_SAVED -> SavedMemoriesMapFragment()
            else -> SavedMemoriesListFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_MAP_SAVED -> context.getString(R.string.map)
            else -> context.getString(R.string.list)
        }
    }

}