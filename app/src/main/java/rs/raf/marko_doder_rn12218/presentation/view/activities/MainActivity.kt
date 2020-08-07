package rs.raf.marko_doder_rn12218.presentation.view.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.presentation.view.adapters.MainPagerAdapter
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val FINE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private var mLocationPermissionsGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        getLocationPermission()
    }

    private fun initUI() {
        initPageAdapter()
        initNavigation()
    }

    private fun initNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.mapMi -> { viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_MAP, false) }
                else -> { viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_LOCATIONS, false) }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initPageAdapter() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager, this)
    }


    fun getLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initUI()
        } else {
            ActivityCompat.requestPermissions(
                this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mLocationPermissionsGranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location permission must be granted", Toast.LENGTH_LONG).show()
                    finishAndRemoveTask()
                }
                initUI()
            }
        }
    }



}